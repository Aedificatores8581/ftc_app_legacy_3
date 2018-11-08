package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    public DcMotor liftMotor;
    public Servo topRatchetServo, sideRatchetServo;
    public final double TOP_RATCHET_ZERO_POSITION  = 0,
                        TOP_RATCHET_ONE_POSITION   = 1,
                        SIDE_RATCHET_ZERO_POSITION = 0,
                        SIDE_RATCHET_ONE_POSITION  = 1;
    private final double TIME_TO_SWITCH_MS = 20;
    public double height;
    private double timer;
    RatchetState ratchetState;

    public Lift(){
        ratchetState = RatchetState.DISENGAGED;
        switchRatchetState();
    }
    public Lift(RatchetState ratchetState){
        this.ratchetState = ratchetState;
        switchRatchetState();
    }
    public void switchRatchetState(){
        resetTimer();
        switch (ratchetState){
            case UP:
                topRatchetServo.setPosition(TOP_RATCHET_ONE_POSITION);
                sideRatchetServo.setPosition(SIDE_RATCHET_ZERO_POSITION);
                break;
            case DOWN:
                topRatchetServo.setPosition(TOP_RATCHET_ZERO_POSITION);
                sideRatchetServo.setPosition(SIDE_RATCHET_ONE_POSITION);
                break;
            case STOPPED:
                topRatchetServo.setPosition(TOP_RATCHET_ZERO_POSITION);
                sideRatchetServo.setPosition(SIDE_RATCHET_ZERO_POSITION);
                break;
            case DISENGAGED:
                topRatchetServo.setPosition(TOP_RATCHET_ONE_POSITION);
                sideRatchetServo.setPosition(SIDE_RATCHET_ONE_POSITION);
                break;
        }
    }
    public void setPower(double pow){
        if(hasSwitched())
            switch (ratchetState) {
                case UP:
                    if (pow > 0)
                        liftMotor.setPower(pow);
                    break;
                case DOWN:
                    if (pow < 0) {
                        liftMotor.setPower(pow);
                    }
                    break;
                case DISENGAGED:
                    liftMotor.setPower(pow);
                    break;
            }
    }
    public void setPowerOverride(double pow){
        if (pow != 0)
            if (ratchetState != RatchetState.DISENGAGED)
                ratchetState = pow > 0 ? RatchetState.UP : RatchetState.DOWN;
        setPower(pow);

    }
    private void resetTimer(){
        timer = System.nanoTime() * 10E6;
    }
    private boolean hasSwitched(){
        return System.currentTimeMillis() - timer > TIME_TO_SWITCH_MS;
    }
    enum RatchetState{
        UP,
        DOWN,
        DISENGAGED,
        STOPPED
    }
}
