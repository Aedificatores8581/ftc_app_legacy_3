package org.firstinspires.ftc.teamcode.Components.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus.Lift;

/**
 * Written by Theodore Lovinski
 * but heavily draws from code written by Frank Portman
 * 10/11/2018
 */

public class Ratchet {
    public DcMotor ratchetMotor;
    public Servo topRatchetServo, sideRatchetServo;

    private RatchetState ratchetState;

    // TODO: Tune these values, see Lift.java.
    public final double TOP_RATCHET_NOT_ENGAGED = 0,
                        TOP_RATCHET_ENGAGED = 1,
                        SIDE_RATCHET_NOT_ENGAGED = 0,
                        SIDE_RATCHET_ENGAGED = 1;

    public void init(HardwareMap hardwareMap) {
        ratchetMotor = hardwareMap.dcMotor.get("rmot");

        topRatchetServo = hardwareMap.servo.get("trat");
        sideRatchetServo = hardwareMap.servo.get("srat");

        ratchetMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ratchetMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ratchetMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public enum RatchetState { UP, DOWN, STOPPED, DISENGAGED }

    public void setRatchetState(RatchetState state) {
        ratchetState = state;

        switch (ratchetState) {
            case UP:
                topRatchetServo.setPosition(TOP_RATCHET_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_NOT_ENGAGED);
                break;

            case DOWN:
                topRatchetServo.setPosition(TOP_RATCHET_NOT_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_ENGAGED);
                break;

            case DISENGAGED:
                topRatchetServo.setPosition(TOP_RATCHET_NOT_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_NOT_ENGAGED);
                break;

            case STOPPED:
                topRatchetServo.setPosition(TOP_RATCHET_ENGAGED);
                sideRatchetServo.setPosition(SIDE_RATCHET_ENGAGED);
                break;
        }
    }

    // stop() is technically unnecessary, but for readability, purposes it exists.
    public void stop() { ratchetState = RatchetState.STOPPED; }
    public void setPower(double power) {
        switch (ratchetState) {
            case UP:
            case DOWN:
                ratchetMotor.setPower(power);
                break;

            case STOPPED:
            case DISENGAGED:
                ratchetMotor.setPower(0);
                break;
        }
    }

    public String toString() { return ratchetState + ""; }
}
