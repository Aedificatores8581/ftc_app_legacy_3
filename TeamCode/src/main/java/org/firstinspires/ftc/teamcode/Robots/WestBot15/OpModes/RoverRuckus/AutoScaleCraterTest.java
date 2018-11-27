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
 * This is a test, among many.
 */

@Autonomous(name = "ScaleCrater", group = "Auto Testing")
public class AutoScaleCraterTest extends WestBot15 {
    MotoG4 motoG4;
    GyroAngles gyroAngles;
    Orientation angle;

    // 100 is a temporary value.
    // TODO: This needs to be tuned.
    private static double onCraterRimThreshold = 0.20;
    private static final double CRATER_ANGLE_ADJUSTMENT_INCREMENT = 0.01;
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

        if (getGyroAngleY() > onCraterRimThreshold) {
            onCrater = true;
        } else {
            onCrater = false;
        }

        // TEMP
        if (gamepad1.left_bumper) {
            onCraterRimThreshold -= CRATER_ANGLE_ADJUSTMENT_INCREMENT;
        } else if (gamepad1.right_bumper) {
            onCraterRimThreshold += CRATER_ANGLE_ADJUSTMENT_INCREMENT;
        }

        //if (!onCrater) {
		//	drivetrain.setRightPow(0.2);
		//	drivetrain.setLeftPow(0.2);
		//}

        drivetrain.updateEncoders();

        telemetry.addData("Rim Threshold", onCraterRimThreshold);
        telemetry.addData("onCrater?", onCrater);
        telemetry.addData("Robot Y", getGyroAngleY());
        //telemetry.addData("Robot X", getGyroAngleX());
        //telemetry.addData("Robot Z", getGyroAngle());
    }
}
