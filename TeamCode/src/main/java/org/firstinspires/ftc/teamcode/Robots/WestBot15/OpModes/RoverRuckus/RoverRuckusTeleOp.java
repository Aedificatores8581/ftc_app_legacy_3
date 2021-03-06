package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

//@TeleOp(name = "teleop")
public class RoverRuckusTeleOp extends WestBot15 {
    ExtensionState extensionState = ExtensionState.NON_RESETTING;
    @Override
    public void init(){
        isAutonomous = false;
        usingIMU = false;
        super.init();
        activateGamepad1();
        activateGamepad2();
        drivetrain.controlState = TankDT.ControlState.ARCADE;

    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop(){
        updateGamepad1();
        updateGamepad2();
        
        drivetrain.draev(leftStick1, gamepad1.left_trigger, gamepad1.right_trigger);

        if(gamepad1.a)
            extensionState = ExtensionState.RESETTING;
        switch (extensionState) {
            case NON_RESETTING:
                aextendo.aextendTM(rightStick1.magnitude());
                if (gamepad1.left_bumper)
                    aextendo.articulateUp();
                else
                    aextendo.articulateDown();
                break;
            case RESETTING:
                aextendo.aextendTM(-1);
                aextendo.articulateUp();
                if (aextendo.isRetracted())
                    extensionState = ExtensionState.NON_RESETTING;
        }
        if(leftStick2.magnitude() > UniversalConstants.Triggered.STICK)
            lift2_0.lift(leftStick2.y);

        else if(gamepad2.dpad_up) {
            lift2_0.lift(1);
            mineralContainer.articulateUp();
        }
        else if(gamepad2.dpad_down) {
            lift2_0.lift(-1);
            mineralContainer.articulateDown();
        }
        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER)
            intaek.dispense();
        if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER)
            mineralContainer.openCage();
        else
            mineralContainer.closeCage();
    }
    enum ExtensionState{
        RESETTING,
        NON_RESETTING
    }
}
