package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Lift;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

public class AutoLowerTest extends WestBot15 {
	private static final double MAX_HEIGHT = 2.0;
	private static final double POWER = .5;

	MotoG4 motoG4;
	Lift lift;

	@Override
	public void init() {
		msStuckDetectInit = MS_STUCK_DETECT_INIT_DEFAULT;

		lift = new Lift(Lift.RatchetState.UP);
		lift.init(hardwareMap);

	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void loop() {
		if (Math.abs(lift.getHeight()) < MAX_HEIGHT) {
			lift.setPower(POWER);
		} else {
			stop();
		}
	}
}
