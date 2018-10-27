package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

/**
 * Created by Frank Portman on 6/1/2018
 */

@TeleOp(name = "West Coast 15 Test Drive", group = "West Coast 15")
public class WestBot15TestDrive extends WestBot15 {
    boolean canSwitchControlState = false;

    @Override
    public void init(){
        super.init();
        activateGamepad1();
        drivetrain.maxSpeed = 0.4;
        drivetrain.controlState = TankDT.ControlState.TANK;
        drivetrain.direction = TankDT.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        //leftIntake.setPower(0.95 * ((gamepad1.left_trigger - gamepad1.right_trigger) / 2 + 0.5));
        //rightIntake.setPower(0.95 * ((gamepad1.left_trigger - gamepad1.right_trigger) / 2 + 0.5));

        drivetrain.maxSpeed = gamepad1.left_stick_button || gamepad1.right_stick_button ? 0.98: 0.5;

        updateGamepad1();
        refreshStartAngle();
        setRobotAngle();
        drivetrain.updateEncoders();

        drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
        if(gamepad1.left_trigger > 0.2)
            drivetrain.maxSpeed = 1;
        else
            drivetrain.maxSpeed = 0.4;
        switch(drivetrain.controlState) {
            case ARCADE:
                if (!gamepad1.dpad_up && gamepad1.dpad_down)
                    canSwitchControlState = true;
                else if (gamepad1.dpad_up && canSwitchControlState) {
                    drivetrain.controlState = drivetrain.controlState.FIELD_CENTRIC;
                    canSwitchControlState = false;
                }
                else if (gamepad1.dpad_down && canSwitchControlState) {
                    drivetrain.controlState = drivetrain.controlState.TANK;
                    canSwitchControlState = false;
                }
                break;

            // TODO: Fix bad TODO.
            // TODO: \/\/\/
            //what?
            case FIELD_CENTRIC:
                if (!gamepad1.dpad_up && !gamepad1.dpad_down)
                    canSwitchControlState = true;
                else if (gamepad1.dpad_up && canSwitchControlState){
                    drivetrain.controlState = TankDT.ControlState.TANK;
                    canSwitchControlState = false;
                }
                else if (gamepad1.dpad_down && canSwitchControlState){
                    drivetrain.controlState = TankDT.ControlState.ARCADE;
                    canSwitchControlState = false;
                }
                break;

            case TANK:
                if (!gamepad1.dpad_up && !gamepad1.dpad_down)
                    canSwitchControlState = true;
                else if (gamepad1.dpad_up && canSwitchControlState) {
                    drivetrain.controlState = TankDT.ControlState.ARCADE;
                    canSwitchControlState = false;
                }
                else if (gamepad1.dpad_down && canSwitchControlState) {
                    drivetrain.controlState = TankDT.ControlState.TANK;
                    canSwitchControlState = false;
                }
                break;
        }

        telemetry.addData("control State", drivetrain.controlState);
        telemetry.addData("leftPower", drivetrain.leftPow);
        telemetry.addData("rightPower", drivetrain.rightPow);
        telemetry.addData("angle1", Math.toDegrees(robotAngle.angle()));
        telemetry.addData("angle2",
                (drivetrain.averageRightEncoders() - drivetrain.averageLeftEncoders()) /
                (drivetrain.ENC_PER_INCH * drivetrain.DISTANCE_BETWEEN_WHEELS));
        telemetry.addData("averageRightEncoders", drivetrain.averageRightEncoders());
        telemetry.addData("averageLeftEncoders", drivetrain.averageLeftEncoders());
        telemetry.addData("encoderAggregate",
                drivetrain.averageRightEncoders() - drivetrain.averageLeftEncoders());
    }

    public void refreshStartAngle(){
        if(gamepad1.y){
            startAngle = Math.toDegrees(leftStick1.angleBetween(robotAngle));

            leftStick1.x = 0;
            leftStick1.y = 0;
        }
    }
}
