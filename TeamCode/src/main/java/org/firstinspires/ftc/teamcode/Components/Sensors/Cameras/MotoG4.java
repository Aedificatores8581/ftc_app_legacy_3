package org.firstinspires.ftc.teamcode.Components.Sensors.Cameras;

import org.firstinspires.ftc.teamcode.Components.Sensors.DigitalCamera;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Size;

import java.lang.Math;
public class MotoG4{
    public DigitalCamera rearCamera;
    public DigitalCamera frontCamera;
    public MotoG4(){
        frontCamera = new DigitalCamera(3.584 / 2 / Math.tan(14 * Math.PI / 15), 1.4 * 10E-3, 1920, 2560);
        rearCamera = new DigitalCamera(3.6, 1.2E-3, 3120, 4208);
    }
    public void setLocationAndOrientation(Point3 location, Point3 orientation){
        rearCamera.setLocationAndOrientation(location, orientation);
        frontCamera.setLocation(rearCamera);
        frontCamera.updateOrientation(Math.PI, Math.PI, Math.PI);
    }
    public void setLocation(Point3 location){
        frontCamera.setLocation(location);
        rearCamera.setLocation(location);
    }
    public Point3 getLocation(){
        return new Point3(rearCamera.x, rearCamera.y, rearCamera.z);
    }
    /*public void setOrientation(Point3 orientation){
        rear.xAng
    }*/
}
