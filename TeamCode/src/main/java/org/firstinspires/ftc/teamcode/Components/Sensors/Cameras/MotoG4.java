package org.firstinspires.ftc.teamcode.Components.Sensors.Cameras;

import org.firstinspires.ftc.teamcode.Components.Sensors.DigitalCamera;

import java.lang.Math;
public class MotoG4{
    //TODO: Fix the non-static import error when using the math class to define the focal length of the front camera in a way which makes sense
    public static
    RearCamera rear;
    FrontCamera front;
    public MotoG4(){
        rear = new RearCamera();
        front = new FrontCamera(3.584 / 2 / Math.tan(14 * Math.PI / 15));
    }
    public class RearCamera extends DigitalCamera{
        public static final double FOCAL_LENGTH = 3.6,
                PIXEL_SIZE = 1.12 * Math.pow(10, -3),
                NUM_PIXELS_WIDTH = 4208,
                NUM_PIXELS_HEIGHT = 3120;

        public RearCamera() {
            super(FOCAL_LENGTH, PIXEL_SIZE, NUM_PIXELS_WIDTH, NUM_PIXELS_HEIGHT);
        }
    }
    public class FrontCamera extends DigitalCamera{
        public static final double FOCAL_LENGTH = 0,
                PIXEL_SIZE = 1.4 * Math.pow(10, -3),
                NUM_PIXELS_WIDTH = 2560,
                NUM_PIXELS_HEIGHT = 1920;/*
        public static final double FOCAL_LENGTH = 3.584 / 2 / Math.tan(14 * Math.PI / 15),
                PIXEL_SIZE = 1.4 * Math.pow(10, -3),
                NUM_PIXELS_WIDTH = 2560,
                NUM_PIXELS_HEIGHT = 1920;
*/
        public FrontCamera(double focalLength) {
            super(focalLength, PIXEL_SIZE, NUM_PIXELS_WIDTH, NUM_PIXELS_HEIGHT);
        }
    }
}
