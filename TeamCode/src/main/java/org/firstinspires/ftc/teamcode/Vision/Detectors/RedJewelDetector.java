package org.firstinspires.ftc.teamcode.Vision.Detectors;


import org.firstinspires.ftc.teamcode.Vision.Detectors.BlockDetector;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import ftc.vision.Detector;

public class RedJewelDetector extends Detector {
    public int H_MIN = 0,
            S_MIN = 132,
            V_MIN = 103,
            H_MAX = 255,
            S_MAX = 255,
            V_MAX = 255;
    public int R_MIN = 100,
            G_MIN = 0,
            B_MIN = 0,
            R_MAX = 255,
            G_MAX = 96,
            B_MAX = 91
                    ;
    public int L_MIN = 0,
    a_MIN = 159,
    b_MIN = 154,
    L_MAX = 146,
    a_MAX = 239,
    b_MAX = 202;
    /*
    public int H_MIN = 36,
            S_MIN = 7,
            V_MIN = 0,
            H_MAX = 53,
            S_MAX = 255,
            V_MAX = 255;
     */
    //0 69 62 87 255 255
    public List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    public Mat workingImage = new Mat(), hsvImage= new Mat(), threshold= new Mat(), i = new Mat(), thresh = new Mat(),
            invert = new Mat(), hsv = new Mat(), r = new Mat(), g = new Mat(), b = new Mat();
    public OperatingState opState = OperatingState.TUNING;
    public float[] radius = {1};
    public float max = 0;
    public int numcenters = 0, contsize = 0;
    public double ratio = 0;
    public RedJewelDetector(){
        super();
    }
    public void detect(Mat image){
        switch(opState){
            case TUNING:
                tune(image);
                break;
            case DETECTING:
                Mat threshold2 = new Mat();
                threshold = new Mat();
                hsvImage = new Mat();
                hsv = new Mat();
                thresh = new Mat();
                i = new Mat();
                invert = new Mat();
                Mat gray = new Mat();
                Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGBA2RGB);
                Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
                hsvImage.copyTo(hsv);
                Core.inRange(hsvImage, new Scalar(39, 42, 59), new Scalar(190, 255, 255), threshold);

                Core.inRange(hsv, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), thresh);

                Core.subtract(thresh, threshold, i);

                Core.inRange(gray, new Scalar(R_MIN, G_MIN, B_MIN), new Scalar(R_MAX, G_MAX, B_MAX), threshold2);
                Core.bitwise_and(i, threshold2, threshold);
                threshold.copyTo(i);
                Core.extractChannel(image, r, 0);
                Core.extractChannel(image, g, 1);
                Core.extractChannel(image, b, 2);

                Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
                Imgproc.erode(threshold, threshold, erosionFactor);
                Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
                //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
                Imgproc.blur(i, i, new Size(10, 10));
                Imgproc.threshold(i, i, 150, 255, Imgproc.THRESH_BINARY);
                Imgproc.findContours(i, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
                double maxArea = 50;
                float[] radius = new float[1];
                Point center = new Point();
                for (int i = 0; i < contours.size(); i++) {
                    MatOfPoint c = contours.get(i);
                    if (Imgproc.contourArea(c) > maxArea) {
                        numcenters++;
                        MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
                        Imgproc.minEnclosingCircle(c2f, center, radius);

                    }
                }
                Rect roi = new Rect((int)center.x, (int)center.y, 2 * (int)radius[0], 2 * (int)radius[0]);
                Mat cropped = new Mat(threshold, roi);
                //Core.inRange(cropped, new Scalar(200), new Scalar(255))
                ratio = ((double)Core.countNonZero(cropped) / (cropped.rows() * cropped.cols()));
                Imgproc.circle(image, center, (int)radius[0], new Scalar(0, 0, 255), 5);
                Imgproc.putText(image,"approx" + (int)(480 * 3.7 / (int)radius[0] / 2) + "inches", new Point(center.x, center.y - (int)radius[0]), 1, 2, new Scalar(255, 255, 255), 1);
                threshold.copyTo(workingImage);

                break;
        }

    }
    public Mat result(){
        return workingImage;
    }
    public void tune(Mat image){
        Mat i2 = new Mat();
        image.copyTo(i2);
        BlockDetector bl = new BlockDetector();
        bl.G_MIN = 122;
        bl.R_MAX = 255;
        bl.tune(image);
        Core.subtract(image, bl.workingImage, image);
        Mat threshold2 = new Mat();
        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert = new Mat();
        Mat labImage = new Mat();
        Imgproc.cvtColor(image, labImage, Imgproc.COLOR_RGB2Lab);
        List<Mat> labChannels = new ArrayList<Mat>();
        Core.split(labImage, labChannels);
        Mat l = new Mat();
        Mat a = new Mat();
        Mat b = new Mat();
        Imgproc.threshold(labChannels.get(0), l, L_MIN, L_MAX, Imgproc.THRESH_BINARY);

        Imgproc.threshold(labChannels.get(1), a, a_MIN, a_MAX, Imgproc.THRESH_BINARY);

        Imgproc.threshold(labChannels.get(2), b, b_MIN, b_MAX, Imgproc.THRESH_BINARY);
        Mat temp = new Mat();
        Core.bitwise_and(a, l, temp);
        Core.bitwise_and(temp, b, i);

  //      Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
//        Imgproc.erode(threshold, threshold, erosionFactor);
        //Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
        //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
        //Imgproc.blur(i, i, new Size(10, 10));
        //Imgproc.threshold(i, i, 150, 255, Imgproc.THRESH_BINARY);
            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Imgproc.findContours(i, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            double maxArea = 0;
            radius = new float[1];
            Point center = new Point();
            contsize = contours.size();
            for (int i = 0; i < contours.size(); i++) {
                MatOfPoint c = contours.get(i);
                if (Imgproc.contourArea(c) > maxArea) {
                    MatOfPoint2f c2f = new MatOfPoint2f(c.toArray());
                    Imgproc.minEnclosingCircle(c2f, center, radius);
                }
            }
            /*Mat cropped = new Mat();
            Rect roi = new Rect((int) center.x, (int) center.y, 2 * (int) radius[0], 2 * (int) radius[0]);
            if (image.cols() > center.x + roi.width && center.x + roi.width > 0 && center.y + roi.height > 0 && image.rows() > center.y + roi.height && contours.size() > 0) {
                threshold.submat(roi).copyTo(cropped);
                Mat BN = new Mat(cropped.width(), cropped.height(), 0);

                Imgproc.threshold(cropped, BN, 123, 255, Imgproc.THRESH_BINARY);

                Mat m = new Mat(cropped.width(), cropped.height(), 0);
                Core.extractChannel(BN, m, 0);
                int n = Core.countNonZero(m);
                ratio = ((double) n / (cropped.rows() * cropped.cols()));
                Mat a = new Mat(image.height(), image.width(), 0, new Scalar(0,0,0));
                Imgproc.rectangle(a, roi.br(), roi.tl(), new Scalar(255, 255, 255), -1);
                if (ratio > 0.32 || ratio < 0.1 || (int) (480 * 3.7 / (int) radius[0] / 2) > 17 * 12) {

                    Imgproc.circle(i, center, (int) radius[0], new Scalar(0, 0, 0), -1);
                }
                else {
                    circleDetected = false;
                    Imgproc.circle(image, center, (int) radius[0], new Scalar(0, 0, 255), 5);
                    Imgproc.putText(image, "approx" + (int) (480 * 3.7 / (int) radius[0] / 2) + "inches", new Point(center.x, center.y - (int) radius[0]), 1, 2, new Scalar(255, 255, 255), 1);
                }
            }

*/

        for (MatOfPoint contour : contours) {
            radius = new float[1];
            center = new Point();
            MatOfPoint2f approx = new MatOfPoint2f();
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(contour2f, approx, 0.01 * Imgproc.arcLength(contour2f, true), true);
            double area = Imgproc.contourArea(contour);
            if (Imgproc.contourArea(contour) > maxArea) {

                MatOfPoint2f c2f = new MatOfPoint2f(contour.toArray());
                Imgproc.minEnclosingCircle(c2f, center, radius);
            }
            Mat temp2 = new Mat(i, Imgproc.boundingRect(contour));
            if (approx.elemSize() > 7 && (int) (480 * 3.7 / (int) radius[0] / 2) < 17 * 12 && UniversalFunctions.withinTolerance((double)Imgproc.boundingRect(contour).height / Imgproc.boundingRect(contour).width ,0.8, 1.25) && Core.mean(temp2).val[0] > 60) {
                Imgproc.circle(i2, center, (int) radius[0], new Scalar(0, 0, 255), 5);
                Imgproc.putText(i2, "approx" + (int) (480 * 3.7 / (int) radius[0] / 2) + "inches", new Point(center.x, center.y - (int) radius[0]), 1, 2, new Scalar(255, 255, 255), 1);
                double x = 3.7 / radius[0] / 2 * (center.x - image.width() / 2);
                double y = 3.7 / radius[0] / 2 * (center.y - image.height() / 2);
                double z = (int) (480 * 3.7 / (int) radius[0] / 2);
            }
        }
                Imgproc.drawContours(i2,contours,-1,new Scalar(230,70,70),2);
            i2.copyTo(workingImage);

        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert  = new Mat();

        //135 0 0 255 96 91
    }
}
