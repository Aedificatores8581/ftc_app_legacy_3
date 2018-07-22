package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.opencv.core.Point;

import ftc.vision.Detector;

@Autonomous(name = "visiontuning", group = "vision")
public class VisionTuningTest extends OpMode {
    Adjust adjust = Adjust.CAN;
    RedJewelDetector tennisBallDetector;
    DetectorRange dRange = DetectorRange.HMIN;
    boolean canSwitch = true;
    double prevTime;
    double maxR = 0, maxG = 0, maxB = 0, minR = 255, minG = 255, minB = 255;
    Point min = new Point();
    Point max = new Point();
    double rAv = 0, gAv = 0, bAv = 0;
    double count = 1;
    double distance = 1;
    public void init(){
        tennisBallDetector = new RedJewelDetector();
        tennisBallDetector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = tennisBallDetector;
        prevTime = System.currentTimeMillis();

    }
    @Override
    public void init_loop(){
        switch(adjust){
            case CANT:
                if(System.currentTimeMillis() > prevTime + 125)
                    adjust = Adjust.CAN;
                break;
            case CAN:
                switch(dRange){
                    case HMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.L_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.HMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case HMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.a_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.b_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.L_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.a_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.b_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.HMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                }
                if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK) {
                    prevTime = System.currentTimeMillis();
                    adjust = adjust.CANT;
                }
                break;
        }
        tennisBallDetector.L_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.L_MIN, 254);
        tennisBallDetector.a_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.a_MIN, 254);
        tennisBallDetector.b_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.b_MIN, 254);
        tennisBallDetector.L_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.L_MAX, 255);
        tennisBallDetector.a_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.a_MAX, 255);
        tennisBallDetector.b_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.b_MAX, 255);
        telemetry.addData("l Min", tennisBallDetector.L_MIN);
        telemetry.addData("a Min", tennisBallDetector.a_MIN);
        telemetry.addData("b Min", tennisBallDetector.b_MIN);
        telemetry.addData("l Max", tennisBallDetector.L_MAX);
        telemetry.addData("a Max", tennisBallDetector.a_MAX);
        telemetry.addData("b Max", tennisBallDetector.b_MAX);
        /*telemetry.addData("Detecting", dRange);
        telemetry.addData("distance away from camera (inches)",  480 * 3.7 / (int)tennisBallDetector.radius[0] / 2);
        telemetry.addData("ratio", (tennisBallDetector.ratio));

        telemetry.addData("contsize", tennisBallDetector.contsize);*/
    }

    public void loop(){ }
    @Override
    public void stop(){
        super.stop();
        tennisBallDetector.isInitialized = false;
    }
    public enum DetectorRange{
        HMIN,
        HMAX,
        SMIN,
        SMAX,
        VMIN,
        VMAX;
    }
    public enum Adjust{
        CAN,
        CANT
    }
}
