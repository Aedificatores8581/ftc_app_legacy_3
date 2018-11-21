package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

/**
 * Created by Frank Portman on 5/21/2018
 */

public class WestCoast15 extends TankDT {
    public DcMotor rightFore, leftFore, leftRear, rightRear;
    public MotorEncoder rfEncoder, lfEncoder, lrEncoder, rrEncoder;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior;
    //TODO: Find this value
    public final double MAX_ENC_VAL = 3036;

    public WestCoast15() {
        ENC_PER_INCH = 140 / Math.PI;
        DISTANCE_BETWEEN_WHEELS = 390.9515 / 25.4;
        zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT;
        maxSpeed = 1;
        leftMotors = new DcMotor[]{leftFore, leftRear};
        rightMotors = new DcMotor[]{rightFore, rightRear};
    }

    public WestCoast15(DcMotor.ZeroPowerBehavior zeroPowBehavior, double speed) {
        zeroPowerBehavior = zeroPowBehavior;
        maxSpeed = speed;

        leftMotors = new DcMotor[]{leftFore, leftRear};
        rightMotors = new DcMotor[]{rightFore, rightRear};
    }

    public void setLeftPow(double pow) {
        pow = UniversalFunctions.clamp(-1, pow, 1);
        leftFore.setPower(pow * maxSpeed);
        leftRear.setPower(pow * maxSpeed);
        leftPow = pow;
    }

    public void setRightPow(double pow) {
        pow = UniversalFunctions.clamp(-1, pow, 1);
        rightFore.setPower(pow * maxSpeed);
        rightRear.setPower(pow * maxSpeed);
        rightPow = pow;
    }

    public void initMotors(HardwareMap map) {
        rightFore = map.dcMotor.get("rf");
        leftFore = map.dcMotor.get("lf");
        leftRear = map.dcMotor.get("la");
        rightRear = map.dcMotor.get("ra");

        leftFore.setZeroPowerBehavior(zeroPowerBehavior);
        leftRear.setZeroPowerBehavior(zeroPowerBehavior);
        rightFore.setZeroPowerBehavior(zeroPowerBehavior);
        rightRear.setZeroPowerBehavior(zeroPowerBehavior);

        rightFore.setDirection(FORWARD);
        rightRear.setDirection(FORWARD);
        leftFore.setDirection(REVERSE);
        leftRear.setDirection(REVERSE);
        lfEncoder = new MotorEncoder(leftFore);
        lrEncoder = new MotorEncoder(leftRear);
        rfEncoder = new MotorEncoder(rightFore);
        rrEncoder = new MotorEncoder(rightRear);
        lfEncoder.initEncoder();
        lrEncoder.initEncoder();
        rfEncoder.initEncoder();
        rrEncoder.initEncoder();
    }
    public void hardResetEncoders(){
        lfEncoder.hardResetEncoder();
        rfEncoder.hardResetEncoder();
        lrEncoder.hardResetEncoder();
        rrEncoder.hardResetEncoder();
    }
    public void normalizeMotors() {}

    @Override
    public void resetEncoders() {}

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
    public void spicyDrive(Vector2 leftStick1, double leftTrigger, double rightTrigger){
        double velocity = rightTrigger - leftTrigger;
        leftFore.setPower(maxSpeed * (velocity + leftStick1.x));
        leftRear.setPower(maxSpeed * (velocity + leftStick1.x));
        rightFore.setPower(maxSpeed * (velocity - leftStick1.x));
        rightRear.setPower(maxSpeed * (velocity - leftStick1.x));
    }
    public void spicyDrive2(Vector2 leftStick1, double leftTrigger, double rightTrigger){
        double velocity = rightTrigger - leftTrigger;
        setLeftPow(velocity + leftStick1.x);
        setRightPow(velocity - leftStick1.x);
    }
}