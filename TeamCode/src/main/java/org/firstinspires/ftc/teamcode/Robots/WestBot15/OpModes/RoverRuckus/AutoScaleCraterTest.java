package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.Robot;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.GyroAngles;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.opencv.core.Point3;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

/**
 * Written by Theo Lovinski, 5/11/2018.
 *
 * This is a test, it should be coupled with other routines in autonomous.
 */

@Autonomous(name = "ScaleCrater", group = "Auto Testing")
public class AutoScaleCraterTest extends WestBot15 {
    MotoG4 motoG4;
    GyroAngles GyroAngles;

    // 100 is a temporary value.
    // TODO: This needs to be tuned.
    private final static int ON_CRATER_RIM_THRESHOLD = 100;
    public boolean onCrater = false;

    @Override
    public void init() {
        msStuckDetectInit = MS_STUCK_DETECT_INIT_DEFAULT;
        normalizeGyroAngle();
        setStartAngle();

        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation(
                new Point3(0, 0, 12),
                new Point3(0, 0, 0)
        );
    }

    @Override
    public void start() { super.start(); }

    @Override
    public void loop() {
        if (Math.abs(GyroAngles.getZ()) > ON_CRATER_RIM_THRESHOLD) {
            onCrater = true;
        } else {
            onCrater = false;
        }

        if (onCrater = false) {
			drivetrain.setRightPow(1.0);
			drivetrain.setLeftPow(1.0);
		}

        drivetrain.updateEncoders();

        telemetry.addData("onCrater?", onCrater);
        telemetry.addData("Robot Z", GyroAngles.getZ());
        telemetry.addData("Robot X", GyroAngles.getX());
        telemetry.addData("Robot Y", GyroAngles.getY());
    }
}
