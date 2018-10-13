package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.Vision.Detectors.GenericDetector;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

import ftc.vision.Detector;

@Autonomous(name = "visiontuning", group = "vision")
public class VisionTuningTest extends OpMode {
    Adjust adjust = Adjust.CAN;
    GenericDetector detector;
    DetectorRange dRange = DetectorRange.MIN1;
    Colorspace colorspace = Colorspace.HSV;
    boolean canSwitch = true;
    double prevTime;
    boolean canSwitchColorSpace = true;
    String detecting = "hsv H min";
    public void init(){
        detector = new GenericDetector();
        detector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = detector;
        prevTime = System.currentTimeMillis();

    }

    public void loop(){
        switch(colorspace){
            case HSV:
                if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchColorSpace){
                    canSwitchColorSpace = false;
                    colorspace = Colorspace.RGB;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)
                    canSwitchColorSpace = true;
                break;
            case RGB:
                if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchColorSpace){
                    canSwitchColorSpace = false;
                    colorspace = Colorspace.Lab;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)
                    canSwitchColorSpace = true;
                break;
            case Lab:
                if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchColorSpace){
                    canSwitchColorSpace = false;
                    colorspace = Colorspace.HLS;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)
                    canSwitchColorSpace = true;
                break;
            case HLS:
                if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchColorSpace){
                    canSwitchColorSpace = false;
                    colorspace = Colorspace.YUv;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)
                    canSwitchColorSpace = true;
                break;
            case YUv:
                if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchColorSpace){
                    canSwitchColorSpace = false;
                    colorspace = Colorspace.HSV;
                }
                else if(gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER)
                    canSwitchColorSpace = true;
                break;
        }
        int addition = (int) Math.signum(gamepad1.left_stick_y);
        switch(adjust){
            case CANT:
                if(System.currentTimeMillis() > prevTime + 125)
                    adjust = Adjust.CAN;
                break;
            case CAN:
                switch(dRange){
                    case MIN1:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK) {
                            switch(colorspace){
                                case RGB:
                                    detector.R_MIN -= addition;
                                    detecting = "rgb R min";
                                    break;
                                case HSV:
                                    detector.H_MIN -= addition;
                                    detecting = "hsv H min";
                                    break;
                                case Lab:
                                    detector.L_MIN -= addition;
                                    detecting = "Lab L min";
                                    break;
                                case HLS:
                                    detector.h_MIN -= addition;
                                    detecting = "hls H min";
                                    break;
                                case YUv:
                                    detector.Y_MIN -= addition;
                                    detecting = "yuv Y min";
                                    break;

                            }
                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MIN2;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case MIN2:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                            switch(colorspace){
                                case RGB:
                                    detector.G_MIN -= addition;
                                    detecting = "rgb G min";
                                    break;
                                case HSV:
                                    detector.S_MIN -= addition;
                                    detecting = "hsv S min";
                                    break;
                                case Lab:
                                    detector.a_MIN -= addition;
                                    detecting = "Lab A min";
                                    break;
                                case HLS:
                                    detector.l_MIN -= addition;
                                    detecting = "hls L min";
                                    break;

                                case YUv:
                                    detector.U_MIN -= addition;
                                    detecting = "yuv U min";
                                    break;

                            }

                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MIN3;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case MIN3:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                            switch(colorspace){
                                case RGB:
                                    detector.B_MIN -= addition;
                                    detecting = "rgb B min";
                                    break;
                                case HSV:
                                    detector.V_MIN -= addition;
                                    detecting = "hsv V min";
                                    break;
                                case Lab:
                                    detector.b_MIN -= addition;
                                    detecting = "Lab B min";
                                    break;
                                case HLS:
                                    detector.s_MIN -= addition;
                                    detecting = "hls S min";
                                    break;

                                case YUv:
                                    detector.v_MIN -= addition;
                                    detecting = "yuv v min";
                                    break;
                            }
                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MAX1;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case MAX1:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                            switch(colorspace){
                                case RGB:
                                    detector.R_MAX -= addition;
                                    detecting = "rgb R max";
                                    break;
                                case HSV:
                                    detector.H_MAX -= addition;
                                    detecting = "hsv H max";
                                    break;
                                case Lab:
                                    detector.L_MAX -= addition;
                                    detecting = "Lab L max";
                                    break;
                                case HLS:
                                    detector.h_MAX -= addition;
                                    detecting = "hls H max";
                                    break;

                                case YUv:
                                    detector.Y_MAX -= addition;
                                    detecting = "yuv Y max";
                                    break;
                            }

                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MAX2;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case MAX2:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                            switch(colorspace){
                                case RGB:
                                    detector.G_MAX -= addition;
                                    detecting = "rgb G max";
                                    break;
                                case HSV:
                                    detector.S_MAX -= addition;
                                    detecting = "hsv S max";
                                    break;
                                case Lab:
                                    detector.a_MAX -= addition;
                                    detecting = "Lab A max";
                                    break;
                                case HLS:
                                    detector.l_MAX -= addition;
                                    detecting = "hls L max";
                                    break;

                                case YUv:
                                    detector.U_MAX -= addition;
                                    detecting = "yuv U max";
                                    break;
                            }
                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MAX3;
                        }
                        else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER)
                            canSwitch = true;
                        break;
                    case MAX3:
                        if(Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                            switch(colorspace){
                                case RGB:
                                    detector.B_MAX -= addition;
                                    detecting = "rgb B max";
                                    break;
                                case HSV:
                                    detector.V_MAX -= addition;
                                    detecting = "hsv V max";
                                    break;
                                case Lab:
                                    detector.b_MAX -= addition;
                                    detecting = "Lab B max";
                                    break;
                                case HLS:
                                    detector.s_MAX -= addition;
                                    detecting = "hls S max";
                                    break;

                                case YUv:
                                    detector.v_MAX -= addition;
                                    detecting = "yuv v max";
                                    break;
                            }
                        }
                        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch){
                            canSwitch = false;
                            dRange = DetectorRange.MIN1;
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
        detector.R_MIN = (int)UniversalFunctions.clamp(0, detector.R_MIN, 254);
        detector.G_MIN = (int)UniversalFunctions.clamp(0, detector.G_MIN, 254);
        detector.B_MIN = (int)UniversalFunctions.clamp(0, detector.B_MIN, 254);
        detector.R_MAX = (int)UniversalFunctions.clamp(1, detector.R_MAX, 255);
        detector.G_MAX = (int)UniversalFunctions.clamp(1, detector.G_MAX, 255);
        detector.B_MAX = (int)UniversalFunctions.clamp(1, detector.B_MAX, 255);

        detector.H_MIN = (int)UniversalFunctions.clamp(0, detector.H_MIN, 254);
        detector.S_MIN = (int)UniversalFunctions.clamp(0, detector.S_MIN, 254);
        detector.V_MIN = (int)UniversalFunctions.clamp(0, detector.V_MIN, 254);
        detector.H_MAX = (int)UniversalFunctions.clamp(1, detector.H_MAX, 255);
        detector.S_MAX = (int)UniversalFunctions.clamp(1, detector.S_MAX, 255);
        detector.V_MAX = (int)UniversalFunctions.clamp(1, detector.V_MAX, 255);

        detector.L_MIN = (int)UniversalFunctions.clamp(0, detector.L_MIN, 254);
        detector.a_MIN = (int)UniversalFunctions.clamp(0, detector.a_MIN, 254);
        detector.b_MIN = (int)UniversalFunctions.clamp(0, detector.b_MIN, 254);
        detector.L_MAX = (int)UniversalFunctions.clamp(1, detector.L_MAX, 255);
        detector.a_MAX = (int)UniversalFunctions.clamp(1, detector.a_MAX, 255);
        detector.b_MAX = (int)UniversalFunctions.clamp(1, detector.b_MAX, 255);

        detector.h_MIN = (int)UniversalFunctions.clamp(0, detector.h_MIN, 254);
        detector.s_MIN = (int)UniversalFunctions.clamp(0, detector.s_MIN, 254);
        detector.l_MIN = (int)UniversalFunctions.clamp(0, detector.l_MIN, 254);
        detector.h_MAX = (int)UniversalFunctions.clamp(1, detector.h_MAX, 255);
        detector.s_MAX = (int)UniversalFunctions.clamp(1, detector.s_MAX, 255);
        detector.l_MAX = (int)UniversalFunctions.clamp(1, detector.l_MAX, 255);

        detector.Y_MIN = (int)UniversalFunctions.clamp(0, detector.Y_MIN, 254);
        detector.U_MIN = (int)UniversalFunctions.clamp(0, detector.U_MIN, 254);
        detector.v_MIN = (int)UniversalFunctions.clamp(0, detector.v_MIN, 254);
        detector.Y_MAX = (int)UniversalFunctions.clamp(1, detector.Y_MAX, 255);
        detector.U_MAX = (int)UniversalFunctions.clamp(1, detector.U_MAX, 255);
        detector.v_MAX = (int)UniversalFunctions.clamp(1, detector.v_MAX, 255);

        telemetry.addData(detecting, adjust);

        telemetry.addData("h Min", detector.h_MIN);
        telemetry.addData("l Min", detector.l_MIN);
        telemetry.addData("s Min", detector.s_MIN);
        telemetry.addData("h Max", detector.h_MAX);
        telemetry.addData("l Max", detector.l_MAX);
        telemetry.addData("s Max", detector.s_MAX);

        telemetry.addData("l Min", detector.L_MIN);
        telemetry.addData("a Min", detector.a_MIN);
        telemetry.addData("b Min", detector.b_MIN);
        telemetry.addData("l Max", detector.L_MAX);
        telemetry.addData("a Max", detector.a_MAX);
        telemetry.addData("b Max", detector.b_MAX);


        telemetry.addData("r Min", detector.R_MIN);
        telemetry.addData("g Min", detector.G_MIN);
        telemetry.addData("b Min", detector.B_MIN);
        telemetry.addData("r Max", detector.R_MAX);
        telemetry.addData("g Max", detector.G_MAX);
        telemetry.addData("b Max", detector.B_MAX);


        telemetry.addData("h Min", detector.H_MIN);
        telemetry.addData("s Min", detector.S_MIN);
        telemetry.addData("v Min", detector.V_MIN);
        telemetry.addData("h Max", detector.H_MAX);
        telemetry.addData("s Max", detector.S_MAX);
        telemetry.addData("v Max", detector.V_MAX);

        telemetry.addData("y Min", detector.Y_MIN);
        telemetry.addData("u Min", detector.U_MIN);
        telemetry.addData("v Min", detector.v_MIN);
        telemetry.addData("y Max", detector.Y_MAX);
        telemetry.addData("u Max", detector.U_MAX);
        telemetry.addData("v Max", detector.v_MAX);
    }

    public void stop(){
        super.stop();
        detector.isInitialized = false;
    }
    public enum DetectorRange{
        MIN1,
        MAX1,
        MIN2,
        MAX2,
        MIN3,
        MAX3;
    }
    public enum Adjust{
        CAN,
        CANT
    }
    public enum Colorspace{
        RGB,
        HSV,
        Lab,
        HLS,
        YUv
    }

}
