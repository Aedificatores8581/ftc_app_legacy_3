package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class MecanumDT extends HolonomicDT {
    DcMotor lf, la, rf, ra;
    public double       leftForePow,
                        rightForePow,
                        leftAftPow,
                        rightAftPow,
                        angleBetween;
    public MecanumDT(double brakePow){
        super(brakePow);
    }
    public void init(){
        super.init();
        minTurn = 1;
    }
    public void start(){
        super.start();
    }

    //returns the power of the left rear and right fore motors needed to drive at a given angle at a given speed
    public double getRightForePow(double ang, double speed){
        return Math.sin(ang + Math.PI / 4) * speed / 2;
    }

    //returns the power of the left rear and right fore motors needed to drive along a given vector
    public double getRightForePow(Vector2 vel){
        return getRightForePow(vel.angle(), vel.magnitude());
    }

    //returns the power of the left fore and right rear motors needed to drive at a given angle at a given speed
    public double getLeftForePow(double ang, double speed){
        return Math.cos(ang + Math.PI / 4) * speed / 2;
    }
    //returns the power of the left fore and right rear motors needed to drive along a given vector
    public double getLeftForePow(Vector2 vel){
        return getLeftForePow(vel.angle(), vel.magnitude());
    }
    //sets the power of the motors in order to drive at a given angle at a given speed
    public void setVelocity(double ang, double speed){
        rightForePow = getRightForePow(ang, speed);
        leftForePow = getLeftForePow(ang, speed);
        leftAftPow = rightForePow;
        rightAftPow = leftForePow;
    }
    //sets the power of the motors in order to drive at a given velocity
    public void setVelocity(Vector2 vel){
        setVelocity(vel.angle(), vel.magnitude());
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
    //sets the turnMult variable
    public void setTurnMult(double tm){
        turnMult = tm;
    }
    //normalizes the motor powers to never go above 1 or below -1
    public void normalizeMotors(){
        double max = UniversalFunctions.maxAbs(leftForePow, rightForePow, leftAftPow, rightAftPow);
        leftForePow /= max;
        rightForePow /= max;
        rightAftPow /= max;
        leftAftPow /= max;
    }

    public void setTurnPow(){
        leftForePow += turnPow;
        leftAftPow += turnPow;
        rightForePow -= turnPow;
        rightAftPow -= turnPow;
    }
    public void loop() {
        updateGamepad1();
        setRobotAngle();
        angleBetween = leftStick1.angleBetween(robotAngle);
        setVelocity(angleBetween, leftStick1.magnitude());
        switchTurnState();
        setTurnPow();
        normalizeMotors();
        refreshMotors();
    }
    public void brake(){
        refreshMotors(brakePow, brakePow, -brakePow, -brakePow);
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

}
