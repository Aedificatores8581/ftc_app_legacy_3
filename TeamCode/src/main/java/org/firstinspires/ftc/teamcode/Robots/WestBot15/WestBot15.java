package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15;
import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.Robot;

 /**
 * Created by Frank Portman on 6/1/2018
 */
public abstract class WestBot15 extends Robot {
    //:TODO: Add an encoder to the servo
    protected WestCoast15 drivetrain = new WestCoast15();
    MotoG4 motoG4 = new MotoG4();
    Servo thetaAdjustor;
    protected double currentAngle = 0;
    private final double MOTO_ZERO_POSITION_THETA = 0,
                         MOTO_ONE_POSITION_THETA = Math.PI * 2;
    @Override
    public void init(){
        super.init();
        drivetrain.maxSpeed = 0.5;
        drivetrain.initMotors(hardwareMap);
        thetaAdjustor = hardwareMap.servo.get("theta");
        msStuckDetectInit = 50000;

    }
    @Override
    public void start(){
        super.start();
    }
    public void setPhoneTheta(double theta){
        thetaAdjustor.setPosition(0);
    }
}
