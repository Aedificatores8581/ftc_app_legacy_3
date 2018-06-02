package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogSensor;

public class Encoder {
    //TODO Finish the code
    AnalogInput encoder;
    public final double TICK_VOLTAGE;
    public Encoder(double voltage){
        TICK_VOLTAGE = voltage;
    }

    public void loop(){

    }
}
