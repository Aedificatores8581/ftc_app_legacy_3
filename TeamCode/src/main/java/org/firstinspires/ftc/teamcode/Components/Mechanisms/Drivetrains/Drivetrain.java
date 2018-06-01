package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 5/29/2018
 */
public abstract class Drivetrain {
    public final DcMotor.Direction FORWARD = DcMotor.Direction.FORWARD, REVERSE = DcMotor.Direction.REVERSE;
    public double minTurn;
    public double brakePow;
    public double maxSpeed = 1;
    public Vector2 turnVector;
    public Vector2 robotAngle;
    public Vector2 speedVector;
    public Drivetrain(double pow){
        brakePow = pow;
    }
    //Maps the drive motors to the rev module
    public abstract void initMotors();
    //gives the motors holding power
    public abstract void brake();
    public enum Direction{
        FOR,
        BACK
    }
    public void setPower(DcMotor m, double pow){
        m.setPower(pow *  maxSpeed);
    }
    public abstract void normalizeMotors();
}
