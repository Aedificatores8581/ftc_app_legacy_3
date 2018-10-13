package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.RobitBot;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.*;
import static org.firstinspires.ftc.teamcode.Universal.UniversalFunctions.clamp;
@Disabled
@TeleOp(name = "Robit TeleOp", group = "robit")
public class RobitTeleOp extends RobitBot {

    double leftGrabberPosition, rightGrabberPosition;

    final static double MAX_SERVO_VALUE = 0.47;

    final static double MIN_SERVO_VALUE = 0.19;


    @Override
    public void init() {
        super.init();
        drivetrain.controlState = TankDT.ControlState.TANK;
        leftGrabberPosition = 0.5;
        rightGrabberPosition = 0.2;
    }

    @Override
    public void start() {
        super.start();

    }

    @Override
    public void loop() {
        updateGamepad1();

        try {
            drivetrain.teleOpLoop(leftStick1, rightStick1, new Vector2(0,0));
        } catch (NullPointerException e) {
            telemetry.addLine(e.getMessage());
        }

        if (gamepad1.dpad_up) {
            lift.setPower(-1.0);
        } else if(gamepad1.dpad_down) {
            lift.setPower(1.0);
        } else {
            lift.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            leftGrabberPosition -= 0.01;
        } else if (gamepad1.left_trigger > Triggered.TRIGGER) {
            leftGrabberPosition += 0.01;
        }

        if (gamepad1.right_bumper) {
            rightGrabberPosition += 0.01;
        } else if (gamepad1.right_trigger > Triggered.TRIGGER) {
            rightGrabberPosition -= 0.01;
        }

        leftGrabberPosition = clamp(MIN_SERVO_VALUE, leftGrabberPosition, MAX_SERVO_VALUE);
        rightGrabberPosition = clamp(MIN_SERVO_VALUE, rightGrabberPosition, MAX_SERVO_VALUE);



        arm.setPosition(0.0);

        leftGrabber.setPosition(leftGrabberPosition);
        rightGrabber.setPosition(rightGrabberPosition);

        telemetry.addData("Right Grabber", rightGrabber.getPosition());
        telemetry.addData("Right Grabber Value", rightGrabberPosition);

        telemetry.addData("\nLeft Grabber", leftGrabber.getPosition());
        telemetry.addData("Left Grabber Value", leftGrabberPosition);
    }

}
