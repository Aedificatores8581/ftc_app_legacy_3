package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Frank Portman on 6/8/2018
 */
public class TouchSensor {
    private DigitalChannel touchSensor;
    //initializes the motors
    public void init(HardwareMap hardwareMap, String name){
        touchSensor = hardwareMap.digitalChannel.get(name);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }
    //returns whether the sensor is pressed
    public boolean isPressed(){
        return touchSensor.getState();
    }
    public String toString(){
        return isPressed() ? "pressed" : "released";
    }
}
