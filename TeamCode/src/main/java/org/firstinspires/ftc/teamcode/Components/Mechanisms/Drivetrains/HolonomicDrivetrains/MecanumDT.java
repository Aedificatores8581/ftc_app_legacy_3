package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.HolonomicDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */
public abstract class MecanumDT extends HolonomicDT {
    public double       leftForePow,
                        rightForePow,
                        leftAftPow,
                        rightAftPow,
                        angleBetween;
    public final double FRONT_TO_BACK_RATIO;


    public MecanumDT(double ratio) {
        FRONT_TO_BACK_RATIO = ratio;
    }

    //returns the power of the left rear and right fore motors needed to drive at a given angle at a given speed
    public double getRightForePow(double ang, double speed){
        return Math.sin(ang + Math.PI / 4) * FRONT_TO_BACK_RATIO * speed / 2;
    }

    //returns the power of the left rear and right fore motors needed to drive along a given vector
    public double getRightForePow(Vector2 vel){
        return getRightForePow(vel.angle(), vel.magnitude());
    }

    //returns the power of the left fore and right rear motors needed to drive at a given angle at a given speed
    public double getLeftForePow(double ang, double speed){
        return Math.cos(ang + Math.PI / 4) * FRONT_TO_BACK_RATIO * speed / 2;
    }

    //returns the power of the left fore and right rear motors needed to drive along a given vector
    public double getLeftForePow(Vector2 vel){
        return getLeftForePow(vel.angle(), vel.magnitude());
    }

    //sets the power of the motors in order to drive at a given angle at a given speed
    public void setVelocity(double ang, double speed){
        rightForePow = getRightForePow(ang, speed);
        leftForePow = getLeftForePow(ang, speed);
        leftAftPow = rightForePow / FRONT_TO_BACK_RATIO;
        rightAftPow = leftForePow / FRONT_TO_BACK_RATIO;
        rightForePow *= FRONT_TO_BACK_RATIO;
        leftForePow *= FRONT_TO_BACK_RATIO;
    }

    //sets the power of the motors in order to drive at a given velocity
    public void setVelocity(Vector2 vel){
        setVelocity(vel.angle(), vel.magnitude());
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

    public void teleOpLoop(Vector2 velocity, Vector2 turnVector, Vector2 angle) {
        angleBetween = velocity.angleBetween(angle);
        setVelocity(angleBetween, velocity.magnitude());
        switchTurnState(velocity, turnVector, angle);
        setTurnPow();
        normalizeMotors();
        refreshMotors();
    }

}