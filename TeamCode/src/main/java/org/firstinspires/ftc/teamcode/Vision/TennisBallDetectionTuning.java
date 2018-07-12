package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;

import ftc.vision.Detector;
import ftc.vision.TennisBallDetector;

@Autonomous
public class TennisBallDetectionTuning extends OpMode {
    TennisBallDetector tennisBallDetector;


    public void init(){
        tennisBallDetector = new TennisBallDetector();
        tennisBallDetector.opState = Detector.OperatingState.TUNING;
        FtcRobotControllerActivity.frameGrabber.detector = tennisBallDetector;
    }
    public void initLoop(){
        if(gamepad1.left_stick_y > UniversalConstants.Triggered.STICK) {
            if (gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER) {
                if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER)
                    tennisBallDetector.V_MIN += Math.signum(gamepad1.left_stick_y);
                else if (gamepad1.right_stick_button)
                    tennisBallDetector.S_MIN += Math.signum(gamepad1.left_stick_y);
                else
                    tennisBallDetector.H_MIN += Math.signum(gamepad1.left_stick_y);
            }
            else{
                if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER)
                    tennisBallDetector.V_MAX += Math.signum(gamepad1.left_stick_y);
                else if (gamepad1.right_stick_button)
                    tennisBallDetector.S_MAX += Math.signum(gamepad1.left_stick_y);
                else
                    tennisBallDetector.H_MAX += Math.signum(gamepad1.left_stick_y);
            }
        }
        UniversalFunctions.clamp(0, tennisBallDetector.V_MIN, 254);
        UniversalFunctions.clamp(0, tennisBallDetector.S_MIN, 254);
        UniversalFunctions.clamp(0, tennisBallDetector.H_MIN, 254);
        UniversalFunctions.clamp(1, tennisBallDetector.V_MAX, 255);
        UniversalFunctions.clamp(1, tennisBallDetector.S_MAX, 255);
        UniversalFunctions.clamp(1, tennisBallDetector.H_MAX, 255);
    }

    public void loop(){

    }
}
