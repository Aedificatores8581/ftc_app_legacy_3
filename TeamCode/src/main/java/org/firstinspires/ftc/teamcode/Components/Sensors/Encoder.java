package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.AnalogInput;

/**
 * Created by Frank Portman on 6/1/2018
 */
public class Encoder{
    AnalogInput              encoder;
    public int               directionMult;
    public double            ticks;
    public final double      TICK_VOLTAGE;
    public RotationDirection rotationDirection;
    public Encoder(AnalogInput enc, double voltage, RotationDirection dir){
        TICK_VOLTAGE = voltage;
        directionMult = dir.equals(RotationDirection.CLOCKWISE) ? 1 : -1;
        encoder = enc;
    }
    //Updates the position of the encoder
    public void updateEncoder(){
        if(encoder.getVoltage() > TICK_VOLTAGE)
            ticks += directionMult;
    }
    //Switches whether the number of ticks increases or decreases when the encoder is updated
    public void switchDirection(){
        switch(rotationDirection){
            case CLOCKWISE:
                directionMult *= -1;
                rotationDirection = RotationDirection.COUNTER_CLOCKWISE;
                break;
            case COUNTER_CLOCKWISE:
                directionMult *= -1;
                rotationDirection = RotationDirection.CLOCKWISE;
                break;
        }
    }
    //Direction that the encoder is rotated
    public enum RotationDirection{
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }
}
