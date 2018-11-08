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
import org.firstinspires.ftc.teamcode.Universal.Map.Map2;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose3;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
import org.opencv.core.Point3;

/**
 * Created by Frank Portman on 6/1/2018
 */

public abstract class WestBot15 extends Robot {
    //IMPORTANT: phone locations should be taken in relation to the robot, not the field
    //:TODO: Add an encoder to the servo
    public CRServo frontIntake, backIntake;
    public Servo dispensor;
    public DcMotor hang, leftLift, rightLift, extension;
    protected WestCoast15 drivetrain = new WestCoast15(DcMotor.ZeroPowerBehavior.BRAKE, 1.0);

    public Map2 robotMap, fieldMap;
    public boolean hadleyOnSchedule = false;
    public MotoG4 motoG4 = new MotoG4();

    protected double currentAngle = 0;


    @Override
    public void init(){
        msStuckDetectInit = 50000000;
        super.init();
        if(hadleyOnSchedule) {
            frontIntake = hardwareMap.crservo.get("inF");
            backIntake = hardwareMap.crservo.get("inB");
            hang = hardwareMap.dcMotor.get("hang");
            leftLift = hardwareMap.dcMotor.get("liL");
            rightLift = hardwareMap.dcMotor.get("liR");
            extension = hardwareMap.dcMotor.get("ex");
            dispensor = hardwareMap.servo.get("tm");
        }
        drivetrain.maxSpeed = 1.0;
        drivetrain.initMotors(hardwareMap);

        drivetrain.position = new Pose();
        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation(new Point3(0, 0, 0), new Point3(0, 0, 0));
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
    public enum AutoState{
        HANG,
        LOWER,
        SAMPLE,
        CYCLE,
        CLAIM,
        PARK
    }
}
