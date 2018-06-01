package org.firstinspires.ftc.teamcode.robotTemplates;

/**
 * Created by Frank Portman on 5/29/2018
 */
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

public abstract class HolonomicDT extends Drivetrain {
    public double turnPow, turnMult;
    public TurnState turnState;
    public HolonomicDT(double brakePow){super(brakePow);}
    public void init(){
        super.init();
    }
    public void start(){
        super.start();
    }

    //sets the power of the motors in order to drive at a given angle at a given speed
    public abstract void setVelocity(double ang, double speed);
    //sets the power of the motors in order to drive at a given velocity
    public abstract void setVelocity(Vector2 vel);
    //sets the power of the left fore and right rear motors
    public void setTurn(double pow){
        pow *= turnMult;
        turnPow = pow;
    }
    public abstract void refreshMotors();

    public enum TurnState{
        ARCADE,
        FIELD_CENTRIC
    }
    public void switchTurnState(){
        turnMult = 1 - leftStick1.magnitude() * (1 - minTurn);
        switch (turnState) {
            case ARCADE:
                setTurn(rightStick1.x);
                break;
            case FIELD_CENTRIC:
                setTurn(Math.sin(rightStick1.angleBetween(robotAngle)));
                break;
        }
    }
}
