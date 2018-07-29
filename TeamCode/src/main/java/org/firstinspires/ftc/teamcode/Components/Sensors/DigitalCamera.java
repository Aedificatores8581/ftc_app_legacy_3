package org.firstinspires.ftc.teamcode.Components.Sensors;

import org.firstinspires.ftc.teamcode.robotUniversal.Pose;
import org.firstinspires.ftc.teamcode.robotUniversal.Pose3;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector3;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Size;

public class DigitalCamera {
    //all calculations are done in millimeters
    public double focalLength = 0;
    public CameraSensor cameraSensor;
    public double x = 0, y = 0, z = 0;
    public double xAng = 0, yAng = 0, zAng = 0;
    public class CameraSensor{
        public final double width,
                            height;
        public CameraSensor(double width, double height){
            this.width = width;
            this.height = height;
        }
        public CameraSensor(double pixelSize, double numPixelsX, double numPixelsY){
            this.width = pixelSize * numPixelsX;
            this.height = pixelSize * numPixelsY;
        }
    }
    //TODO: Add magnification to calculations
    public DigitalCamera(double focalLength, double pixelSize, double resolutionX, double resolutionY){
        this.focalLength = focalLength;
        cameraSensor = new CameraSensor(pixelSize, resolutionX, resolutionY);
    }
    public DigitalCamera(double focalLength, double width, double height){
        this.focalLength = focalLength;
        cameraSensor = new CameraSensor(width, height);
    }
    //TODO: Fix variable names
    public Point getObjectLocation(Point pointOnImage, Size imageSize, double widthRatio, double heightRatio, double objectHeight){
        pointOnImage.x -= imageSize.width / 2;
        pointOnImage.y -= imageSize.height / 2;
        Vector2 temp = new Vector2(pointOnImage.x, pointOnImage.y);
        temp.rotate(yAng);
        pointOnImage.x = temp.x + imageSize.width / 2;
        pointOnImage.y = temp.y + imageSize.height / 2;
        double phi = Math.PI / 4 - horizontalAngleOfView(widthRatio) * (pointOnImage.x / imageSize.width - 0.5) + zAng;
        double theta = Math.PI / 4 - verticalAngleOfView(heightRatio) * (pointOnImage.y / imageSize.height - 0.5) - xAng;
        Point3 newPoint = new Point3(UniversalFunctions.sphericalToCartesian(1, theta, phi));
        double newX = newPoint.x * (z - objectHeight) / -newPoint.z + x;
        double newY = newPoint.y * (z - objectHeight) / -newPoint.z + y;
        return new Point(newX, newY);
    }
    public double horizontalAngleOfView(double widthRatio){
        return 2 * Math.atan2(cameraSensor.width * widthRatio, 2 * focalLength);
    }
    public double horizontalAngleOfView(){
        return horizontalAngleOfView(1);
    }
    public double verticalAngleOfView(double heightRatio){
        return 2 * Math.atan2(cameraSensor.height * heightRatio, 2 * focalLength);
    }
    public double verticalAngleOfView(){
        return verticalAngleOfView(1);
    }
}
