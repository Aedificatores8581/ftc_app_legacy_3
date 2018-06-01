package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class Mecanum2 extends MecanumDT {
    DcMotor lf, la, rf, ra;
    public Mecanum2(){
        super(0.01, 1);
        maxSpeed = 1;
    }
    public Mecanum2(double brakePow, double speed) {
        super(brakePow, 1);
        maxSpeed = speed;
    }
    @Override
    public void init() {
        usingIMU = true;
        super.init();
        initMotors();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop() {
        super.loop();
    }
    public void  initMotors(){
        rf = hardwareMap.dcMotor.get("rf");
        lf = hardwareMap.dcMotor.get("lf");
        la = hardwareMap.dcMotor.get("la");
        ra = hardwareMap.dcMotor.get("ra");
        rf.setDirection(REVERSE);
        ra.setDirection(REVERSE);
        lf.setDirection(FORWARD);
        la.setDirection(FORWARD);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        la.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
        ra.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.UNKNOWN);
    }
    //sets the motor powers to the specified values
    public void refreshMotors(double I, double II, double III, double IV){
        rf.setPower(I);
        lf.setPower(II);
        la.setPower(III);
        ra.setPower(IV);
    }
    //sets the motor powers to the specified values at the specified speed
    public void refreshMotors(double I, double II, double III, double IV, double speed){
        rf.setPower(speed * I);
        lf.setPower(speed * II);
        la.setPower(speed * III);
        ra.setPower(speed * IV);
    }
    //sets the motor powers to rightForePow and leftForePow respectively
    public void refreshMotors(){
        setPower(rf, rightForePow);
        setPower(la, leftAftPow);
        setPower(lf, leftForePow);
        setPower(ra, rightAftPow);
    }
    public void brake(){
        refreshMotors(brakePow, brakePow, -brakePow, -brakePow);
    }
}
