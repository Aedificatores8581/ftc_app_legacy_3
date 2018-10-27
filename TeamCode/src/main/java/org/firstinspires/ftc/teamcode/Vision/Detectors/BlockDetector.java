package org.firstinspires.ftc.teamcode.Vision.Detectors;

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

public class BlockDetector extends Detector {

    public ArrayList<Point> elements = new ArrayList<Point>(1);
    public Point element = new Point();
    /*public int H_MIN = 21,
            S_MIN = 185,
            V_MIN = 138,
            H_MAX = 44,
            S_MAX = 254,
            V_MAX = 252;*/

    public int H_MIN = 0,
            S_MIN = 185,
            V_MIN = 0,
            H_MAX = 44,
            S_MAX = 255,
            V_MAX = 255;
    public int R_MIN = 164,
    G_MIN = 76,
    B_MIN = 0,
    R_MAX = 243,
    G_MAX = 237,
    B_MAX = 176;
    //0 69 62 87 255 255

    public Mat workingImage = new Mat();
    public Mat hsvImage= new Mat();
    public Mat threshold= new Mat();
    public Mat i = new Mat();
    public Mat thresh = new Mat();
    public Mat invert = new Mat();
    public Mat hsv = new Mat();
    public Mat r = new Mat();
    public Mat g = new Mat();
    public Mat b = new Mat();
    public OperatingState opState = OperatingState.TUNING;

    public BlockDetector(){
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


                break;
        }

    }
    public Mat result(){
        return workingImage;
    }

    public void tune(Mat image){
        Mat threshold2 = new Mat(image.size(), 0);
        threshold = new Mat(image.size(), 0);
        hsvImage = new Mat(image.size(), 0);
        hsv = new Mat(image.size(), 0);
        thresh = new Mat(image.size(), 0);
        i = new Mat(image.size(), 0);
        invert = new Mat(image.size(), 0);

        /*
        Imgproc.cvtColor(image, invert, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
        hsvImage.copyTo(hsv);
        Core.inRange(hsv, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), thresh);
        Core.inRange(invert, new Scalar(R_MIN, G_MIN, B_MIN), new Scalar(R_MAX, G_MAX, B_MAX), threshold2);

        Core.bitwise_and(thresh, threshold2, threshold);
*/
        Imgproc.cvtColor(image, invert, Imgproc.COLOR_RGB2YUV);
        Core.inRange(invert, new Scalar(0, 0, 0), new Scalar(255, 70, 255), threshold);
        //Imgproc.threshold(invert.col(1), threshold, 70, 255, Imgproc.THRESH_BINARY);
        Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.dilate(threshold, threshold, erosionFactor);
        Mat mask = new Mat(image.size(), 0);
        threshold.copyTo(mask);
        image.copyTo(i, mask);

        Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
        //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(threshold, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        float[] radius = new float[1];
        Point center = new Point();
        double area = -1;
        element = new Point();
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint c = contours.get(i);
            double contourArea = Imgproc.contourArea(c);
            if(contourArea > area){
                MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
                Imgproc.minEnclosingCircle(c2f, center, radius);
                element = center.clone();
                area = contourArea;
            }
        }
        //element = center;
        //elements.clear();
        //elements.set(0, center.clone());


        i.copyTo(workingImage);
        Imgproc.drawContours(workingImage, contours,-1, new Scalar(255,0,0),2);
        Imgproc.circle(workingImage, center, (int) radius[0], new Scalar(0, 0, 255), 5);
       // Imgproc.distanceTransform();
        //Mat dilationFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
        //Imgproc.dilate(thresh, thresh, dilationFactor);
        //Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
        //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
        threshold.release();
        hsvImage.release();
        hsv.release();
        thresh.release();
        i.release();
        invert.release();
        threshold2.release();
    }
}
