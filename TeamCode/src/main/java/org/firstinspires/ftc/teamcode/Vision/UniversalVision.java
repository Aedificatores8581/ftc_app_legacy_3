package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import ftc.vision.Detector;

public class UniversalVision {

    public Mat combinedDetection(Mat image, Detector detector1, Detector detector2){
        detector1.detect(image.clone());
        detector2.detect(image.clone());
        Mat output = new Mat();
        Mat temp = new Mat();
        Core.bitwise_or(detector1.result(), detector2.result(), temp);
        Core.add(detector1.result(), detector2.result(), temp, output);
        return output;
    }

    public static void rgb2hsvThresh(Mat rgb, Mat threshold, Scalar min, Scalar max){
        Mat hsv = new Mat();
        Imgproc.cvtColor(rgb, hsv, Imgproc.COLOR_RGB2HSV_FULL);
        Core.inRange(hsv, min, max, threshold);
        hsv.release();
    }

    public static void rgb2hsvThresh(Mat rgb, Mat threshold, Scalar min, Scalar max, Mat mask){
        Mat thresh = new Mat();
        rgb2hsvThresh(rgb, thresh, min, max);
        thresh.copyTo(threshold, mask);
        thresh.release();
    }

    public static void rgb2hlsThresh(Mat rgb, Mat threshold, Scalar min, Scalar max){
        Mat hls = new Mat();
        Imgproc.cvtColor(rgb, hls, Imgproc.COLOR_RGB2HLS_FULL);
        Core.inRange(hls, min, max, threshold);
        hls.release();
    }

    public static void rgb2hlsThresh(Mat rgb, Mat threshold, Scalar min, Scalar max, Mat mask){
        Mat temp = new Mat();
        rgb2hlsThresh(rgb, temp, min, max);
        temp.copyTo(threshold, mask);
        temp.release();
    }

    public static void rgb2labThresh(Mat rgb, Mat threshold, Scalar min, Scalar max){
        Mat lab = new Mat();
        Imgproc.cvtColor(rgb, lab, Imgproc.COLOR_RGB2Lab);
        Core.inRange(lab, min, max, threshold);
        lab.release();
    }

    public static void rgb2labThresh(Mat rgb, Mat threshold, Scalar min, Scalar max, Mat mask){
        Mat temp = new Mat();
        rgb2labThresh(rgb, temp, min, max);
        temp.copyTo(threshold, mask);
        temp.release();
    }

    public static void rgb2yuvThresh(Mat rgb, Mat threshold, Scalar min, Scalar max){
        Mat yuv = new Mat();
        Imgproc.cvtColor(rgb, yuv, Imgproc.COLOR_RGB2YUV);
        Core.inRange(yuv, min, max, threshold);
        yuv.release();
    }

    public static void rgb2yuvThresh(Mat rgb, Mat threshold, Scalar min, Scalar max, Mat mask){
        Mat temp = new Mat();
        rgb2yuvThresh(rgb, temp, min, max);
        temp.copyTo(threshold, mask);
        temp.release();
    }

    public static void rgbaThresh(Mat rgba, Mat threshold, Scalar min, Scalar max){
        Mat rgb = new Mat();
        Imgproc.cvtColor(rgba, rgb, Imgproc.COLOR_RGBA2RGB);
        Core.inRange(rgb, min, max, threshold);
        rgb.release();
    }

    public static void rgbaThresh(Mat rgba, Mat threshold, Scalar min, Scalar max, Mat mask){
        Mat temp = new Mat();
        rgbaThresh(rgba, temp, min, max);
        temp.copyTo(threshold, mask);
        temp.release();
    }

    public static void drawContours(Mat rgb, Mat dst, double gausianSize){
        Mat gray = new Mat();
        Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.GaussianBlur(gray, gray, new Size(gausianSize, gausianSize), 2, 2);
        Imgproc.findContours(gray ,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(dst,contours,-1,new Scalar(230,70,70),2);
        //TODO: Make sure this line does not cause a runtime exception
        for(MatOfPoint m : contours){
            m.release();
        }
        gray.release();
    }
    public static List<MatOfPoint> drawContoursREEEELEASEMATSPLEASE(Mat rgb, Mat dst, double gausianSize){
        Mat gray = new Mat();
        Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.GaussianBlur(gray, gray, new Size(gausianSize, gausianSize), 2, 2);
        Imgproc.findContours(gray ,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(dst,contours,-1,new Scalar(230,70,70),2);
        //TODO: Make sure this line does not cause a runtime exception
        gray.release();
        return contours;
    }

    public static void drawContours(Mat rgb, Mat dst){
        drawContours(rgb, dst, 9);
    }

    public static void drawContours(Mat rgb, double gausianSize){
        //TODO: Make sure this line does not cause a runtime exception
        drawContours(rgb.clone(), rgb, gausianSize);
    }

    public static void drawContours(Mat rgb){
        drawContours(rgb.clone(), rgb, 9);
    }
}
