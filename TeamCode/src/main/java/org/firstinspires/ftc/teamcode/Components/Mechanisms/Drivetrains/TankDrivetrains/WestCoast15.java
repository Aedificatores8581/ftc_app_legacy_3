package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class WestCoast15 extends TankDT {
    public DcMotor rightFore, leftFore, leftRear, rightRear;
    public MotorEncoder rfEncoder, lfEncoder, lrEncoder, rrEncoder;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior;
    //TODO: Find the value of ENC_PER_INCH
    public final double ENC_PER_INCH = 0;
    public WestCoast15(){
        zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        maxSpeed = 1;
        leftMotors = new DcMotor[]{leftFore, leftRear};
        rightMotors = new DcMotor[]{rightFore, rightRear};
    }
    public WestCoast15(DcMotor.ZeroPowerBehavior zeroPowBehavior, double speed){
        zeroPowerBehavior = zeroPowBehavior;
        maxSpeed = speed;
        leftMotors = new DcMotor[]{leftFore, leftRear};
        rightMotors = new DcMotor[]{rightFore, rightRear};
    }
    public void setLeftPow(double pow) {
        leftFore.setPower(pow * maxSpeed);
        leftRear.setPower(pow * maxSpeed);
        leftPow = pow;
    }
    public void setRightPow(double pow){
        rightFore.setPower(pow * maxSpeed);
        rightRear.setPower(pow * maxSpeed);
        rightPow = pow;
    }
    public void initMotors(HardwareMap map){
        rightFore = map.dcMotor.get("rf");
        leftFore = map.dcMotor.get("lf");
        leftRear = map.dcMotor.get("la");
        rightRear = map.dcMotor.get("ra");

        rightFore.setDirection(REVERSE);
        rightRear.setDirection(REVERSE);
        leftFore.setDirection(FORWARD);
        leftRear.setDirection(FORWARD);

        lfEncoder = new MotorEncoder(leftFore);
        lrEncoder = new MotorEncoder(leftRear);
        rfEncoder = new MotorEncoder(rightFore);
        rrEncoder = new MotorEncoder(rightRear);
    }
    public void normalizeMotors(){
    }
    public void updateEncoders(){
        lfEncoder.updateEncoder();
        rfEncoder.updateEncoder();
        rrEncoder.updateEncoder();
        lrEncoder.updateEncoder();
    }
    public double averageLeftEncoders(){
        return (lfEncoder.currentPosition + lrEncoder.currentPosition) / 2;
    }
    public double averageRightEncoders(){
        return (rfEncoder.currentPosition + rrEncoder.currentPosition) / 2;
    }
}