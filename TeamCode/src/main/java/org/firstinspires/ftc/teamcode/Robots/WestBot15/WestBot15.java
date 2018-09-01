package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import android.provider.ContactsContract;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
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
    protected WestCoast15 drivetrain = new WestCoast15();
    MotoG4 motoG4 = new MotoG4();
    Servo thetaAdjustor;
    protected double currentAngle = 0;
    PhoneCoordinateSystem phoneCoordinateSystem = PhoneCoordinateSystem.ROBOT;
    private final double MOTO_ZERO_POSITION_THETA = 0,
                         MOTO_ONE_POSITION_THETA = Math.PI * 2;
    @Override
    public void init(){
        super.init();
        drivetrain.maxSpeed = 0.5;
        drivetrain.initMotors(hardwareMap);
        //thetaAdjustor = hardwareMap.servo.get("theta");
        msStuckDetectInit = 50000;
        drivetrain.position = new Pose();
    }
    @Override
    public void start(){
        super.start();
    }
    public void setPhoneTheta(double theta){
        thetaAdjustor.setPosition(0);
    }
    public synchronized void updateLocation(double leftChange, double rightChange){
        double oldX = drivetrain.position.x, oldY = drivetrain.position.y;
        drivetrain.updateLocation(leftChange, rightChange);
        switch(phoneCoordinateSystem){
            case FIELD:
                motoG4.setLocation(UniversalFunctions.add(motoG4.getLocation(), new Point3(drivetrain.position.x, drivetrain.position.y, 0)));
                //motoG4.setLocationAndOrientation();
                break;
        }
    }
    public enum PhoneCoordinateSystem{
        ROBOT,
        FIELD
    }
}
