package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class WestCoast15 extends TankDT {
    public DcMotor rightFront, leftFront, leftRear, rightRear;
    public WestCoast15(){
        super(0.001);
        maxSpeed = 1;
    }
    public WestCoast15(double brakePow, double speed){
        super(brakePow);
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
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void normalizeMotors(){

    }

}