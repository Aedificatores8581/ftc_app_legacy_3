package org.firstinspires.ftc.teamcode.Universal;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

import java.util.concurrent.TimeUnit;

/**
 * Written by Theodore Lovinski, 08/11/2018
 *
 * And this is NOT TESTED.
 *
 */

// TODO: Test!

public class ConsecutiveButtonPress {
	private ElapsedTime timer;
	private Gamepad gamepad1;
	private double startTime;
	private int pressesSatisfied = 0;

	public boolean ConsecutiveTrigger(int pressesDesired, double maxInterval, boolean value) {
		pressesSatisfied = 0;

		startTime = timer.now(TimeUnit.MILLISECONDS);

		if (timer.now(TimeUnit.MILLISECONDS) - startTime > maxInterval) {
			if (gamepad1.left_bumper) {
				pressesSatisfied += 1;
				if (pressesSatisfied == pressesDesired) { return true; }
			}
		} else {
			return false;
		}

		return pressesDesired == pressesSatisfied;
	}
}
