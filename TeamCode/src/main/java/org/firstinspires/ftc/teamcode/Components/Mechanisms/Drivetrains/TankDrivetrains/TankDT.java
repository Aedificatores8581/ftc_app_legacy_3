package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */
public abstract class TankDT extends Drivetrain {
    public double       turnMult,
                        angleBetween,
                        directionMult = 1,
                        cos,
                        maxTurn       = 1,
                        leftPow,
                        rightPow;
    private boolean     turn          = false;
    public Direction    direction;
    public ControlState controlState;
    public FCTurnState  turnState;
    public TankDT(double brakePow){
        super(brakePow);
        leftPow = 0;
        rightPow = 0;
    }
    //Different control systems
    public enum ControlState{
        ARCADE,
        TANK,
        FIELD_CENTRIC
    }
    //Two algorithms for turning in field-centric mode
    public enum FCTurnState{
        SMOOTH,
        FAST
    }
    //Basic Tele-Op driving functionality
    public void teleOpLoop(Vector2 leftVect, Vector2 rightVect, Vector2 angle){
        switch(controlState) {
            case ARCADE:
                turnMult = 1 - leftVect.magnitude() * (1 - maxTurn);
                leftPow = -leftVect.y - turnMult * rightVect.x;
                rightPow = -leftVect.y + turnMult * rightVect.x;
                break;
            case FIELD_CENTRIC:
                turnMult = 1;
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
                    cos = Math.cos(angleBetween);
                    switch(turnState){
                        case FAST:
                            turnMult = Math.abs(cos) + 1;
                            leftPow = directionMult * (-leftVect.magnitude() - turnMult * cos);
                            rightPow = directionMult * (-leftVect.magnitude() + turnMult * cos);
                            break;
                        case SMOOTH:
                            if(cos > 0) {
                                rightPow = -directionMult;
                                leftPow = directionMult * Math.cos(2 * angleBetween);
                            }
                            else{
                                leftPow = -directionMult;
                                rightPow = directionMult * Math.cos(2 * angleBetween);
                            }
                    }
                }
                break;
            case TANK:
                leftPow = -leftVect.y;
                rightPow = -rightVect.y;
                break;
        }
        setLeftPow();
        setRightPow();
    }
    //returns the direction the robot is moving
    public Direction setTurnDir(){
        if(leftPow + rightPow > 0)
            direction = Direction.FOR;
        else if(leftPow != rightPow)
            direction = Direction.BACK;
        return direction;
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
}