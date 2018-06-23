package org.firstinspires.ftc.teamcode.Robots.SensorBot;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Frank Portman on 5/27/2018
 */
public abstract class SensorBot extends Robot {
    public DcMotor lm, rm;
    Servo phoneServo1, phoneServo2;
    double  ps1InitPos = 0,
            ps2InitPos = 0;

    public void SensorBot (DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        lm.setZeroPowerBehavior(zeroPowerBehavior);
        rm.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public TankDT drivetrain = new TankDT() {
        @Override
        public void setLeftPow(double pow) {
            leftPow = pow * maxSpeed;
            lm.setPower(leftPow);
        }

        @Override
        public void setRightPow(double pow) {
            rightPow = pow * maxSpeed;
            rm.setPower(rightPow);
        }

        @Override
        public void initMotors(HardwareMap hardwareMap) {
            lm = hardwareMap.dcMotor.get("lm");
            rm = hardwareMap.dcMotor.get("rm");
            lm.setDirection(Drivetrain.FORWARD);
            rm.setDirection(Drivetrain.REVERSE);
        }

        public void normalizeMotors() {
        }
    };
    @Override
    public void init(){
        super.init();
        drivetrain.initMotors(hardwareMap);
        //phoneServo1 = hardwareMap.servo.get("ps1");
        //phoneServo2 = hardwareMap.servo.get("ps2");
        //phoneServo1.setPosition(ps1InitPos);
        //phoneServo2.setPosition(ps2InitPos);
    }
    @Override
    public void start(){
        super.start();
    }
}
