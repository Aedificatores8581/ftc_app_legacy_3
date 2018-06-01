package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by Frank Portman on 5/21/2018
 */
import java.util.ArrayList;

public abstract class Drivetrain extends Robot {
    public final DcMotor.Direction FORWARD = DcMotor.Direction.FORWARD, REVERSE = DcMotor.Direction.REVERSE;
    public double minTurn;
    public double brakePow;
    public double maxSpeed = 1;
    public Drivetrain(double pow){
        super(false);
        brakePow = pow;
    }
    //Maps the drive motors to the rev module
    public abstract void initMotors();
    @Override
    public void init(){
        super.init();
    }
    //gives the motors holding power
    public abstract void brake();
    public enum Direction{
        FOR,
        BACK
    }
    public void start(){
        super.start();
    }
    public void setPower(DcMotor m, double pow){
        m.setPower(pow *  maxSpeed);
    }
    public abstract void normalizeMotors();
}
