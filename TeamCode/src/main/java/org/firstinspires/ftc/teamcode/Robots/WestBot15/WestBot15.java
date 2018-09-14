package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Components.Sensors.REVToFSensor;
import org.firstinspires.ftc.teamcode.Robots.Robot;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
import org.opencv.core.Point3;

/**
 * Created by Frank Portman on 6/1/2018
 */

public abstract class WestBot15 extends Robot {
    //IMPORTANT: phone locations should be taken in relation to the robot, not the field
    //:TODO: Add an encoder to the servo
    public CRServo leftIntake, rightIntake;
    protected WestCoast15 drivetrain = new WestCoast15();

    public REVToFSensor xTof, yTof;

    MotoG4 motoG4 = new MotoG4();

    protected double currentAngle = 0;


    @Override
    public void init(){
        super.init();
        leftIntake = hardwareMap.crservo.get("lin");
        rightIntake = hardwareMap.crservo.get("rin");
        drivetrain.maxSpeed = 0.9775;
        drivetrain.initMotors(hardwareMap);

        msStuckDetectInit = 50000;
        drivetrain.position = new Pose();
    }

    @Override
    public void start(){
        super.start();
    }
    @Override
    public double getGyroAngle(){
        if(!usingIMU)
            return startAngle + (drivetrain.averageRightEncoders() -  drivetrain.averageLeftEncoders()) / drivetrain.ENC_PER_INCH / drivetrain.DISTANCE_BETWEEN_WHEELS;
        return super.getGyroAngle();
    }
}
