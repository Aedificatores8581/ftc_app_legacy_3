package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */
public abstract class TankDT extends Drivetrain {
    public double       turnMult     ,
                        angleBetween ,
                        directionMult = 1,
                        cos          ,
                        maxTurn       = 1,
                        leftPow      ,
                        rightPow     ;

    public boolean      turn          = false;

    public double       max;

    public Direction    direction;
    public ControlState controlState;
    public FCTurnState  turnState;

    public TankDT(){
        leftPow = 0;
        rightPow = 0;
    }

    //Different control systems
    public enum ControlState{
        ARCADE,
        TANK,
        FIELD_CENTRIC,
        FIELD_CENTRIC_VECTOR
    }

    //Two algorithms for turning in field-centric mode
    public enum FCTurnState{
        SMOOTH,
        FAST
    }

    // TODO: Might need a fix, who knows.
    //Basic Tele-Op driving functionality
    public void teleOpLoop(Vector2 leftVect, Vector2 rightVect, Vector2 angle){
        switch(controlState) {
            case ARCADE:
                turnMult = 1 - leftVect.magnitude() * (1 - maxTurn);
                leftPow = leftVect.y + turnMult * rightVect.x;
                rightPow = leftVect.y - turnMult * rightVect.x;
                break;

            case FIELD_CENTRIC:
                angleBetween = leftVect.angleBetween(angle);
                if (leftVect.magnitude() < UniversalConstants.Triggered.STICK) {
                    leftPow = 0;
                    rightPow = 0;
                } else {
                    switch (direction) {
                        case FOR:
                            if (Math.sin(angleBetween) < 0 && turn) {
                                direction = Direction.BACK;
                                directionMult *= -1;
                                turn = false;
                            } else if (Math.sin(angleBetween) >= 0)
                                turn = true;
                            break;

                        case BACK:
                            if (Math.sin(angleBetween) > 0 && turn) {
                                direction = Direction.FOR;
                                turn = false;
                                directionMult *= -1;
                            } else if (Math.sin(angleBetween) <= 0)
                                turn = true;
                            break;
                    }
                    switch(turnState){
                        case FAST:
                            cos = Math.cos(angleBetween);
                            turnMult = Math.abs(cos) + 1;
                            leftPow = directionMult * (leftVect.magnitude() + turnMult * cos);
                            rightPow = directionMult * (leftVect.magnitude() - turnMult * cos);
                            break;

                        case SMOOTH:
                            cos = Math.cos(angleBetween);
                            if(cos > 0) {
                                leftPow = directionMult * leftVect.magnitude();
                                rightPow = directionMult * -Math.cos(2 * angleBetween) * leftVect.magnitude();
                            } else {
                                rightPow = directionMult * leftVect.magnitude();
                                leftPow = directionMult * -Math.cos(2 * angleBetween) * leftVect.magnitude();
                            }
                            break;
                    }
                }
                break;
            case FIELD_CENTRIC_VECTOR:
                angleBetween = leftVect.angleBetween(angle);
                if (leftVect.magnitude() < UniversalConstants.Triggered.STICK) {
                    leftPow = 0;
                    rightPow = 0;
                } else {
                }
                switch (direction) {
                    case FOR:
                        leftPow = Math.sin(angleBetween) + Math.cos(angleBetween);
                        rightPow = Math.sin(angleBetween) - Math.cos(angleBetween);

                        switch(turnState){
                            case SMOOTH:
                                max = UniversalFunctions.maxAbs(leftPow, rightPow);
                                leftPow = leftPow / max * leftVect.magnitude();
                                rightPow = rightPow / max * leftVect.magnitude();
                                break;
                            case FAST:
                                leftPow *= leftVect.magnitude();
                                rightPow *= leftVect.magnitude();
                                break;
                        }
                        if (Math.sin(angleBetween) < 0 && turn) {
                            direction = Direction.BACK;
                            directionMult *= -1;
                            turn = false;
                        } else if (Math.sin(angleBetween) >= 0)
                            turn = true;
                        break;

                    case BACK:
                        leftPow = Math.sin(angleBetween) - Math.cos(angleBetween);
                        rightPow = Math.sin(angleBetween) + Math.cos(angleBetween);
                        switch(turnState){
                            case SMOOTH:
                                max = UniversalFunctions.maxAbs(leftPow, rightPow);
                                leftPow = leftPow / max * leftVect.magnitude();
                                rightPow = rightPow / max * leftVect.magnitude();
                                break;
                            case FAST:
                                leftPow *= leftVect.magnitude();
                                rightPow *= leftVect.magnitude();
                                break;
                        }
                        if (Math.sin(angleBetween) > 0 && turn) {
                            direction = Direction.FOR;
                            turn = false;
                            directionMult *= -1;
                        } else if (Math.sin(angleBetween) <= 0)
                            turn = true;
                        break;
                }
                break;
            case TANK:
                leftPow = -leftVect.y;
                rightPow = -rightVect.y;
                break;
        }
        setLeftPow(leftPow);
        setRightPow(rightPow);
    }

    //returns the direction the robot is moving
    public Direction setDirection(){
        if(leftPow + rightPow > 0)
            direction = Direction.FOR;
        else if(leftPow != rightPow)
            direction = Direction.BACK;
        return direction;
    }
    //TODO: implement location tracking to find currentPos
    public void autotomousLoop1(Vector2 currentPos, Vector2 destination, double tolerance, double turnSpeed, boolean normalized){
        turnSpeed = Math.abs(turnSpeed);
        switch(direction){
            case FOR:
                if(isFacing(currentPos, destination, tolerance)){
                    leftPow = (destination.y + destination.x) * Math.sqrt(2) / 2;
                    rightPow = (destination.y - destination.x) * Math.sqrt(2) / 2;
                }
                else
                    turnToFace(currentPos, destination, turnSpeed);
                break;
            case BACK:
                if(isFacingBack(currentPos, destination, tolerance)){
                    leftPow = (destination.y - destination.x) * Math.sqrt(2) / 2;
                    rightPow = (destination.y + destination.x) * Math.sqrt(2) / 2;
                }
                else
                    turnToFace(currentPos, destination, turnSpeed);
                break;
        }
        if(normalized && UniversalFunctions.maxAbs(leftPow, rightPow) > 1){
            max = UniversalFunctions.max(leftPow, rightPow);
            leftPow /= max;
            rightPow /= max;
        }
    }
    //Sets the power of the left motor(s)
    public abstract void setLeftPow(double pow);

    //Sets the power of the right motor(s)
    public abstract void setRightPow(double pow);

    //Sets the power of the left motor(s) to the leftPow variable
    public void setLeftPow(){
        setLeftPow(leftPow);
    }

    //Sets the power of the right motor(s) to the rightPow variable
    public void setRightPow(){
        setRightPow(rightPow);
    }

    //Sets the maximum speed of the drive motors
    public void setSpeed(double speed){
        maxSpeed = speed;
    }
    //turns the front of the robot to the specified direction
    public void turnToFace(Vector2 currentAngle, Vector2 desiredAngle, double tolerance, double turnSpeed){
        angleBetween = currentAngle.angleBetween(desiredAngle);
        if(!isFacing(currentAngle, desiredAngle, tolerance)){
            leftPow = angleBetween > 0 ? -turnSpeed : turnSpeed;
            rightPow = angleBetween > 0 ? turnSpeed : -turnSpeed;
        }
    }
    //returns a boolean representing whether the drivetrain is facing a given direction
    public boolean isFacing(Vector2 currentAngle, Vector2 desiredAngle, double tolerance){
        return UniversalFunctions.withinTolerance(-tolerance, currentAngle.angleBetween(desiredAngle), tolerance);
    }
    //returns a boolean representing whether the drivetrain is facing a given direction
    public boolean isFacingBack(Vector2 currentAngle, Vector2 desiredAngle, double tolerance){
        return UniversalFunctions.withinTolerance(-tolerance, UniversalFunctions.normalizeAngleRadians(currentAngle.angleBetween(desiredAngle) + Math.PI), tolerance);
    }
    //turns the front of the robot to the specified direction
    public void turnToFace(Vector2 currentAngle, Vector2 desiredAngle, double turnSpeed){
        angleBetween = currentAngle.angleBetween(desiredAngle);
        turnSpeed *= Math.sin(angleBetween);
        leftPow = -turnSpeed;
        rightPow = turnSpeed;
    }
    //Turns the back of the robot to the specified direction
    public void turnToFaceBack(Vector2 currentAngle, Vector2 desiredAngle, double turnSpeed){
        angleBetween = UniversalFunctions.normalizeAngleRadians(currentAngle.angleBetween(desiredAngle) + Math.PI);
        turnSpeed *= Math.sin(angleBetween);
        leftPow = -turnSpeed;
        rightPow = turnSpeed;
    }
}