package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class WestCoast15 extends TankDT {
    public DcMotor rightFore, leftFore, leftRear, rightRear;
    public MotorEncoder rfEncoder, lfEncoder, lrEncoder, rrEncoder;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior;
    public WestCoast15(){
        ENC_PER_INCH = 140 / Math.PI;
        DISTANCE_BETWEEN_WHEELS = 390.9515 / 25.4;
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
        lfEncoder.initEncoder();
        lrEncoder.initEncoder();
        rfEncoder.initEncoder();
        rrEncoder.initEncoder();
    }
    public void normalizeMotors(){
    }

    @Override
    public void resetEncoders() {

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

    @Override
    public double averageEncoders() {
        return (averageLeftEncoders() + averageRightEncoders()) / 2;
    }

    public Pose updateLocation(Pose y, Pose x, double xVal, double yVal){
        //xVal *= -1;
        double angle = xVal / x.radius() / Math.cos(Math.PI / 2 - x.angleOfVector());
        yVal += angle * y.radius() * Math.sin(Math.PI / 2 - y.angleOfVector());
        Vector2 velocity = new Vector2();
        double radius = yVal / angle;
        velocity.setFromPolar(radius, angle);
        velocity.x -= radius;
        position.add(velocity);
        return position;
    }
}