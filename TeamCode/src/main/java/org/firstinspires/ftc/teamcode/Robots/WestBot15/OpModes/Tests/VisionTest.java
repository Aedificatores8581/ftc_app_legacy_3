package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.firstinspires.ftc.teamcode.Vision.Detectors.BlockDetector;
import org.opencv.core.Point;

import ftc.vision.Detector;

@Autonomous(name = "block detector test", group = "none")
public class VisionTest extends WestBot15 {
    BlockDetector detector;
    boolean hasDrove;
    double prevLeft, prevRight = 0;
    Point newNewPoint = new Point();
    Vector2 sampleVect = new Vector2();
    double xAng = 0;
    public void init(){
        msStuckDetectInit = 500000;
        super.init();
        activateGamepad1();
        detector = new BlockDetector();
        detector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = detector;

        drivetrain.controlState = TankDT.ControlState.FIELD_CENTRIC;
        drivetrain.direction = TankDT.Direction.FOR;
    }
    public void initLoop(){
        //telemetry.addData("location 1", motoG4.rearCamera.getObjectLocation(detector.elements.get(0), detector.result().size(), 2));
    }
    @Override
    public void start(){
        super.start();

    }

    public void loop(){
        drivetrain.updateLocation(drivetrain.averageLeftEncoders() - prevLeft, drivetrain.averageRightEncoders() - prevRight);
        setRobotAngle();
        drivetrain.maxSpeed = 0.2;
        setRobotAngle();
        Vector2 temp = new Vector2(detector.element.y, -detector.element.x);
        temp.x -= 480/ 2;
        temp.y += 640 / 2;

        double vertAng = temp.y / 640 * motoG4.rearCamera.verticalAngleOfView();
        double horiAng = temp.x / 480 * motoG4.rearCamera.horizontalAngleOfView();

        double newY = (10 - 2 / 2) / Math.tan(-vertAng);
        double newX = newY * Math.tan(horiAng);
        newY += 5.75;
        newX += 3.5;
        Vector2 location = new Vector2(-newX, newY);
        horiAng = -location.angle();
        Point newPoint = new Point(newX, newY);
        if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && !hasDrove) {
            hasDrove = true;
            sampleVect = new Vector2(newX, newY);
            xAng = horiAng;
        }
        if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER && hasDrove)
            hasDrove = false;
        int i;
        i = gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER ? 1 : -1;

        if(hasDrove){
            drivetrain.setLeftPow(-i * Math.sin(xAng - drivetrain.position.angle));
            drivetrain.setRightPow(i * Math.sin(xAng - drivetrain.position.angle));
        }
        /*if(hasDrove) {
            drivetrain.updateLocation(drivetrain.averageLeftEncoders() - prevLeft0, drivetrain.averageRightEncoders() - prevRight);
            prevLeft0 = drivetrain.averageLeftEncoders();
            prevRight = drivetrain.averageRightEncoders();
            drivetrain.driveToPoint(sampleVect.x, sampleVect.y, TankDT.Direction.FOR);
            if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                hasDrove = false;
                drivetrain.setLeftPow(0);
                drivetrain.setRightPow(0);
            }
        }*/
        telemetry.addData("sample location: ", location);
        telemetry.addData("robot location: ", drivetrain.position.angle);
        telemetry.addData("horiAng: ", Math.toDegrees(horiAng));
        telemetry.addData("xAng: ", Math.toDegrees(xAng));
        telemetry.addData("robot ang: ", Math.toDegrees(robotAngle.angle()));
    }

    public void stop(){
        super.stop();
        detector.isInitialized = false;
    }
}
