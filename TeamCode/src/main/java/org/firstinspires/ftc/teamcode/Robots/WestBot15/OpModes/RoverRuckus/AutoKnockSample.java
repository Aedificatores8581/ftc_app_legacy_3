package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Vision.Detectors.BlockDetector;
import org.firstinspires.ftc.teamcode.Vision.TennisBallDetector;
import org.opencv.core.Point;
import org.opencv.core.Point3;

import ftc.vision.Detector;

/**
 * Written 13/10/18 by Theodore Lovinski.
 *
 * Tested: No
 */

@Autonomous(name = "KnockSample", group = "Auto Testing")
public class AutoKnockSample extends WestBot15 {
    BlockDetector detector;
    Point sampleLocation;
    MotoG4 motoG4;
    Pose desiredPose;

    @Override
    public void init() {
        msStuckDetectInit = 500000;

        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation(new Point3(0, 0, 12), new Point3(0, 0, 0));

        detector = new BlockDetector();
        detector.isInitialized = true;
        detector.opState = Detector.OperatingState.DETECTING;

        FtcRobotControllerActivity.frameGrabber.detector = this.detector;

        desiredPose = new Pose();

        super.init();
        activateGamepad1();
    }

    @Override
    public void start() { super.start(); }

    @Override
    public void loop() {
        // updateGamepad1();

        Vector2 _elementVector = new Vector2(detector.element.y + 320, -detector.element.x - 240);

        double adjustedVerticalAngle = _elementVector.y / 640 * motoG4.verticalAngleOfViewRear();
        double adjustedHorizontalAngle = _elementVector.x / 480 * motoG4.horizontalAngleOfViewRear();

        double locationPointY = 11 / Math.tan(adjustedVerticalAngle);
        double locationPointX = locationPointY * Math.tan(adjustedHorizontalAngle);
        Point locationPoint = new Point(locationPointX, locationPointY);

        drivetrain.driveToPoint(locationPoint.x, locationPoint.y, Drivetrain.Direction.FOR);

        // TODO: find some way to tell that the block has been knocked.
        // Talk to Frank about how to do this.
        // At the moment, just detect the floor, and if red or blue is detected,
        // then the task is complete.

        telemetry.addData("Image Size", detector.workingImage.size());
    }

    public void stop() {
        super.stop();
        detector.isInitialized = false;
    }
}