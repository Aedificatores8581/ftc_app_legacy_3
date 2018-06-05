package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 6/1/2018
 */

@TeleOp(name = "Sensor Bot Test Drive", group = "SensorBot")
public class SensorBotTestDrive extends SensorBot {
    boolean switchControlState    = false,
            canSwitchControlState = false,
            switchTurnState       = false,
            canSwitchTurnState    = false;

    public double turnMult = 0,
                  rightPow = 0,
                  leftPow = 0;

    @Override
    public void init(){
        super.init();
        activateGamepad1();
        drivetrain.controlState = TankDT.ControlState.ARCADE;
        drivetrain.turnState = TankDT.FCTurnState.FAST;
        drivetrain.direction = Drivetrain.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    public void loop(){
        updateGamepad1();
        setRobotAngle();
        // TODO drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
        switch(drivetrain.controlState) {
            case ARCADE:
                if (switchControlState) {
                    drivetrain.controlState = drivetrain.controlState.FIELD_CENTRIC;
                    switchControlState = false;
                    canSwitchControlState = false;
                    drivetrain.directionMult = 1;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchControlState = false;
                    canSwitchControlState = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;

                turnMult = 1; // - leftStick1 * (1 - maxTurn); Removed for debugging purposes.
                leftPow = -rightStick1.y - turnMult * rightStick1.x;
                rightPow = -leftStick1.y + turnMult * rightStick1.x;
                break;

            case FIELD_CENTRIC:
                switch (drivetrain.turnState) {
                    case FAST:
                        if (switchTurnState) {
                            drivetrain.turnState = TankDT.FCTurnState.SMOOTH;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        } else if (gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER) {
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        } else if (gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;

                    case SMOOTH:
                        if (switchTurnState) {
                            drivetrain.turnState = TankDT.FCTurnState.FAST;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        } else if (gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER) {
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        } else if (gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                }

                if (switchControlState) {
                    drivetrain.controlState = TankDT.ControlState.TANK;
                    switchControlState = false;
                    canSwitchControlState = false;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchControlState = false;
                    canSwitchControlState = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
                // TEMP: Missing behaviors, for debugging purposes.

            case TANK:
                if (switchControlState) {
                    drivetrain.controlState = TankDT.ControlState.FIELD_CENTRIC_VECTOR;
                    switchControlState = false;
                    canSwitchControlState = false;
                    drivetrain.directionMult = 1;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchControlState = false;
                    canSwitchControlState = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;

                leftPow = -leftStick1.y;
                rightPow = -rightStick1.y;
                break;

            case FIELD_CENTRIC_VECTOR:
                if(switchControlState){
                    drivetrain.controlState = TankDT.ControlState.ARCADE;
                    switchControlState = false;
                    canSwitchControlState = false;
                    drivetrain.directionMult = 1;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
                // TEMP: Missing behaviors, for debugging purposes.
        }
        drivetrain.setLeftPow(leftPow);
        drivetrain.setRightPow(rightPow);

        telemetry.addData("control State", drivetrain.controlState);
        telemetry.addData("fcTurnState", drivetrain.turnState);
        telemetry.addData(" ", " ");
        telemetry.addData("leftvec1", leftStick1);
        telemetry.addData("leftPower", leftPow);
        telemetry.addData("rightPower", rightPow);
    }
}
