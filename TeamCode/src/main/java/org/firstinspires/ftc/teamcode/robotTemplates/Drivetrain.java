package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.robotUniversal.GyroAngles;

/**
 * Created by Frank Portman on 5/21/2018
 */
import java.util.ArrayList;

public abstract class Drivetrain extends Robot {
    public final DcMotor.Direction FORWARD = DcMotor.Direction.FORWARD, REVERSE = DcMotor.Direction.REVERSE;
    ArrayList<DcMotor> driveMotors;
    public double brakePow;
    public double speed = 1;
    public Drivetrain(double pow){
        super(true);
        String[] names = names();
        DcMotor[] motors = motors();
        DcMotor.Direction[] dir = dir();
        for(int i = 0; i < dir.length; i++){
            motors[i] = hardwareMap.dcMotor.get(names[i]);
            motors[i].setDirection(dir[i]);
            driveMotors.add(motors[i]);
        }
        brakePow = pow;
    }
    public void init(){
        super.init();
    }
    //gives the motors holding power
    public void brake(){
        for(int i = 0; i < driveMotors.size(); i++)
            driveMotors.get(i).setPower(brakePow);
    }
    //returns the names of the motors of the drivetrain
    public abstract String[] names();
    //returns the motors of the drivetrain
    public abstract DcMotor[] motors();
    //returns the directions of the motors of the drivetrain
    public abstract DcMotor.Direction[] dir();

    public enum Direction{
        FOR,
        BACK
    }
    public void setPower(DcMotor m, double pow){
        m.setPower(pow *  speed);
    }
}