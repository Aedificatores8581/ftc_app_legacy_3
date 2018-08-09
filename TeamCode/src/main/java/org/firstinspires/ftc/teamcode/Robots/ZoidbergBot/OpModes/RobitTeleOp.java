package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.RobitBot;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

@TeleOp(name = "Robit TeleOp", group = "robit")
public class RobitTeleOp extends RobitBot {

    @Override
    public void init() {
        super.init();
        drivetrain.controlState = TankDT.ControlState.ARCADE;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        updateGamepad1();
        setRobotAngle();

        try {
            drivetrain.teleOpLoop(leftStick1, rightStick1, new Vector2(0,0));
        } catch (NullPointerException e) {
            telemetry.addLine(e.getMessage());
        }

        if (gamepad1.dpad_up) {
            lift.setPower(1.0);
        } else if(gamepad1.dpad_down) {
            lift.setPower(1.0);
        } else {
            lift.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            leftGrabber.setPosition(leftGrabber.getPosition() - 0.01);
            rightGrabber.setPosition(rightGrabber.getPosition() + 0.01);

        } else if (gamepad1.right_bumper) {
            leftGrabber.setPosition(leftGrabber.getPosition() - 0.01);
            rightGrabber.setPosition(rightGrabber.getPosition() + 0.01);
        } else {
            leftGrabber.setPosition(leftGrabber.getPosition());
            rightGrabber.setPosition(rightGrabber.getPosition());

        }

    }
}
