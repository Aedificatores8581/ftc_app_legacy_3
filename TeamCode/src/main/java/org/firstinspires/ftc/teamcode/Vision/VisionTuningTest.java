package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;

import ftc.vision.Detector;
import ftc.vision.TennisBallDetector;

@Autonomous(name = "visiontuning", group = "vision")
public class VisionTuningTest extends OpMode {
    Adjust adjust;
    TennisBallDetector tennisBallDetector;
    DetectorRange dRange = DetectorRange.HMIN;
    boolean canSwitch = true;
    double prevTime;
    public void init(){
        tennisBallDetector = new TennisBallDetector();
        tennisBallDetector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = tennisBallDetector;
        prevTime = System.currentTimeMillis();

    }
    @Override
    public void init_loop(){
        switch(adjust){
            case CANT:
                if(System.currentTimeMillis() > prevTime + 250)
                    adjust = Adjust.CAN;
                break;
            case CAN:
                switch(dRange){
                    case HMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.H_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.HMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case HMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.H_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.S_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.SMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case SMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.S_MAX -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMIN;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMIN:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.V_MIN -= (int)Math.signum(gamepad1.left_stick_y);
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.VMAX;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case VMAX:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK)
                            tennisBallDetector.V_MAX -= (int)Math.signum(gamepad1.left_stick_y);
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
        }

        tennisBallDetector.V_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.V_MIN, 254);
        tennisBallDetector.S_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.S_MIN, 254);
        tennisBallDetector.H_MIN = (int)UniversalFunctions.clamp(0, tennisBallDetector.H_MIN, 254);
        tennisBallDetector.V_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.V_MAX, 255);
        tennisBallDetector.S_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.S_MAX, 255);
        tennisBallDetector.H_MAX = (int)UniversalFunctions.clamp(1, tennisBallDetector.H_MAX, 255);
        telemetry.addData("H Min", tennisBallDetector.H_MIN);
        telemetry.addData("S Min", tennisBallDetector.S_MIN);
        telemetry.addData("V Min", tennisBallDetector.V_MIN);
        telemetry.addData("H Max", tennisBallDetector.H_MAX);
        telemetry.addData("S Max", tennisBallDetector.S_MAX);
        telemetry.addData("V Max", tennisBallDetector.V_MAX);
        telemetry.addData("Detecting", dRange);
    }

    public void loop(){

    }
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
