package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

/**
 * Created by Frank Portman on 6/1/2018
 */
@Disabled
@TeleOp(name = "Sensor Bot Test Drive", group = "SensorBot")
public class SensorBotTestDrive extends SensorBot {
    boolean switchControlState    = false,
            canSwitchControlState = false,
            switchTurnState       = false,
            canSwitchTurnState    = false;

    @Override
    public void init(){
        super.init();
        activateGamepad1();
        drivetrain.controlState = TankDT.ControlState.TANK;
        drivetrain.turnState = TankDT.FCTurnState.FAST;
        drivetrain.direction = Drivetrain.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        updateGamepad1();
        setRobotAngle();
        drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
        rm.setPower(drivetrain.rightPow);
        lm.setPower(drivetrain.leftPow);

        switch(drivetrain.controlState){
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

            case TANK:
                if (switchControlState) {
                    drivetrain.controlState = TankDT.ControlState.ARCADE;
                    switchControlState = false;
                    canSwitchControlState = false;
                    drivetrain.directionMult = 1;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchControlState = false;
                    canSwitchControlState = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
        }

        telemetry.addData("control State", drivetrain.controlState);
        telemetry.addData("fcTurnState", drivetrain.turnState);
        telemetry.addData("leftvect1", leftStick1);
        telemetry.addData("leftPower", drivetrain.leftPow);
        telemetry.addData("rightPower", drivetrain.rightPow);
        telemetry.addData("realLeftPower", lm.getPower());
        telemetry.addData("realRightPower", rm.getPower());
    }
}
