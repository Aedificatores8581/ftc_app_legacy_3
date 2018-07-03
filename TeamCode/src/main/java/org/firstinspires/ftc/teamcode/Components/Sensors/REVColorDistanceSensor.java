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
    public double                sumOfColors    = 0;
    //initializes the color sensor and distance sensor
    public void init(HardwareMap hardwareMap, String color, String distance){
        initColorSensor(hardwareMap, color);
        initDistanceSensor(hardwareMap, distance);
    }
    //initializes the color sensor
    public void initColorSensor(HardwareMap hardwareMap, String color){
        hardwareMap.get(NormalizedColorSensor.class, color);
    }
    //initializes the distance sensor
    public void initDistanceSensor(HardwareMap hardwareMap, String distance){
        hardwareMap.get(DistanceSensor.class, distance);
    }
    //Updates the values of the color sensor
    public synchronized void updateColorSensor(){
        colors = colorSensor.getNormalizedColors();
        sumOfColors = colors.red + colors.blue + colors.green;
    }
    //Returns the percentage of red recorded during the last update of the color sensor
    public double getRed(){
        return colors.red / sumOfColors* 100;
    }
    //Returns the percentage of blue recorded during the last update of the color sensor
    public double getBlue(){
        return colors.blue / sumOfColors* 100;
    }
    //Returns the percentage of green recorded during the last update of the color sensor
    public double getGreen(){
        return colors.green / sumOfColors * 100;
    }
    //Returns the opacity recorded during the last update of the color sensor
    public double getOpacity(){
        return colors.alpha;
    }
    //TODO: Test the sensor to implement opacity into the raw distance.
    //Returns the raw values returned by the color sensor.
    public double getRawDistance(){
        return sumOfColors / 3;
    }
    //TODO: linearize the output of the getDistance functions.
    //Returns the distance read by the distance sensor in centimeters
    public synchronized double getDistanceCM(){
        return distanceSensor.getDistance(DistanceUnit.CM);
    }
    //Returns the distance read by the distance sensor in inches
    public synchronized double getDistanceIN(){
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }
    //Returns the distance read by the distance sensor in millimeters
    public synchronized double getDistanceMM(){
        return distanceSensor.getDistance(DistanceUnit.MM);
    }
    //Returns the distance read by the distance sensor in meter
    public synchronized double getDistanceM(){
        return distanceSensor.getDistance(DistanceUnit.METER);
    }
    public String toString(){
        return "%R: " + getRed() + ", %G: " + getGreen() + ", %B: " + getBlue() + ", A: " + getOpacity() + ", Distance: " + getDistanceIN();
    }
}
