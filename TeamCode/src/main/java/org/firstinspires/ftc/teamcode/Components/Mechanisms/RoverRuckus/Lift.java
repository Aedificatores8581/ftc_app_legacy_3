package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    public DcMotor liftMotor;
    public Servo topRatchetServo, sideRatchetServo;
    //TODO: find these values
    public final double  TOP_RATCHET_NOT_ENGAGED  = 0 ,
                         TOP_RATCHET_ENGAGED   = 1 ,
                         SIDE_RATCHET_NOT_ENGAGED = 0 ,
                         SIDE_RATCHET_ENGAGED  = 1 ;
    private final double TIME_TO_SWITCH_MS          = 20,
                         TICKS_PER_INCH             = 0,
                         MAX_LIFT_DISTANCE          = 30;

    private double height;
    private double timer;

    private RatchetState ratchetState;

    public Lift() {
        ratchetState = RatchetState.DISENGAGED;
        switchRatchetState();
        height = 0;
    }

    public Lift(RatchetState ratchetState) {
        this.ratchetState = ratchetState;
        switchRatchetState();
        height = 0;
    }

    public void init(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.dcMotor.get("lift");

        topRatchetServo = hardwareMap.servo.get("trat");
        sideRatchetServo = hardwareMap.servo.get("srat");

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void switchRatchetState(){
        resetTimer();
        switch (ratchetState){
            case DISENGAGED:
            case UP:
                topRatchetServo.setPosition(TOP_RATCHET_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_NOT_ENGAGED);
                break;

            case DOWN:
                topRatchetServo.setPosition(TOP_RATCHET_NOT_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_ENGAGED);
                break;

            case STOPPED:
                topRatchetServo.setPosition(TOP_RATCHET_NOT_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_NOT_ENGAGED);
                break;
        }
        setPower();
    }

    public void setPower(){
        setPower(liftMotor.getPower());
    }
    public void setPower(double power) {
        if(hasSwitched()) {
            switch (ratchetState) {
                case UP:
                    if (power > 0 || getHeight() < MAX_LIFT_DISTANCE)
                        liftMotor.setPower(power);
                    break;

                case DOWN:
                    if (power < 0 || getHeight() > 0) liftMotor.setPower(power);
                    break;

                case DISENGAGED:
                    liftMotor.setPower(power);
                    break;

                case STOPPED:
                    liftMotor.setPower(0);
                    break;
            }
        } else {
            liftMotor.setPower(0);
        }
    }

    public void setPowerOverride(double power) {
        if (power != 0) {
            if (ratchetState != RatchetState.DISENGAGED) {
                ratchetState = power > 0 ? RatchetState.UP : RatchetState.DOWN;
            }
        }

        setPower(power);
    }

    public void  stop() {
        ratchetState = RatchetState.STOPPED;
        switchRatchetState();
    }

    public double getHeight(){
        return liftMotor.getCurrentPosition() * TICKS_PER_INCH;
    }

    private void resetTimer(){
        timer = System.nanoTime() * 10E6;
    }

    private boolean hasSwitched(){
        return System.currentTimeMillis() - timer > TIME_TO_SWITCH_MS;
    }

    public enum RatchetState { UP, DOWN, DISENGAGED, STOPPED }

    public String toString(){
        return ratchetState + ", " + height + " inches upwards";
    }
}
