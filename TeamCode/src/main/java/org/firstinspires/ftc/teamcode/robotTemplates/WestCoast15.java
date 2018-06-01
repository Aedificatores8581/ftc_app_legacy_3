package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class WestCoast15 extends TankDT {
    public DcMotor rf, lf, la, ra;
    public WestCoast15(){
        super(0.001);
        maxSpeed = 1;
    }
    public WestCoast15(double brakePow, double speed){
        super(brakePow);
        maxSpeed = speed;
    }
    @Override
    public void init(){
        usingIMU = true;
        super.init();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop(){
        super.loop();
        initMotors();
    }
    public void setLeftPow(double pow) {
        lf.setPower(pow * maxSpeed);
        la.setPower(pow * maxSpeed);
        leftPow = pow;
    }
    public void setRightPow(double pow){
        rf.setPower(pow * maxSpeed);
        ra.setPower(pow * maxSpeed);
        rightPow = pow;
    }
    public void initMotors(){
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
    public void normalizeMotors(){

    }

}
