package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;

import ftc.vision.Detector;
import ftc.vision.TennisBallDetector;

@Autonomous(name = "tennisballtuning", group = "vision")
public class TennisBallDetectionTuning extends OpMode {
    TennisBallDetector tennisBallDetector;


    public void init(){
        tennisBallDetector = new TennisBallDetector();
        tennisBallDetector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = tennisBallDetector;
    }
    public void initLoop(){
        if (gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER) {
            if (Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                if(Math.abs(gamepad1.right_stick_y) > UniversalConstants.Triggered.STICK)
                    tennisBallDetector.V_MIN += (int)Math.signum(gamepad1.left_stick_y);
                else
                    tennisBallDetector.H_MIN += (int)Math.signum(gamepad1.left_stick_y);
            }
            else if(Math.abs(gamepad1.right_stick_y) > UniversalConstants.Triggered.STICK)
                tennisBallDetector.S_MIN += (int)Math.signum(gamepad1.right_stick_y);
        else{
            if (Math.abs(gamepad1.left_stick_y) > UniversalConstants.Triggered.STICK){
                if(Math.abs(gamepad1.right_stick_y) > UniversalConstants.Triggered.STICK)
                    tennisBallDetector.V_MAX += (int)Math.signum(gamepad1.left_stick_y);
                else
                    tennisBallDetector.H_MAX += (int)Math.signum(gamepad1.left_stick_y);
            }
            else if(Math.abs(gamepad1.right_stick_y) > UniversalConstants.Triggered.STICK)
                tennisBallDetector.S_MAX += (int)Math.signum(gamepad1.right_stick_y);
            }
        }
        UniversalFunctions.clamp(0, tennisBallDetector.V_MIN, 254);
        UniversalFunctions.clamp(0, tennisBallDetector.S_MIN, 254);
        UniversalFunctions.clamp(0, tennisBallDetector.H_MIN, 254);
        UniversalFunctions.clamp(1, tennisBallDetector.V_MAX, 255);
        UniversalFunctions.clamp(1, tennisBallDetector.S_MAX, 255);
        UniversalFunctions.clamp(1, tennisBallDetector.H_MAX, 255);
        telemetry.addData("H Min", tennisBallDetector.H_MIN);
        telemetry.addData("S Min", tennisBallDetector.S_MIN);
        telemetry.addData("V Min", tennisBallDetector.V_MIN);
        telemetry.addData("H Max", tennisBallDetector.H_MAX);
        telemetry.addData("S Max", tennisBallDetector.S_MAX);
        telemetry.addData("V Max", tennisBallDetector.V_MAX);
    }

    public void loop(){

    }
}
