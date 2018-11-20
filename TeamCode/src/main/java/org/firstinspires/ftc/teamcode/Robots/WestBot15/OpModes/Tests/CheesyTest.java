package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

@TeleOp(name = "Cheesy Test Drive", group = "wcd")
public class CheesyTest extends WestBot15 {
    @Override
    public void init() {
        super.init();
        activateGamepad1();

        drivetrain.direction = TankDT.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        updateGamepad1();

        if (gamepad1.left_stick_button) {
			drivetrain.maxSpeed = 1;
		} else {
            drivetrain.maxSpeed = gamepad1.left_trigger * 3 / 10.0;

            if (gamepad1.left_bumper) {
				drivetrain.maxSpeed += 0.3;
			} else {
				drivetrain.maxSpeed += 0.5;
			}
        }

        double maxTurn = 1;

        if (!gamepad1.right_bumper) {
			maxTurn = 2.0 / 3;
		} else {
			maxTurn = 1;
		}

        drivetrain.turnMult = (1 - leftStick1.magnitude() * maxTurn) * gamepad1.right_trigger + leftStick1.magnitude() * (1 - gamepad1.right_trigger);
        drivetrain.leftPow = leftStick1.y + drivetrain.turnMult * rightStick1.x;
        drivetrain.rightPow = leftStick1.y - drivetrain.turnMult * rightStick1.x;
        drivetrain.setRightPow();
        drivetrain.setLeftPow();

        telemetry.addData("lfpow: ", drivetrain.leftFore.getPower());
        telemetry.addData("lrpow: ", drivetrain.leftRear.getPower());
        telemetry.addData("rfpow: ", drivetrain.rightFore.getPower());
        telemetry.addData("rrpow: ", drivetrain.rightRear.getPower());
    }
}
