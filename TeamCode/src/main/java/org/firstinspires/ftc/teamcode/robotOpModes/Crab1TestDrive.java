package org.firstinspires.ftc.teamcode.robotOpModes;

import org.firstinspires.ftc.teamcode.robotTemplates.TankDT;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;

public class Crab1TestDrive extends Crab1 {
    boolean switchDriveMode            = false,
            canSwitchDriveMode         = false,
            switchWheelRotationMode    = false,
            canSwitchWheelRotationMode = false;
    double  rt,
            lt;
    @Override
    public void init(){
        super.init();
        TankDrive.init();
        TankDrive.controlState = TankDT.ControlState.ARCADE;
        TankDrive.direction = TankDT.Direction.FOR;
        TankDrive.setSpeed(1);
        activateGamepad1();
        activateGamepad2();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop() {
        super.loop();
        lt = gamepad1.left_trigger;
        switch (driveMode) {
            case SWERVE:
                rt = gamepad1.right_trigger;
                switch(wheelRotationMode){
                    case FAST:
                        if (switchWheelRotationMode) {
                            wheelRotationMode = WheelRotationMode.SMOOTH;
                            switchWheelRotationMode = false;
                            canSwitchWheelRotationMode = false;
                            directionMult = 1;
                        } else if (rt < UniversalConstants.Triggered.TRIGGER) {
                            switchWheelRotationMode = false;
                            canSwitchWheelRotationMode = true;
                        } else if (rt > UniversalConstants.Triggered.TRIGGER && canSwitchDriveMode)
                            switchWheelRotationMode = true;
                        break;
                    case SMOOTH:
                        if (switchWheelRotationMode) {
                            wheelRotationMode = WheelRotationMode.FAST;
                            switchWheelRotationMode = false;
                            canSwitchWheelRotationMode = false;
                            directionMult = 1;
                        } else if (rt < UniversalConstants.Triggered.TRIGGER) {
                            switchWheelRotationMode = false;
                            canSwitchWheelRotationMode = true;
                        } else if (rt > UniversalConstants.Triggered.TRIGGER && canSwitchDriveMode)
                            switchWheelRotationMode = true;
                        break;
                }
                if (switchDriveMode) {
                    driveMode = DriveMode.TANK;
                    switchDriveMode = false;
                    canSwitchDriveMode = false;
                    directionMult = 1;
                } else if (lt < UniversalConstants.Triggered.TRIGGER) {
                    switchDriveMode = false;
                    canSwitchDriveMode = true;
                } else if (lt > UniversalConstants.Triggered.TRIGGER && canSwitchDriveMode)
                    switchDriveMode = true;
                break;
            case TANK:
                if (switchDriveMode) {
                    driveMode = DriveMode.SWERVE;
                    switchDriveMode = false;
                    canSwitchDriveMode = false;
                    directionMult = 1;
                } else if (lt < UniversalConstants.Triggered.TRIGGER) {
                    switchDriveMode = false;
                    canSwitchDriveMode = true;
                } else if (lt > UniversalConstants.Triggered.TRIGGER && canSwitchDriveMode)
                    switchDriveMode = true;
                break;
        }
    }
}
