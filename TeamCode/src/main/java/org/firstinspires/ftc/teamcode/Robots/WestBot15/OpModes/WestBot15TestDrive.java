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

        drivetrain.controlState = TankDT.ControlState.ARCADE;
        drivetrain.direction = TankDT.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        updateGamepad1();
        refreshStartAngle();
        setRobotAngle();
        drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
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
        telemetry.addData("angle2", (drivetrain.averageRightEncoders() - drivetrain.averageLeftEncoders()) / (drivetrain.ENC_PER_INCH * drivetrain.DISTANCE_BETWEEN_WHEELS));
    }

    public void refreshStartAngle(){
        if(gamepad1.y){
            startAngle = Math.toDegrees(leftStick1.angleBetween(robotAngle));

            leftStick1.x = 0;
            leftStick1.y = 0;
        }
    }
}
