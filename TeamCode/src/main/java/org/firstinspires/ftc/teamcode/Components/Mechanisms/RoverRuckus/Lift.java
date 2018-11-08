package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    public DcMotor liftMotor;
    public Servo topRatchetServo, sideRatchetServo;
    //TODO: find these values
    public final double  TOP_RATCHET_ZERO_POSITION  = 0 ,
                         TOP_RATCHET_ONE_POSITION   = 1 ,
                         SIDE_RATCHET_ZERO_POSITION = 0 ,
                         SIDE_RATCHET_ONE_POSITION  = 1 ;
    private final double TIME_TO_SWITCH_MS          = 20,
                         TICKS_PER_INCH             = 0,
                         MAX_LIFT_DISTANCE          = 30;
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
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        setPower();
    }
    public void setPower(){
        setPower(liftMotor.getPower());
    }
    public void setPower(double pow){
        if(hasSwitched())
            switch (ratchetState) {
                case UP:
                    if (pow > 0 || getHeight() < MAX_LIFT_DISTANCE)
                        liftMotor.setPower(pow);
                    break;
                case DOWN:
                    if (pow < 0 || getHeight() > 0)
                        liftMotor.setPower(pow);
                    break;
                case DISENGAGED:
                    liftMotor.setPower(pow);
                    break;
                case STOPPED:
                    liftMotor.setPower(0);
                    break;
            }
            else
                liftMotor.setPower(0);
    }
    public void setPowerOverride(double pow){
        if (pow != 0)
            if (ratchetState != RatchetState.DISENGAGED)
                ratchetState = pow > 0 ? RatchetState.UP : RatchetState.DOWN;
        setPower(pow);
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
    enum RatchetState{
        UP,
        DOWN,
        DISENGAGED,
        STOPPED
    }
    public String toString(){
        return ratchetState + ", " + height + " inches upwards";
    }
}
