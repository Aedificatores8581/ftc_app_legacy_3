package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Vision.Detectors.BlockDetector;
import org.firstinspires.ftc.teamcode.Vision.Detectors.GenericDetector;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Size;

import ftc.vision.Detector;

@Autonomous(name = "block detector test", group = "none")
public class VisionTest extends WestBot15 {
    BlockDetector detector;
    Point sampleLocation;
    MotoG4 motoG4;

    public void init(){
        msStuckDetectInit = 500000;
        super.init();
        activateGamepad1();
        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation(new Point3(0, 0, 12), new Point3(0, 0, 0));
        detector = new BlockDetector();
        detector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = detector;
    }
    public void initLoop(){
        telemetry.addData("location 1", motoG4.rearCamera.getObjectLocation(detector.elements.get(0), detector.result().size(), 2));
    }
    @Override
    public void start(){
        super.start();

    }

    public void loop(){

        Vector2 temp = new Vector2(detector.element.y, -detector.element.x);
        temp.x -= 480/ 2;
        temp.y += 640 / 2;

        double vertAng = temp.y / 640 * motoG4.rearCamera.verticalAngleOfView();
        double horiAng = temp.x / 480 * motoG4.rearCamera.horizontalAngleOfView();

        double newY = (12 - 2 / 2) / Math.tan(vertAng);
        double newX = newY * Math.tan(horiAng);
        Point newPoint = new Point(newX, newY);

        telemetry.addData("location 1", (int)(100 * newPoint.x) / 100.0 + ", " + (int)(100 * newPoint.y) / 100.0);
        telemetry.addData("center", (float)detector.element.x + ", " + (float)detector.element.y);
        telemetry.addData("size", detector.workingImage.size());
        telemetry.addData("temp", temp);
    }

    public void stop(){
        super.stop();
        detector.isInitialized = false;
    }
}
