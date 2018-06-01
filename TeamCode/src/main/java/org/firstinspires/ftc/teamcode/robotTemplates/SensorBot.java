package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Frank Portman on 5/27/2018
 */
public abstract class SensorBot extends Robot {
    public DcMotor lm, rm;
    Servo phoneServo1, phoneServo2;
    double  ps1InitPos = 0,
            ps2InitPos = 0;
    public TankDT drivetrain = new TankDT(0.05) {
        @Override
        public void init(){
            usingIMU = false;
            super.init();
        }
        @Override
        public void setLeftPow(double pow) {
            setPower(lm, pow);
            leftPow  = pow;
        }

        @Override
        public void setRightPow(double pow) {
            setPower(rm, pow);
            rightPow = pow;
        }

        @Override
        public void initMotors() {

        }
        public void normalizeMotors(){

        }
    };
    @Override
    public void init(){
        super.init();
        lm = hardwareMap.dcMotor.get("lm");
        rm = hardwareMap.dcMotor.get("rm");
        lm.setDirection(drivetrain.FORWARD);
        rm.setDirection(drivetrain.REVERSE);
        drivetrain.init();
        //phoneServo1 = hardwareMap.servo.get("ps1");
        //phoneServo2 = hardwareMap.servo.get("ps2");
        //phoneServo1.setPosition(ps1InitPos);
        //phoneServo2.setPosition(ps2InitPos);
    }
    public void start(){
        super.start();
    }
    @Override
    public void loop(){

    }
}