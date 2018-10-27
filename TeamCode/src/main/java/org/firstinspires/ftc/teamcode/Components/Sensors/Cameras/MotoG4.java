package org.firstinspires.ftc.teamcode.Components.Sensors.Cameras;

import org.firstinspires.ftc.teamcode.Components.Sensors.DigitalCamera;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Size;

import java.lang.Math;
public class MotoG4{
    public RearCamera rear;
    public FrontCamera front;
    public MotoG4(){
        rear = new RearCamera();
        front = new FrontCamera(3.584 / 2 / Math.tan(14 * Math.PI / 15));
    }
    public void setLocationAndOrientation(Point3 location, Point3 orientation){
        rear.setLocationAndOrientation(location, orientation);
        front.setLocation(rear);
        front.updateOrientation(Math.PI, Math.PI, Math.PI);
    }
    public void setLocation(Point3 location){
        front.setLocation(location);
        rear.setLocation(location);
    }
    public Point3 getLocation(){
        return new Point3(rear.x, rear.y, rear.z);
    }
    /*public void setOrientation(Point3 orientation){
        rear.xAng
    }*/
    public Point getObjectLocationRear(Point pointOnImage, Size imageSize, double objectHeight){
        return rear.getObjectLocation(pointOnImage, imageSize, objectHeight);
    }
    public double verticalAngleOfViewRear(){
        return rear.verticalAngleOfView();
    }
    public double horizontalAngleOfViewRear(){
        return rear.horizontalAngleOfView();
    }
}

class RearCamera extends DigitalCamera{
    static final double FOCAL_LENGTH = 3.6,
            PIXEL_SIZE = 1.12 * Math.pow(10, -3),
            NUM_PIXELS_WIDTH = 3120,
            NUM_PIXELS_HEIGHT = 4208;

    RearCamera() {
        super(FOCAL_LENGTH, PIXEL_SIZE, NUM_PIXELS_WIDTH, NUM_PIXELS_HEIGHT);
    }

}
class FrontCamera extends DigitalCamera{
    static final double FOCAL_LENGTH = 0,
            PIXEL_SIZE = 1.4 * Math.pow(10, -3),
            NUM_PIXELS_WIDTH = 2560,
            NUM_PIXELS_HEIGHT = 1920;/*
        public static final double FOCAL_LENGTH = 3.584 / 2 / Math.tan(14 * Math.PI / 15),
                PIXEL_SIZE = 1.4 * Math.pow(10, -3),
                NUM_PIXELS_WIDTH = 2560,
                NUM_PIXELS_HEIGHT = 1920;
*/
    FrontCamera(double focalLength) {
        super(focalLength, PIXEL_SIZE, NUM_PIXELS_WIDTH, NUM_PIXELS_HEIGHT);
    }
}
