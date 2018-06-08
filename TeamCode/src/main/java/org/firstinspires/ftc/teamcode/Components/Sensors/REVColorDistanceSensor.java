package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Frank Portman on 6/8/2018
 */
public class REVColorDistanceSensor {
    public NormalizedColorSensor colorSensor;
    public NormalizedRGBA        colors;
    public DistanceSensor        distanceSensor;
    public double                sumOfColors = 0;
    public void init(HardwareMap hardwareMap, String color, String distance){
        initColorSensor(hardwareMap, color);
        initDistanceSensor(hardwareMap, distance);
    }
    public void initColorSensor(HardwareMap hardwareMap, String color){
        hardwareMap.get(NormalizedColorSensor.class, color);
    }
    public void initDistanceSensor(HardwareMap hardwareMap, String distance){
        hardwareMap.get(DistanceSensor.class, distance);
    }
    public void updateColorSensor(){
        colors = colorSensor.getNormalizedColors();
        sumOfColors = colors.red + colors.blue + colors.green;
    }
    public double getRed(){
        return colors.red / sumOfColors* 100;
    }
    public double getBlue(){
        return colors.blue / sumOfColors* 100;
    }
    public double getGreen(){
        return colors.green / sumOfColors * 100;
    }
    public double getOpacity(){
        return colors.alpha;
    }
    //TODO: Test the sensor to implement opacity into the raw distance.
    public double getRawDistance(){
        return sumOfColors / 3;
    }
    //TODO: linearize the output of the getDistance functions.
    public double getDistanceCM(){
        return distanceSensor.getDistance(DistanceUnit.CM);
    }
    public double getDistanceIN(){
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }
    public double getDistanceMM(){
        return distanceSensor.getDistance(DistanceUnit.MM);
    }
    public double getDistanceM(){
        return distanceSensor.getDistance(DistanceUnit.METER);
    }
    public String toString(){
        return "%R: " + getRed() + ", %G: " + getGreen() + ", %B: " + getBlue() + ", A: " + getOpacity() + ", Distance: " + getDistanceIN();
    }
}
