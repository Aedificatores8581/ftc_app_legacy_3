package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Intake;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Lift;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

//@TeleOp(name = "teleop")
public class RoverRuckusTeleOp extends WestBot15 {
    boolean canSwitch = false;
    boolean markerDropped = false;
    boolean engame = false;
    boolean ended = false;
    double prevRightStick1;
    double ratchetTimer = 0;
    @Override
    public void init(){
        isAutonomous = false;
        usingIMU = false;
        super.init();
        activateGamepad1();
        activateGamepad2();

    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop(){
        updateGamepad1();
        updateGamepad2();

        if(gamepad2.left_stick_button && gamepad2.right_stick_button)
            engame = true;

        drivetrain.mildSpicyDrive(leftStick1, gamepad1.left_trigger, gamepad1.right_trigger);

        intaek.intakeMode = gamepad2.left_bumper ? Intake.IntakeMode.GOLD : Intake.IntakeMode.SILVER;
        intaek.setModePower(gamepad2.left_trigger);
        intaek.dispense();

        if(!engame) {
            double aextensionSpeed = (gamepad1.left_bumper ? 1 : 0) - (gamepad1.right_bumper ? -1 : 0);
            aextendo.aextendTM(aextensionSpeed);
            if (leftStick1.magnitude() > UniversalConstants.Triggered.STICK)
                aextendo.articulateUp();
            else
                aextendo.articulateDown();
            if(gamepad2.x)
                intaek.dispense();
            if(gamepad1.left_stick_button)
                drivetrain.maxSpeed = 0.95;
            else
                drivetrain.maxSpeed = 0.5 + 3 * rightStick1.x;
        }
        else {
            drivetrain.maxSpeed = 0.3;
            aextendo.aextendTM(-1);
            aextendo.articulateUp();
            intaek.dispense();
            if(gamepad2.right_trigger > UniversalConstants.Triggered.TRIGGER && gamepad2.left_trigger > UniversalConstants.Triggered.TRIGGER) {
                ended = true;
                lift.stop();
            }
            else {
                if (Math.signum(prevRightStick1) != Math.signum(prevRightStick1))
                    lift.switchRatchetState();
                lift.setPowerOverride(rightStick1.magnitude());
                prevRightStick1 = rightStick1.magnitude();
            }
        }

    }
}
