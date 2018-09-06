package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Frank Portman on 6/2/2018
 */
public class MotorEncoder {

    public DcMotor    motor;
    public int resetPosition   = 0,
               currentPosition = 0;


    public MotorEncoder(DcMotor dc){
        motor = dc;
    }

    //Sets the motor's runmode to RUN_USING_ENCODER
    public void initEncoder(){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        resetPosition = motor.getCurrentPosition();
    }

    //Updates and returns the current position of the encoder
    public double updateEncoder(){
        currentPosition = motor.getCurrentPosition() - resetPosition;
        return currentPosition;
    }

    //Sets resetPosition to the motor encoder's current position
    public void resetEncoder(){
        resetPosition = motor.getCurrentPosition();
    }


    //resets the encoder in the motor and sets the currentPosition and resetPosition variables to 0
    public void hardResetEncoder(){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        currentPosition = 0;
        resetPosition = 0;
    }

}
