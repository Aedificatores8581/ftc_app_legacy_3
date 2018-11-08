package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;

public class AExtendotm {
    public DcMotor articulator, extendo;
    public Servo marker;
    public Pose markerPosition = new Pose();
    public final Pose AEXTENDOtm_POSE = new Pose();
    //TODO: find these values
    public final double SERVO_ZERO_POSITION = 0,
                        SERVO_ONE_POSITION = 1,
                        MAX_EXTENSION_LENGTH = 36,
                        ENC_PER_RADIAN = 10,
                        TICKS_PER_INCH = 500,
                        START_ANGLE = 0,
                        RETRACTED_RAISED_ANGLE = 0,
                        EXTENDED_RAISED_ANGLE = 0,
                        LOWERED_ANGLE = -Math.PI
                        ;
    public void init(HardwareMap hardwareMap, boolean isAutonomous){
        articulator = hardwareMap.dcMotor.get("arti");
        extendo = hardwareMap.dcMotor.get("aetm");
        marker = hardwareMap.servo.get("mrkr");
        articulator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extendo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        articulator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendo.setMode(isAutonomous ? DcMotor.RunMode.RUN_TO_POSITION : DcMotor.RunMode.RUN_USING_ENCODER);
        articulator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    public double getArticulatorAngle(){
        return articulator.getCurrentPosition() / ENC_PER_RADIAN;
    }
    public int angleToEnc(double ang){
        return (int) (ang * ENC_PER_RADIAN);
    }
    public void dropMarker(){
        marker.setPosition(SERVO_ONE_POSITION);
    }
    public void aextendTM(double value){
        if(extendo.getMode() == DcMotor.RunMode.RUN_TO_POSITION)
            extendo.setPower(value);
        else
            extendo.setTargetPosition((int)(value * TICKS_PER_INCH));
    }
    public double getExtensionLength(){
        return TICKS_PER_INCH * extendo.getCurrentPosition();
    }
    public double getDesiredExtensionLength(){
        return TICKS_PER_INCH * extendo.getTargetPosition();
    }
    public boolean isRetracted(){
        return getExtensionLength() < 5;
    }
    public boolean willBeRetracted(){
        return getDesiredExtensionLength() < 5;
    }
    public void articulateDown(){
        articulator.setTargetPosition(angleToEnc(LOWERED_ANGLE));
    }
    public void articulateUp(){
        articulator.setTargetPosition(angleToEnc(willBeRetracted() ? RETRACTED_RAISED_ANGLE : EXTENDED_RAISED_ANGLE));
    }
    public String toString(){
        String markerTerm  = marker.getPosition() == SERVO_ONE_POSITION ? "marker is deployed" : "marker is not deployed";
        return getExtensionLength() + " inches, " + getArticulatorAngle() + " degrees, " + markerTerm;
    }
}
