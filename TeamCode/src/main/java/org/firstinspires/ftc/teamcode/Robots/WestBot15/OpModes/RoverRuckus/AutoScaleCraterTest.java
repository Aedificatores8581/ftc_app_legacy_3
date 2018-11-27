package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
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
    GyroAngles gyroAngles;
    Orientation angle;

    // 100 is a temporary value.
    // TODO: This needs to be tuned.
    private final static int ON_CRATER_RIM_THRESHOLD = 60;
    public boolean onCrater = false;

    @Override
    public void init() {
        angle = new Orientation();
        gyroAngles = new GyroAngles(angle);

        usingIMU = true;
        super.init();

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
        if (Math.abs(gyroAngles.getY()) > ON_CRATER_RIM_THRESHOLD) {
            onCrater = true;
        } else {
            onCrater = false;
        }

        if (!onCrater) {
            drivetrain.setRightPow(0.2);
            drivetrain.setLeftPow(0.2);
        }

        drivetrain.updateEncoders();

        telemetry.addData("onCrater?", onCrater);
        telemetry.addData("Robot Y", gyroAngles.getY());
    }
}
