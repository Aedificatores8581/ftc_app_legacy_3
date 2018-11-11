package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.AExtendotm;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Intake;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Lift;
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
    public boolean isAutonomous = false;
    //IMPORTANT: phone locations should be taken in relation to the robot, not the field
    //public Intake intaek = new Intake();
    //public Lift lift = new Lift();
    //public AExtendotm aextendo = new AExtendotm();
    protected WestCoast15 drivetrain = new WestCoast15(DcMotor.ZeroPowerBehavior.BRAKE, 1.0);

    public Map2 robotMap, fieldMap;
    public boolean hadleyOnSchedule = false;
    public MotoG4 motoG4 = new MotoG4();
    protected double currentAngle = 0;


    @Override
    public void init(){
        msStuckDetectInit = 50000000;
        super.init();

        drivetrain.maxSpeed = 1.0;
        drivetrain.initMotors(hardwareMap);

        drivetrain.position = new Pose();
        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation(new Point3(2.3, 3.5, 11.7), new Point3(0, 0, 0));
        /*if (false) {
            aextendo.init(hardwareMap, isAutonomous);
            intaek.init(hardwareMap);
            lift.init(hardwareMap);
        }*/
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
