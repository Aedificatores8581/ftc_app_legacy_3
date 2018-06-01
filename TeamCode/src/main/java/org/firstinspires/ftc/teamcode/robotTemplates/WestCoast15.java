package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;

/**
 * Created by Frank Portman on 5/21/2018
 */

public class WestCoast15 extends WestCoastDT{
    public DcMotor rf, lf, lr, rr;
    public double turnMult, mult, cos;
    public boolean turn;
    public double angleBetween;

    public WestCoast15(double brakePow, double sped){
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
<<<<<<< HEAD
        super.loop();
        initMotors();
=======
        updateGamepad1();

        switch(controlState) {
            case ARCADE:
                turnMult = 1 - lStick1.magnitude() * (1 - super.turnMult);
                leftPow = -lStick1.y - turnMult * rStick1.x;
                rightPow = -lStick1.y + turnMult * rStick1.x;
                break;
            case FIELD_CENTRIC:
                setRobotAngle();
                angleBetween = lStick1.angleBetween(robotAngle);
                if (lStick1.magnitude() < UniversalConstants.Triggered.STICK)
                    brake();
                else {
                    switch (direction) {
                        case FOR:
                            if (Math.sin(angleBetween) < 0 && turn) {
                                direction = Direction.BACK;
                                mult *= -1;
                                turn = false;
                            } else if (Math.sin(angleBetween) >= 0)
                                turn = true;
                            break;
                        case BACK:
                            if (Math.sin(angleBetween) > 0 && turn) {
                                direction = Direction.FOR;
                                turn = false;
                                mult *= -1;
                            } else if (Math.sin(angleBetween) <= 0)
                                turn = true;
                            break;
                    }
                    cos = Math.cos(angleBetween);
                    turnMult = (Math.abs(cos) + 1);
                    leftPow = mult * (-lStick1.magnitude() - turnMult * cos);
                    rightPow = mult * (-lStick1.magnitude() + turnMult * cos);
                }
                break;
            case TANK:
                leftPow = -gamepad1.left_stick_y;
                rightPow = -gamepad1.right_stick_y;
                break;
        }
        setLeftPow();
        setRightPow();
>>>>>>> master
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
<<<<<<< HEAD
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

=======

    public DcMotor[] motors(){
        DcMotor[] motors = {rf, lf, lr, rr};
        return motors;
    }

    public String[] names(){
        String[] names = {"rf", "lf", "lr", "rr"};
        return names;
    }

    public DcMotor.Direction[] dir(){
        DcMotor.Direction[] dir = {FORWARD, REVERSE, REVERSE, FORWARD};
        return dir;
>>>>>>> master
    }

}
