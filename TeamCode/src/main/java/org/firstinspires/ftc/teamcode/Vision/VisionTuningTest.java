package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.opencv.core.Core;
import org.opencv.core.Point;

import ftc.vision.BlockDetector;
import ftc.vision.Detector;
import ftc.vision.RedJewelDetector;
import ftc.vision.TennisBallDetector;

@Autonomous(name = "visiontuning", group = "vision")
public class VisionTuningTest extends OpMode {
    Adjust adjust = Adjust.CAN;
    BlockDetector tennisBallDetector;
    DetectorRange dRange = DetectorRange.HMIN;
    boolean canSwitch = true;
    double prevTime;
    double maxR = 0, maxG = 0, maxB = 0, minR = 255, minG = 255, minB = 255;
    Point min = new Point();
    Point max = new Point();
    double rAv = 0, gAv = 0, bAv = 0;
    double count = 1;

    public void init(){
        tennisBallDetector = new BlockDetector();
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
                            tennisBallDetector.R_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.HMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case HMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.G_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.B_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.R_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.G_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.B_MAX -= (int)Math.signum(gamepad1.left_stick_y);
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
                    adjust = Adjust.CANT;
                }
                break;
        }

        tennisBallDetector.R_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.R_MIN, 254);
        tennisBallDetector.G_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.G_MIN, 254);
        tennisBallDetector.B_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.B_MIN, 254);
        tennisBallDetector.R_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.R_MAX, 255);
        tennisBallDetector.G_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.G_MAX, 255);
        tennisBallDetector.B_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.B_MAX, 255);
        telemetry.addData("R Min", tennisBallDetector.R_MIN);
        telemetry.addData("G Min", tennisBallDetector.G_MIN);
        telemetry.addData("B Min", tennisBallDetector.B_MIN);
        telemetry.addData("R Max", tennisBallDetector.R_MAX);
        telemetry.addData("G Max", tennisBallDetector.G_MAX);
        telemetry.addData("B Max", tennisBallDetector.B_MAX);
        telemetry.addData("Detecting", dRange);
        maxR = Math.max(Core.minMaxLoc(tennisBallDetector.r).maxVal, maxR);
        maxG = Math.max(Core.minMaxLoc(tennisBallDetector.g).maxVal, maxG);
        maxB = Math.max(Core.minMaxLoc(tennisBallDetector.b).maxVal, maxB);
        minR = Math.min(Core.minMaxLoc(tennisBallDetector.r).minVal, minR);
        minG = Math.min(Core.minMaxLoc(tennisBallDetector.g).minVal, minG);
        minB = Math.min(Core.minMaxLoc(tennisBallDetector.b).minVal, minB);
        telemetry.addData("rmax", maxR);
        telemetry.addData("gmax", maxG);
        telemetry.addData("bmax", maxB);
        telemetry.addData("rmin", minR);
        telemetry.addData("gmin", minG);
        telemetry.addData("bmin", minB);
        rAv = rAv + Core.minMaxLoc(tennisBallDetector.r).maxVal;
        gAv = gAv + Core.minMaxLoc(tennisBallDetector.g).maxVal;
        bAv = bAv + Core.minMaxLoc(tennisBallDetector.b).maxVal;
        count ++;
        telemetry.addData("rav", rAv / count);
        telemetry.addData("gav", gAv / count);
        telemetry.addData("bav", bAv / count);
        //237 196 166

        //255, 225, 176
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
