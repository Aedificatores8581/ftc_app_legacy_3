package org.firstinspires.ftc.teamcode.Components.Sensors;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose3;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
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
    public void setLocation(Point3 location){
        x = location.x;
        y = location.y;
        z = location.z;
    }
    public void setLocation(DigitalCamera aCamera){
        setLocation(new Point3(aCamera.x, aCamera.y, aCamera.z));
    }
    public void setOrientation(Point3 orientation){
        xAng = orientation.x;
        yAng = orientation.y;
        zAng = orientation.z;
    }
    public void setOrientation(DigitalCamera aCamera){
        setOrientation(new Point3(aCamera.xAng, aCamera.yAng, aCamera.zAng));
    }
    public void setLocationAndOrientation(Point3 location, Point3 orientation){
        setOrientation(orientation);
        setLocation(location);
    }
    public void setLocationAndOrientation(DigitalCamera aCamera){
        setLocation(aCamera);
        setOrientation(aCamera);
    }
    public void setLocationAndOrientation(Pose3 pose){
        x = pose.x;
        y = pose.y;
        z = pose.z;
        xAng = pose.rho;
        yAng = pose.phi;
        zAng = pose.theta;
    }
    //TODO: Fix variable names
    public Point getObjectLocation(Point pointOnImage, Size imageSize, double objectHeight){
        Vector2 temp = new Vector2(pointOnImage.y, -pointOnImage.x);
        temp.x -= imageSize.height / 2;
        temp.y -= imageSize.width / 2;

        double vertAng = temp.y / imageSize.width * verticalAngleOfView();
        double horiAng = temp.x / imageSize.height * horizontalAngleOfView();

        double newY = (z - objectHeight / 2) / Math.tan(vertAng);
        double newX = newY * Math.tan(horiAng);
        return new Point(newX, newY);
    }
    public Point getObjectLocation2(Point pointOnImage, Size imageSize, double objectHeight){
        pointOnImage.x -= imageSize.width / 2;
        pointOnImage.y -= imageSize.height / 2;
        Vector2 temp = new Vector2(pointOnImage.x, pointOnImage.y);
        temp.rotate(Math.PI / 2 + yAng);
        double theta = Math.PI / 2 - temp.y / imageSize.height * verticalAngleOfView() + xAng;
        double rho = Math.PI / 2 - temp.x / imageSize.width * horizontalAngleOfView() - zAng;
        return new Point((z - objectHeight / 2) * Math.tan(theta) * Math.cos(rho), (z - objectHeight / 2) * Math.tan(theta) * Math.sin(rho));
    }
    public void updateLocation(double xChange, double yChange, double zChange){
        x += xChange;
        y += yChange;
        z += zChange;
    }
    public void updateLocation(Point3 differentialPosition){
        updateLocation(differentialPosition.x, differentialPosition.y, differentialPosition.z);
    }
    public void updateOrientation(double xChange, double yChange, double zChange){
        xAng += xChange;
        yAng += yChange;
        zAng += zChange;
        normalizeAngles();
    }
    public void updateOrientation(Point3 differentialOrientation){
        updateOrientation(differentialOrientation.x, differentialOrientation.y, differentialOrientation.z);
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
    public void normalizeAngles(){
        UniversalFunctions.normalizeAngleRadians(xAng);
        UniversalFunctions.normalizeAngleRadians(yAng);
        UniversalFunctions.normalizeAngleRadians(zAng);
    }
}
