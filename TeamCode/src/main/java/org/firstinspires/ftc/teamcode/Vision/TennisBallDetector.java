package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import ftc.vision.Detector;

public class TennisBallDetector extends Detector {
    public int H_MIN = 0,
            S_MIN = 132,
            V_MIN = 103,
            H_MAX = 255,
            S_MAX = 255,
            V_MAX = 255;
    /*
    public int H_MIN = 36,
            S_MIN = 7,
            V_MIN = 0,
            H_MAX = 53,
            S_MAX = 255,
            V_MAX = 255;
     */
    //0 69 62 87 255 255

    Mat workingImage = new Mat(), hsvImage= new Mat(), threshold= new Mat(), i = new Mat(), thresh = new Mat(),
     invert = new Mat(), hsv = new Mat();
    public OperatingState opState = OperatingState.TUNING;

    public TennisBallDetector(){
        super();
    }
    public void detect(Mat image){
        switch(opState){
            case TUNING:
                tune(image);
                break;
            case DETECTING:
                workingImage = image;
                Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
                Core.inRange(hsvImage, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), threshold);

                Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
                Mat dilationFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));

                Imgproc.erode(threshold, threshold, erosionFactor);
                Imgproc.erode(threshold, threshold, erosionFactor);
                Imgproc.dilate(threshold, threshold, dilationFactor);
                Imgproc.dilate(threshold, threshold, dilationFactor);
                Mat colSum = new Mat();
                Core.reduce(threshold, colSum, 0, Core.REDUCE_SUM, 4);


                //TODO: Add approximate location of images on the screen
                //TODO: Add contour detection
                break;
        }

    }
    public Mat result(){
        return workingImage;
    }
    public void tune(Mat image){
        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert = new Mat();
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
        hsvImage.copyTo(hsv);
        Core.inRange(hsvImage, new Scalar(39, 42, 59), new Scalar(190, 255, 255), threshold);
        Core.inRange(hsv, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), thresh);

        Core.subtract(thresh, threshold, i);
        Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
        //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
        Imgproc.blur(i, i, new Size(10, 10));
        Imgproc.threshold(i, i, 150, 255, Imgproc.THRESH_BINARY);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(i, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        double maxArea = 0;
        float[] radius = new float[1];
        Point center = new Point();
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint c = contours.get(i);
            if (Imgproc.contourArea(c) > maxArea) {
                MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
                Imgproc.minEnclosingCircle(c2f, center, radius);
            }
        }
        Imgproc.circle(image, center, (int)radius[0], new Scalar(0, 0, 255), 5);
        image.copyTo(workingImage);
        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert  = new Mat();
    }
}
