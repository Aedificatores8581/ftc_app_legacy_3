package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.opencv.core.Point3;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

@Autonomous(name = "ScaleCrater", group = "Auto Testing")
public class AutoScaleCrater extends WestBot15 {
    MotoG4 motoG4;

    // 100 is a temporary value.
    private static final int ON_CRATER_RIM_THRESHOLD = 100;
    public static boolean onCrater = false;

    @Override
    public void init() {
        usingIMU = true;
        super.init();
        msStuckDetectInit = MS_STUCK_DETECT_INIT_DEFAULT;

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
        if (getGyroAngle() > ON_CRATER_RIM_THRESHOLD) {
            onCrater = true;
        } else {
            onCrater = false;
        }

        drivetrain.updateEncoders();
    }
}
