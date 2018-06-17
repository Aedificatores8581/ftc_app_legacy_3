package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class WestCoast15 extends TankDT {
    public DcMotor rightFront, leftFront, leftRear, rightRear;
    public MotorEncoder rfEncoder, lfEncoder, lrEncoder, rrEncoder;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior;
    public WestCoast15(){
        zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        maxSpeed = 1;
    }
    public WestCoast15(DcMotor.ZeroPowerBehavior zeroPowBehavior, double speed){
        zeroPowerBehavior = zeroPowBehavior;
        maxSpeed = speed;
    }

    public void setLeftPow(double pow) {
        leftFront.setPower(pow * maxSpeed);
        leftRear.setPower(pow * maxSpeed);
        leftPow = pow;
    }
    public void setRightPow(double pow){
        rightFront.setPower(pow * maxSpeed);
        rightRear.setPower(pow * maxSpeed);
        rightPow = pow;
    }
    public void initMotors(HardwareMap map){
        rightFront = map.dcMotor.get("rf");
        leftFront = map.dcMotor.get("lf");
        leftRear = map.dcMotor.get("la");
        rightRear = map.dcMotor.get("ra");

        rightFront.setDirection(REVERSE);
        rightRear.setDirection(REVERSE);
        leftFront.setDirection(FORWARD);
        leftRear.setDirection(FORWARD);

        lfEncoder = new MotorEncoder(leftFront);
        lrEncoder = new MotorEncoder(leftRear);
        rfEncoder = new MotorEncoder(rightFront);
        rrEncoder = new MotorEncoder(rightRear);
    }
    public void normalizeMotors(){
    }

}