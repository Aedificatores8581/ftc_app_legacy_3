package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

@TeleOp(name = "motorTest")
public class MotorTest extends WestBot15 {

    public void init() {
        super.init();
        activateGamepad1();
    }
    public void loop(){
        drivetrain.maxSpeed = 0.5;
        updateGamepad1();
        if(gamepad1.left_stick_button)
            drivetrain.spicyDrive(leftStick1, gamepad1.right_trigger, gamepad1.left_trigger);
        else
            drivetrain.spicyDrive(leftStick1, gamepad1.right_trigger, gamepad1.left_trigger);

        telemetry.addData("lfpow: ", drivetrain.leftFore.getPower());
        telemetry.addData("lrpow: ", drivetrain.leftRear.getPower());
        telemetry.addData("rfpow: ", drivetrain.rightFore.getPower());
        telemetry.addData("rrpow: ", drivetrain.rightRear.getPower());
        telemetry.addData("leftPow", drivetrain.leftPow);
        telemetry.addData("rightPow", drivetrain.rightPow);

    }

}