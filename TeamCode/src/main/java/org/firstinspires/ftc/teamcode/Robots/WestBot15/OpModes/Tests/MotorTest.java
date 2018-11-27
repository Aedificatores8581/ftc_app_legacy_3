package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

@TeleOp(name = "motorTest")
public class MotorTest extends WestBot15 {

    public void init() {
        super.init();
        activateGamepad1();
    }
    public void loop(){
        drivetrain.maxSpeed = 0.3;
        updateGamepad1();
        Vector2 newLeftStick1 = new Vector2(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        if(gamepad1.right_stick_button)
            leftStick1 = newLeftStick1;
        if(gamepad1.left_stick_button)
            drivetrain.spicyDrive(leftStick1, gamepad1.right_trigger, gamepad1.left_trigger);
        else
            drivetrain.spicyDrive2(leftStick1, gamepad1.right_trigger, gamepad1.left_trigger);

        telemetry.addData("left fore: ", drivetrain.leftFore.getPower());
        telemetry.addData("left rear: ", drivetrain.leftRear.getPower());
        telemetry.addData("right fore: ", drivetrain.rightFore.getPower());
        telemetry.addData("right rear: ", drivetrain.rightRear.getPower());

        telemetry.addData("left fore pos: ", drivetrain.leftFore.getCurrentPosition());
        telemetry.addData("left rear pos: ", drivetrain.leftRear.getCurrentPosition());
        telemetry.addData("right fore pos: ", drivetrain.rightFore.getCurrentPosition());
        telemetry.addData("right rear pos: ", drivetrain.rightRear.getCurrentPosition());

        telemetry.addData("leftPow", drivetrain.leftPow);
        telemetry.addData("rightPow", drivetrain.rightPow);
        telemetry.addData("isSpicyDrive2", gamepad1.left_stick_button);
        telemetry.addData("leftStick", leftStick1);
        telemetry.addData("leftSticky", gamepad1.left_stick_y);

    }

}