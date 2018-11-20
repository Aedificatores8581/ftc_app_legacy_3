package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.GyroAngles;
import org.opencv.core.Point3;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

@Autonomous(name = "ScaleCraterTuner", group = "Auto Testing")
public class AutoScaleCraterTune extends WestBot15 {
    MotoG4 motoG4;
    GyroAngles gyroAngles;
    Orientation angle;

    // 100 is a temporary value.
    // TODO: This needs to be tuned.
    private final static int ON_CRATER_RIM_THRESHOLD = 60;
    private       static int on_crater_rim_tuner = 60;
    private final static int TUNING_INCREMENT = 2;
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
        getGyroAngle();
        updateGamepad1();

        if (gamepad1.left_bumper) {
        	on_crater_rim_tuner += TUNING_INCREMENT;
        } else {
        	on_crater_rim_tuner -= TUNING_INCREMENT;
		}

        if (Math.abs(gyroAngles.getY()) > on_crater_rim_tuner) {
            onCrater = true;
        } else {
            onCrater = false;
        }

        //if (!onCrater) {
        //    drivetrain.setRightPow(0.2);
        //    drivetrain.setLeftPow(0.2);
        //}

        //drivetrain.updateEncoders();

        telemetry.addData("Tuner", on_crater_rim_tuner);
        telemetry.addData("onCrater?", onCrater);
        telemetry.addData("Robot Y", gyroAngles.getY());
        telemetry.addData("Robot X", gyroAngles.getX());
        telemetry.addData("Robot Z", gyroAngles.getZ());
    }
}
