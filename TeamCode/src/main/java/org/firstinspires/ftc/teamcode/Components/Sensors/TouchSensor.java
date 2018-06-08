package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Frank Portman on 6/8/2018
 */
public class TouchSensor {
    private DigitalChannel touchSensor;
    public void init(HardwareMap hardwareMap, String name){
        touchSensor = hardwareMap.digitalChannel.get(name);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }
    public boolean isPressed(){
        return touchSensor.getState();
    }
    public String toString(){
        return isPressed() ? "pressed" : "released";
    }
}
