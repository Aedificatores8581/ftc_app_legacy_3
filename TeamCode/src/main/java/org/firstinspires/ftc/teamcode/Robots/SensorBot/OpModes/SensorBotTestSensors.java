package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Components.Sensors.MagneticLimitSwitch;
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
	private TouchSensor localTouchSensor = new TouchSensor();
	private MagneticLimitSwitch localMagenteticSensor = new MagneticLimitSwitch();
	private REVColorDistanceSensor localColorDistanceSensor = new REVColorDistanceSensor();

	public enum testingSensor {
		TOUCH,
		MAGNET,
		COLOR,
		DISTANCE,
		NONE,
	}

	public static testingSensor currentlyTestedSensor = testingSensor.NONE;

	@Override
	public void init() {

		super.init();
		updateGamepad1();
		setRobotAngle();

		// Pretty pedantic, but they're not doing any harm.
		// Remove them?
		drivetrain.controlState = TankDT.ControlState.ARCADE;
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

		localColorDistanceSensor.updateColorSensor();

		if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
			switch (currentlyTestedSensor) {
				case TOUCH:
					currentlyTestedSensor = testingSensor.MAGNET;
					break;

				case MAGNET:
					currentlyTestedSensor = testingSensor.COLOR;
					break;

				case COLOR:
					currentlyTestedSensor = testingSensor.DISTANCE;
					break;

				case DISTANCE:
					currentlyTestedSensor = testingSensor.NONE;
					break;

				case NONE:
					currentlyTestedSensor = testingSensor.TOUCH;
					// Wraps around.
					break;
			}
		} else if (gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER) {
			switch (currentlyTestedSensor) {
				case TOUCH:
					// Wraparound is here.
					currentlyTestedSensor = testingSensor.NONE;
					break;

				case MAGNET:
					currentlyTestedSensor = testingSensor.TOUCH;
					break;

				case COLOR:
					currentlyTestedSensor = testingSensor.MAGNET;
					break;

				case DISTANCE:
					currentlyTestedSensor = testingSensor.COLOR;
					break;

				case NONE:
					currentlyTestedSensor = testingSensor.DISTANCE;
					// Wraps around.
					break;
			}
		}

		switch (currentlyTestedSensor) {
			case TOUCH:
				// Touch sensor.
				if (localTouchSensor.isPressed()) {telemetry.addData("Touch Sensor", "Pressed");}
				else {telemetry.addData("Touch Sensor", "Not Pressed");}
				break;

			case MAGNET:
				// Magnet Sensor.
				if (localMagenteticSensor.isActivated()) {telemetry.addData("Magnetic Sensor", "Pressed");}
				else {telemetry.addData("Magnetic Sensor", "Not Pressed");}
				break;

			case COLOR:
				telemetry.addData("r", localColorDistanceSensor.getRed());
				telemetry.addData("g", localColorDistanceSensor.getGreen());
				telemetry.addData("b", localColorDistanceSensor.getBlue());
				telemetry.addData("a", localColorDistanceSensor.getOpacity());
				break;

			case DISTANCE:
				telemetry.addData("Distace in cm.", localColorDistanceSensor.getDistanceCM());
				break;

			case NONE:
				telemetry.addData("Sensors","Currently testing nothing...");
				break;

			default:
				telemetry.addData("Error!","Enum inaccessible.");
				break;
		}
	}
}