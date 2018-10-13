package org.firstinspires.ftc.teamcode.Components.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;

/**
 * Created by Frank Portman on 6/2/2018
 */

public class IncrementalMotor {
    public DcMotor      motor;
    public MotorEncoder encoder;
    public double       desiredPow,
                        acceleration,
                        decelleration,
                        currentPow = 0,
                        minAbsolutePow;

    public IncrementalMotor(DcMotor dc, double accPerSec, double decPerSec, double minAbs){
        motor = dc;
        encoder = new MotorEncoder(motor);
        encoder.initEncoder();
        acceleration = Math.abs(accPerSec);
        decelleration = Math.abs(decPerSec);
        minAbsolutePow = minAbs;
    }

    public IncrementalMotor(DcMotor dc, double acc, double dec){
        motor = dc;
        encoder = new MotorEncoder(motor);
        encoder.initEncoder();
        acceleration = Math.abs(acc);
        decelleration = Math.abs(dec);
        minAbsolutePow = acceleration;
    }


    //returns the actual current power of the motor
    public double getPower(){
        return motor.getPower();
    }

    //incrementally sets the power to the desiredPow variable
    public synchronized void setPower(double pow){
        desiredPow = pow;
        if(currentPow == 0) {
            if (desiredPow != 0.0)
                currentPow += Math.signum(desiredPow) * minAbsolutePow;
        }
        if(currentPow < desiredPow){
            if(currentPow > 0)
                currentPow += acceleration;
            else if (currentPow < 0)
                currentPow += decelleration;
            currentPow = Math.min(currentPow, Math.max(Math.signum(currentPow) * Math.abs(desiredPow), 0));
        }
        else if(currentPow > desiredPow){
            if(currentPow > 0){
                currentPow -= decelleration;
                currentPow = Math.max(currentPow, 0);
            }
            else if (currentPow < 0){
                currentPow -= acceleration;
                currentPow = Math.max(currentPow, desiredPow);
            }
            currentPow = Math.max(currentPow, Math.max(Math.signum(currentPow) * desiredPow, 0));
        }
        motor.setPower(currentPow );
    }

    public synchronized void setPower() {
        if (currentPow == 0) {
            if (desiredPow != 0.0)
                currentPow += Math.signum(desiredPow) * minAbsolutePow;
        }

        if (currentPow < desiredPow) {
            if (currentPow > 0)
                currentPow += acceleration;
            else if (currentPow < 0)
                currentPow += decelleration;
            currentPow = Math.min(currentPow, Math.max(Math.signum(currentPow) * Math.abs(desiredPow), 0));

        } else if (currentPow > desiredPow) {
            if (currentPow > 0) {
                currentPow -= decelleration;
                currentPow = Math.max(currentPow, 0);
            } else if (currentPow < 0) {
                currentPow -= acceleration;
                currentPow = Math.max(currentPow, desiredPow);
            }
            currentPow = Math.max(currentPow, Math.max(Math.signum(currentPow) * desiredPow, 0));
        }

        motor.setPower(currentPow);
    }

    //stops the motor
    public void stop(){
        motor.setPower(0);
    }
}
