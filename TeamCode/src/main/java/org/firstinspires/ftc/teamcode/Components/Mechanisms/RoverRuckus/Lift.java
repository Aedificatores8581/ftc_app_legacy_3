package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    public DcMotor liftMotor;
    public Servo topRatchetServo, sideRatchetServo;

    //TODO: Tune these values.
    public final double  TOP_RATCHET_ZERO_POSITION  = 0,
                         TOP_RATCHET_ONE_POSITION   = 1,
                         SIDE_RATCHET_ZERO_POSITION = 0,
                         SIDE_RATCHET_ONE_POSITION  = 1;
    private final double TIME_TO_SWITCH_MS          = 20,
                         TICKS_PER_INCH             = 0;

    public double height;
    private double timer;
    RatchetState ratchetState;

    public Lift(){
        ratchetState = RatchetState.DISENGAGED;
        switchRatchetState();
        height = 0;
    }

    public Lift(RatchetState ratchetState){
        this.ratchetState = ratchetState;
        switchRatchetState();
        height = 0;
    }

    public void init(HardwareMap hardwareMap){
        liftMotor = hardwareMap.dcMotor.get("lift");

        topRatchetServo = hardwareMap.servo.get("trat");
        sideRatchetServo = hardwareMap.servo.get("srat");

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void setPower(double power) {
        if(hasSwitched()) {
            switch (ratchetState) {
                case UP:
                    if (power > 0) liftMotor.setPower(power);
                    break;
                case DOWN:
                    if (power < 0) liftMotor.setPower(power);
                    break;
                case DISENGAGED:
                    liftMotor.setPower(power);
                    break;
            }
        }
    }

    public void setPowerOverride(double power) {
            if (ratchetState != RatchetState.DISENGAGED && power != 0) {
                ratchetState = power > 0 ? RatchetState.UP : RatchetState.DOWN;
            }
        setPower(power);
    }

    public double getHeight() {
        return liftMotor.getCurrentPosition() * TICKS_PER_INCH;
    }
    private void resetTimer() {
        timer = System.nanoTime() * 10E6;
    }

    private boolean hasSwitched() {
        return System.currentTimeMillis() - timer > TIME_TO_SWITCH_MS;
    }

    enum RatchetState {
        UP,
        DOWN,
        DISENGAGED,
        STOPPED
    }

    public String toString() {
        return ratchetState + ", " + height + " inches upwards";
    }
}
