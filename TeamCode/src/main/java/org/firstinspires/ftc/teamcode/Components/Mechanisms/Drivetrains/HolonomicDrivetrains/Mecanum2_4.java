package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.HolonomicDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class Mecanum2_4 extends MecanumDT {
    DcMotor leftFore, leftRear, rightFore, rightRear;


    public Mecanum2_4(){
        super(1);
        leftFore.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightFore.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        maxSpeed = 1;
    }
    public Mecanum2_4(DcMotor.ZeroPowerBehavior zeroPowerBehavior, double speed) {
        super(1);
        leftFore.setZeroPowerBehavior(zeroPowerBehavior);
        leftRear.setZeroPowerBehavior(zeroPowerBehavior);
        rightFore.setZeroPowerBehavior(zeroPowerBehavior);
        rightRear.setZeroPowerBehavior(zeroPowerBehavior);
        maxSpeed = speed;
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

        rightFore.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        leftFore.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
    }

    //sets the motor powers to the specified values
    public void refreshMotors(double I, double II, double III, double IV){
        rightFore.setPower(I);
        leftFore.setPower(II);
        leftRear.setPower(III);
        rightRear.setPower(IV);
    }

    //sets the motor powers to the specified values at the specified speed
    public void refreshMotors(double I, double II, double III, double IV, double speed){
        rightFore.setPower(speed * I);
        leftFore.setPower(speed * II);
        leftRear.setPower(speed * III);
        rightRear.setPower(speed * IV);
    }

    //sets the motor powers to rightForePow and leftForePow respectively
    public void refreshMotors(){
        rightFore.setPower(rightForePow * maxSpeed);
        rightRear.setPower(rightAftPow * maxSpeed);
        leftFore.setPower(leftForePow * maxSpeed);
        leftRear.setPower(leftAftPow * maxSpeed);
    }
}