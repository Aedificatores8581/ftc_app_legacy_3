package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Frank Portman on 6/2/2018
 */
public class MotorEncoder {
    DcMotor    motor;
    public int resetPosition   = 0,
               currentPosition = 0;
    public MotorEncoder(DcMotor dc){
        motor = dc;
    }
    public void initEncoder(){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public double updateEncoder(){
        currentPosition = motor.getCurrentPosition() - resetPosition;
        return currentPosition;
    }
    public void resetEncoder(){
        resetPosition = motor.getCurrentPosition();
    }
}
