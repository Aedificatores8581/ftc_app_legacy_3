package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Components.Sensors.REVColorDistanceSensor;
import org.firstinspires.ftc.teamcode.Components.Sensors.TouchSensor;
import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;

/**
 *
 * Writ by Þeodore Lovinski on 06/25/2018.
 *
 * This is meant to be a TeleOp mode in which the sensors are to be tested, making sensorbot actually
 * fulfill its title. (Note: has much fewer control states.)
 *
 */

@TeleOp(name = "SensorBot Sensor Tests", group = "SensorBot")
public class SensorBotTestSensors extends SensorBot {
	int currentlyTestingSensor = 0;

	@Override
	public void init() {
		super.init();
		updateGamepad1();
		setRobotAngle();

		// TODO: Do I need these?
		drivetrain.controlState = TankDT.ControlState.ARCADE;
		drivetrain.turnState = TankDT.FCTurnState.FAST;
		drivetrain.direction = Drivetrain.Direction.FOR;
	}

	@Override
	public void start() {super.start();}

	@Override
	public void loop() {
		updateGamepad1();
		setRobotAngle();
		drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
		rm.setPower(drivetrain.rightPow);
		lm.setPower(drivetrain.leftPow);

		if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)     {currentlyTestingSensor += 1;}
		else if (gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER) {currentlyTestingSensor -= 1;}

		// TODO: Make this OpMode not pointless.
		switch (currentlyTestingSensor) {
			case 1:


			case 2:

		}
	}
}
