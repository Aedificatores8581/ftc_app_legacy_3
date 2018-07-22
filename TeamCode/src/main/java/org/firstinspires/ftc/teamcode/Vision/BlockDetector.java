package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import ftc.vision.Detector;

public class BlockDetector extends Detector {
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

    public Mat workingImage = new Mat(), hsvImage= new Mat(), threshold= new Mat(), i = new Mat(), thresh = new Mat(),
            invert = new Mat(), hsv = new Mat(), r = new Mat(), g = new Mat(), b = new Mat();
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


                //TODO: Add approximate location of images on the screen
                //TODO: Add contour detection
                break;
        }

    }
    public Mat result(){
        return workingImage;
    }
    public void tune(Mat image){
        Mat threshold2 = new Mat();
        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert = new Mat();
        Imgproc.cvtColor(image, invert, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
        hsvImage.copyTo(hsv);
        Core.extractChannel(image, r, 0);
        Core.extractChannel(image, g, 1);
        Core.extractChannel(image, b, 2);

        Core.inRange(hsv, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), thresh);
        Core.inRange(invert, new Scalar(R_MIN, G_MIN, B_MIN), new Scalar(R_MAX, G_MAX, B_MAX), threshold2);
        Core.bitwise_and(thresh, threshold2, threshold);

        Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.dilate(threshold, threshold, erosionFactor);
        Mat mask = new Mat(image.size(), 0);
        threshold.copyTo(mask);
        image.copyTo(i, mask);

        //Mat dilationFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
        //Imgproc.dilate(thresh, thresh, dilationFactor);
        //Imgproc.GaussianBlur(i, i, new Size(9, 9), 2, 2);
        //Imgproc.HoughCircles(i, i, Imgproc.CV_HOUGH_GRADIENT, 1, i.rows()/8, 100, 20, 0, 0);
        i.copyTo(workingImage);
        threshold = new Mat();
        hsvImage = new Mat();
        hsv = new Mat();
        thresh = new Mat();
        i = new Mat();
        invert  = new Mat();
    }
}
