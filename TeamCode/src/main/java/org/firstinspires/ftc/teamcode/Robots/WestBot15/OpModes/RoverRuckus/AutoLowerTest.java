package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Lift;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

public class AutoLowerTest extends WestBot15 {
	MotoG4 MotoG4;
	Lift instanceLift;
	Lift.RatchetState ratchetState;

	@Override
	public void init() {
		msStuckDetectInit = MS_STUCK_DETECT_INIT_DEFAULT;

		ratchetState = Lift.RatchetState.UP;

	}

	@Override
	public void start() { super.start(); }

	@Override
	public void loop() {

	}
}
