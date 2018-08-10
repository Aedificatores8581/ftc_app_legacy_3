package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.RobitBot;
import org.firstinspires.ftc.teamcode.Universal.JSONAutonGetter;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

@Autonomous(name = "Robit Autonomous", group = "robit")
public class RobitAuto extends RobitBot{
    //region Classes and Enums

    // State of Autonomous.
    private enum State {
        ARM_DOWN(false, "ARM_DOWN"),
        DETECT_JEWEL(false, "DETECT_JEWEL"),
        HIT_FRONT_JEWEL(true, "HIT_FRONT_JEWEL"),   // Note that this and the next state are
        HIT_BACK_JEWEL(true, "HIT_BACK_JEWEL"),     // relative to the wall near the near positions
        ARM_UP(false, "ARM_UP"),
        FAR_TURN(true, "FAR_TURN"),                 // Only used in Far Positions
        GO_FORWARD_FROM_BACK_JEWEL(true, "GO_FORWARD_FROM_BACK_JEWEL"),
        GO_FORWARD_FROM_FRONT_JEWEL(true, "GO_FORWARD_FROM_FRONT_JEWEL"),
        PARK_AND_END(false, "PARK_AND_END");

        private boolean usesEncoders;
        private String description;

        State(boolean usesEncoders, String description) {
            this.usesEncoders = usesEncoders;
            this.description = description;
        }

        public boolean isUsingEncoders() {
            return usesEncoders;
        }

        public String getDescription() {
            return description;
        }
    }


    private enum FieldPosition {
        RED_NEAR("Red Near"),
        RED_FAR("Red Far"),
        BLUE_NEAR("Blue Near"),
        BLUE_FAR("Blue Far");

        private String description;
        FieldPosition(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    private static class OpModeConstants {
        static double ARM_DOWN_POSITION;
        static double ARM_UP_POSITION;

        static double MOTOR_SPEED;

        static int JEWEL_RED_THRESHOLD;
        static int JEWEL_BLUE_THRESHOLD;

        static void setConstants(double arm_down_position,
                                 double arm_up_position,
                                 double motor_speed,
                                 int jewel_red_threshold,
                                 int jewel_blue_threshold) {
            ARM_DOWN_POSITION = arm_down_position;
            ARM_UP_POSITION = arm_up_position;
            MOTOR_SPEED = motor_speed;
            JEWEL_RED_THRESHOLD = jewel_red_threshold;
            JEWEL_BLUE_THRESHOLD = jewel_blue_threshold;
        }
    }
    //endregion

    //region Variable Initializations
    private State state;
    private State nextStateFromFarTurn; // Stores which state to go to next
                                        // (GO_FORWARD_FROM_BACK_JEWEL or GO_FORWARD_FROM_FRONT_JEWEL)
                                        // from FAR_TURN

    private FieldPosition fieldPosition;
    private JSONAutonGetter jsonAutonGetter;
    private Gamepad prev;

    // Provides a way to access how far to move based on a state given
    private HashMap<State,Integer> encoderList;
    //endregion


    @Override
    public void init() {
        super.init();
        drivetrain.resetEncoders();

        try {
            jsonAutonGetter = new JSONAutonGetter("RobitAuto.json");
        } catch (IOException | JSONException e) {
            telemetry.addLine(e.getMessage());
            stop();
        }

        //region Initialize OpModeConstants
        try {
            OpModeConstants.setConstants(
                    jsonAutonGetter.jsonObject.getDouble("ARM_DOWN_POSITION"),
                    jsonAutonGetter.jsonObject.getDouble("ARM_UP_POSITION"),

                    jsonAutonGetter.jsonObject.getDouble("MOTOR_SPEED"),

                    jsonAutonGetter.jsonObject.getInt("JEWEL_RED_THRESHOLD"),
                    jsonAutonGetter.jsonObject.getInt("JEWEL_BLUE_THRESHOLD")
            );
        } catch (JSONException e) {
            telemetry.addLine(e.getMessage());
            telemetry.update();
            stop();
        }
        //endregion

        fieldPosition = FieldPosition.RED_NEAR;
        encoderList = new HashMap<>();
        state = State.ARM_DOWN;
        prev = new Gamepad();
        try {
            prev.copy(gamepad1);
        } catch (RobotCoreException e) {
            telemetry.addLine(e.getMessage());
        }
    }

    @Override
    public void init_loop() {
        telemetry.addLine("Select Field Position:\n");

        switch (fieldPosition) { // TODO: Super Inefficient use of space; Create TeleMenu Class
            case RED_NEAR:
                telemetry.addLine(">" + FieldPosition.RED_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.RED_FAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_FAR.getDescription());

                if (gamepad1.dpad_down && !prev.dpad_down) {
                    fieldPosition = FieldPosition.RED_FAR;
                } else if (gamepad1.dpad_up && !prev.dpad_up) {
                    fieldPosition = FieldPosition.BLUE_FAR;
                }
                break;

            case RED_FAR:
                telemetry.addLine(" " + FieldPosition.RED_NEAR.getDescription());
                telemetry.addLine(">" + FieldPosition.RED_FAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_FAR.getDescription());

                if (gamepad1.dpad_down && !prev.dpad_down) {
                    fieldPosition = FieldPosition.BLUE_NEAR;
                } else if (gamepad1.dpad_up && !prev.dpad_up) {
                    fieldPosition = FieldPosition.RED_NEAR;
                }
                break;

            case BLUE_NEAR:
                telemetry.addLine(" " + FieldPosition.RED_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.RED_FAR.getDescription());
                telemetry.addLine(">" + FieldPosition.BLUE_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_FAR.getDescription());

                if (gamepad1.dpad_down && !prev.dpad_down) {
                    fieldPosition = FieldPosition.BLUE_FAR;
                } else if (gamepad1.dpad_up && !prev.dpad_up) {
                    fieldPosition = FieldPosition.RED_FAR;
                }
                break;

            case BLUE_FAR:
                telemetry.addLine(" " + FieldPosition.RED_NEAR.getDescription());
                telemetry.addLine(" " + FieldPosition.RED_FAR.getDescription());
                telemetry.addLine(" " + FieldPosition.BLUE_NEAR.getDescription());
                telemetry.addLine(">" + FieldPosition.BLUE_FAR.getDescription());

                if (gamepad1.dpad_down && !prev.dpad_down) {
                    fieldPosition = FieldPosition.RED_NEAR;
                } else if (gamepad1.dpad_up && !prev.dpad_up) {
                    fieldPosition = FieldPosition.BLUE_NEAR;
                }
                break;
        }

        try {
            prev.copy(gamepad1);
        } catch (RobotCoreException e) {
            telemetry.addLine(e.getMessage());
        }

    }

    @Override
    public void start() {
        super.start();
        String FieldPrefixForJSON = "";

        switch (fieldPosition) {

            case RED_NEAR:
                FieldPrefixForJSON = "RED_NEAR_";
                break;

            case RED_FAR:
                FieldPrefixForJSON = "RED_FAR_";
                break;

            case BLUE_NEAR:
                FieldPrefixForJSON = "BLUE_NEAR_";
                break;

            case BLUE_FAR:
                FieldPrefixForJSON = "BLUE_FAR_";
                break;
        }

        for (State currentState : State.values()) {

            if (!currentState.isUsingEncoders()) {
                encoderList.put(currentState, 0);
            } else {

                try {
                    encoderList.put(currentState, // TODO: Fix Formatting
                            jsonAutonGetter.jsonObject.getInt(
                                    FieldPrefixForJSON + currentState.getDescription()));

                } catch (JSONException e) {
                    telemetry.addLine("Couldn't get " +
                            FieldPrefixForJSON + currentState.getDescription() +
                            " from JSON: " + e.getMessage());
                    telemetry.update();
                    stop();
                }
            }
        }


    }

    @Override
    public void loop() {
        switch (state) {
            case ARM_DOWN:
                arm.setPosition(OpModeConstants.ARM_DOWN_POSITION);
                state = State.DETECT_JEWEL;
                break;

            case DETECT_JEWEL:
                switch (fieldPosition) {
                    case RED_NEAR:
                    case RED_FAR:
                        if (colorSensor.red() > OpModeConstants.JEWEL_RED_THRESHOLD && colorSensor.red() > colorSensor.blue()) {
                            state = State.HIT_BACK_JEWEL;
                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                        } else if(colorSensor.blue() > OpModeConstants.JEWEL_BLUE_THRESHOLD && colorSensor.blue() > colorSensor.red()) {
                            state = State.HIT_FRONT_JEWEL;
                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                        }
                        break;

                    case BLUE_NEAR:
                    case BLUE_FAR:
                        if (colorSensor.blue() > OpModeConstants.JEWEL_BLUE_THRESHOLD && colorSensor.blue() > colorSensor.red()) {
                            state = State.HIT_FRONT_JEWEL;
                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                        } else if(colorSensor.red() > OpModeConstants.JEWEL_RED_THRESHOLD && colorSensor.red() > colorSensor.blue()) {
                            state = State.HIT_BACK_JEWEL;
                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                        }
                        break;
                }
                break;

            case HIT_FRONT_JEWEL:
                nextStateFromFarTurn = State.GO_FORWARD_FROM_FRONT_JEWEL;

                if (Math.abs(drivetrain.averageLeftEncoders()) >= Math.abs(encoderList.get(State.HIT_FRONT_JEWEL))) {
                    drivetrain.setLeftPow(0.0);
                    drivetrain.setRightPow(0.0);

                    drivetrain.resetEncoders();

                    switch (fieldPosition) {

                        case RED_NEAR:
                            state = State.GO_FORWARD_FROM_FRONT_JEWEL;

                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                            break;

                        case BLUE_NEAR:
                            state = State.GO_FORWARD_FROM_FRONT_JEWEL;

                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                            break;

                        case RED_FAR:
                            state = State.FAR_TURN;

                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                            break;

                        case BLUE_FAR:
                            state = State.FAR_TURN;

                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                            break;
                    }
                }
                break;

            case HIT_BACK_JEWEL:
                nextStateFromFarTurn = State.GO_FORWARD_FROM_BACK_JEWEL;

                if (Math.abs(drivetrain.averageLeftEncoders()) >= Math.abs(encoderList.get(State.HIT_BACK_JEWEL))) {
                    drivetrain.setLeftPow(0.0);
                    drivetrain.setRightPow(0.0);

                    drivetrain.resetEncoders();

                    switch (fieldPosition) {

                        case RED_NEAR:
                            state = State.GO_FORWARD_FROM_BACK_JEWEL;

                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                            break;

                        case BLUE_NEAR:
                            state = State.GO_FORWARD_FROM_BACK_JEWEL;

                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                            break;

                        case RED_FAR:
                            state = State.FAR_TURN;

                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                            break;

                        case BLUE_FAR:
                            state = State.FAR_TURN;

                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                            break;
                    }
                }


                break;
            case FAR_TURN:
                if (Math.abs(drivetrain.averageLeftEncoders()) >= Math.abs(encoderList.get(State.FAR_TURN))) {
                    drivetrain.setLeftPow(0.0);
                    drivetrain.setRightPow(0.0);

                    drivetrain.resetEncoders();
                    state = nextStateFromFarTurn;

                    switch (fieldPosition) {
                        case RED_FAR:
                            drivetrain.setLeftPow(OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(OpModeConstants.MOTOR_SPEED);
                            break;

                        case BLUE_FAR:
                            drivetrain.setLeftPow(-OpModeConstants.MOTOR_SPEED);
                            drivetrain.setRightPow(-OpModeConstants.MOTOR_SPEED);
                            break;
                    }
                }
                break;

            case GO_FORWARD_FROM_BACK_JEWEL:
                if (Math.abs(drivetrain.averageLeftEncoders()) >= Math.abs(encoderList.get(State.GO_FORWARD_FROM_BACK_JEWEL))) {
                    drivetrain.setLeftPow(0.0);
                    drivetrain.setRightPow(0.0);

                    drivetrain.resetEncoders();
                    state = State.PARK_AND_END;
                }
                break;

            case GO_FORWARD_FROM_FRONT_JEWEL:
                if (Math.abs(drivetrain.averageLeftEncoders()) >= Math.abs(encoderList.get(State.GO_FORWARD_FROM_FRONT_JEWEL))) {
                    drivetrain.setLeftPow(0.0);
                    drivetrain.setRightPow(0.0);

                    drivetrain.resetEncoders();
                    state = State.PARK_AND_END;
                }
                break;
            case PARK_AND_END:
                break;
        }

        telemetry.addData("State", state);
        telemetry.addData("Encoder listing", encoderList.get(state));
        telemetry.addData("\nLeft Encoder",drivetrain.leftEncVal);
        telemetry.addData("Right Encoder",drivetrain.rightEncVal);
        telemetry.addData("Average Encoder",(drivetrain.leftEncVal + drivetrain.rightEncVal) / 2 );
        telemetry.addData("\nRed", colorSensor.red());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("\nArm",arm.getPosition());

    }

    @Override
    public void stop() {
        super.stop();
        try {
            jsonAutonGetter.close();
        } catch (IOException e) {
            telemetry.addLine(e.getMessage());
        }
    }
}
