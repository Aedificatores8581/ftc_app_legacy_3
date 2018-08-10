package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15Inc;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

/**
 * Created by Frank Portman on 6/13/2018
 */
//This is a version of WestCoast15TestDrive with incremental motors
@TeleOp(name = "WCD15 Incremental Test Drive")
public class WestBot15IncrementalTestDrive extends WestBot15Inc {
    boolean switchControlState    = false,
            canSwitchControlState = false,
            switchTurnState       = false,
            canSwitchTurnState    = false;
    @Override
    public void init(){
        super.init();
        activateGamepad1();
        drivetrain.controlState = TankDT.ControlState.FIELD_CENTRIC;
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
        refreshStartAngle();
        setRobotAngle();
        drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
        switch(drivetrain.controlState){
            case ARCADE:
                if(switchControlState){
                    drivetrain.controlState = drivetrain.controlState.FIELD_CENTRIC;
                    switchControlState = false;
                    canSwitchControlState = false;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
            case FIELD_CENTRIC:
                switch(drivetrain.turnState){
                    case FAST:
                        if(switchTurnState){
                            drivetrain.turnState = TankDT.FCTurnState.SMOOTH;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                    case SMOOTH:
                        if(switchTurnState){
                            drivetrain.turnState = TankDT.FCTurnState.FAST;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                }
                if(switchControlState){
                    drivetrain.controlState = TankDT.ControlState.TANK;
                    switchControlState = false;
                    canSwitchControlState = false;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER){
                    switchControlState = false;
                    canSwitchControlState = true;
                }
                else if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                    switchControlState = true;
                break;
            case TANK:
                if(switchControlState){
                    drivetrain.controlState = TankDT.ControlState.FIELD_CENTRIC_VECTOR;
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
            case FIELD_CENTRIC_VECTOR:
                switch(drivetrain.turnState){
                    case FAST:
                        if(switchTurnState){
                            drivetrain.turnState = TankDT.FCTurnState.SMOOTH;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                    case SMOOTH:
                        if(switchTurnState){
                            drivetrain.turnState = TankDT.FCTurnState.FAST;
                            switchTurnState = false;
                            canSwitchTurnState = false;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                            switchTurnState = false;
                            canSwitchTurnState = true;
                        }
                        else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                            switchTurnState = true;
                        break;
                }

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
        }

    }
    @Override
    public void stop(){
        drivetrain.terminate();
        super.stop();
    }
    public void refreshStartAngle(){
        if(gamepad1.left_stick_button){
            startAngle = Math.toDegrees(leftStick1.angleBetween(robotAngle));
            leftStick1.x = 0;
            leftStick1.y = 0;
        }
    }
}
