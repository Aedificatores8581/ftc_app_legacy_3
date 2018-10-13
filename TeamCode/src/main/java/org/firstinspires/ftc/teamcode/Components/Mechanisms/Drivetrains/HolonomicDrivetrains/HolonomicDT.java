package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.HolonomicDrivetrains;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;

/**
 * Created by Frank Portman on 5/29/2018
 */
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

public abstract class HolonomicDT extends Drivetrain {
    public double turnPow, turnMult;
    public TurnState turnState;

    // Sets the velocity based on angle and speed.
    public abstract void setVelocity(double ang, double speed);

    // Sets motor power to drive at a given velocity.
    public abstract void setVelocity(Vector2 vel);

    // Sets motor power in relation the turn multiplier.
    public void setTurn(double pow){
        pow *= turnMult;
        turnPow = pow;
    }
    //updates the motor powers of the drivetrain
    public abstract void refreshMotors();
    //Represents two types of turning
    public enum TurnState{
        ARCADE,
        FIELD_CENTRIC
    }
    //Calls setTurn based on the current turnState
    public void switchTurnState(Vector2 velocity, Vector2 turnVector, Vector2 angle){
        turnMult = 1 - velocity.magnitude() * (1 - minTurn);
        switch (turnState) {
            case ARCADE:
                setTurn(turnVector.x);
                break;

            case FIELD_CENTRIC:
                setTurn(Math.sin(velocity.angleBetween(angle)));
                break;
        }
    }
    public void switchTurnState(Vector2 velocity, double turnVector, Vector2 angle){
        turnMult = 1 - velocity.magnitude() * (1 - minTurn);
        switch (turnState) {
            case ARCADE:
                setTurn(turnVector);
                break;

            case FIELD_CENTRIC:
                setTurn(Math.sin(velocity.angleBetween(angle)));
                break;
        }
    }
}