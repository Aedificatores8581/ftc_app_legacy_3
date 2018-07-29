package org.firstinspires.ftc.teamcode.Vision.Detectors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import ftc.vision.Detector;

public class GenericDetector extends Detector {
    public int L_MIN = 0,
                a_MIN = 0,
                b_MIN = 0,
                L_MAX = 255,
                a_MAX = 255,
                b_MAX = 255;
    public int H_MIN = 0,
            S_MIN = 0,
            V_MIN = 0,
            H_MAX = 255,
            S_MAX = 255,
            V_MAX = 255;
    public int h_MIN = 0,
            s_MIN = 0,
            l_MIN = 0,
            h_MAX = 255,
            s_MAX = 255,
            l_MAX = 255;
    public int R_MIN = 0,
    R_MAX = 255,
    G_MIN = 0,
    G_MAX = 255,
    B_MIN = 0,
    B_MAX = 255;

    Mat hlsH = new Mat();
    Mat hlsL = new Mat();
    Mat hlsS = new Mat();
    Mat rgbR = new Mat();
    Mat rgbG = new Mat();
    Mat rgbB = new Mat();
    Mat mask = new Mat();
    Mat labL = new Mat();
    Mat labA = new Mat();
    Mat labB = new Mat();

    Mat hsvH = new Mat();
    Mat hsvS = new Mat();
    Mat hsvV = new Mat();

    Mat yuvY = new Mat();
    Mat yuvU = new Mat();
    Mat yuvV = new Mat();
    Mat temp = new Mat();
    Mat temp2 = new Mat();
    Mat i2 = new Mat();
    Mat labImage = new Mat();
    Mat hsvImage = new Mat();
    Mat hlsImage = new Mat();
        public Mat workingImage = new Mat(), i = new Mat();
        public OperatingState opState = OperatingState.TUNING;
        public GenericDetector(){
            super();
        }
        public void detect(Mat image){
            switch(opState){
                case TUNING:
                    tune(image);
                    break;
                case DETECTING:

                    break;
            }

        }
        public void releaseMats (){
            rgbR.release();
            rgbG.release();
            rgbB.release();
            hsvH.release();
            hsvS.release();
            hsvV.release();
            labA.release();
            labB.release();
            labL.release();
            hlsH.release();
            hlsL.release();
            hlsS.release();
            i2.release();
            i.release();
            temp.release();
            hsvImage.release();
            temp2.release();
            yuvY.release();
            yuvU.release();
            yuvV.release();
            mask.release();
        }
        public Mat result(){
            return workingImage;
        }
        public void tune(Mat image) {

            rgbR = new Mat(image.size(), 0);
            rgbG = new Mat(image.size(), 0);
            rgbB = new Mat(image.size(), 0);

            labL = new Mat(image.size(), 0);
            labA = new Mat(image.size(), 0);
            labB = new Mat(image.size(), 0);

            hsvH = new Mat(image.size(), 0);
            hsvS = new Mat(image.size(), 0);
            hsvV = new Mat(image.size(), 0);

            yuvY = new Mat(image.size(), 0);
            yuvU = new Mat(image.size(), 0);
            yuvV = new Mat(image.size(), 0);

            labImage = new Mat();
            hsvImage = new Mat(image.size(), 0);
            hlsImage = new Mat(image.size(), 0);
            Mat gray = new Mat(image.size(), 0);
            Imgproc.cvtColor(image, labImage, Imgproc.COLOR_RGB2Lab);
            Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
            Imgproc.cvtColor(image, hlsImage, Imgproc.COLOR_RGB2HLS_FULL);
            Imgproc.cvtColor(image, image, Imgproc.COLOR_RGBA2RGB);
            Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);
            temp = new Mat();
            Core.inRange(image, new Scalar(R_MIN, G_MIN, B_MIN), new Scalar(R_MAX, G_MAX, B_MAX), temp);
            mask = new Mat(image.size(), 0);
            temp.copyTo(mask);
            i = new Mat(image.size(), 0);
            //image.copyTo(i, mask);
            workingImage.release();
            workingImage = new Mat();
            //image.copyTo(workingImage, mask);

            temp = new Mat();
            Core.inRange(hsvImage, new Scalar(h_MIN, s_MIN, l_MIN), new Scalar(h_MAX, s_MAX, l_MAX), temp);
            //mask = new Mat(image.size(), 0);
            //temp.copyTo(mask);
            //Core.add(mask, temp, mask);
            i = new Mat(image.size(), 0);
            //image.copyTo(i, mask);
            //i.copyTo(image, mask);
            workingImage.release();
            workingImage = new Mat();
            image.copyTo(workingImage, mask);

            workingImage.copyTo(i, temp);

            i.copyTo(workingImage);

            temp = new Mat();
            Core.inRange(labImage, new Scalar(L_MIN, a_MIN, b_MIN), new Scalar(L_MAX, a_MAX, b_MAX), temp);
            //mask = new Mat(image.size(), 0);
            //temp.copyTo(mask);
            //Core.add(mask, temp, mask);
            i = new Mat(image.size(), 0);
            //image.copyTo(i, mask);
            //i.copyTo(image, mask);
            workingImage.copyTo(i, temp);

            i.copyTo(workingImage);


/*

            temp = new Mat();
            Core.inRange(labImage, new Scalar(L_MIN, a_MIN, b_MIN), new Scalar(L_MAX, a_MAX, b_MAX), temp);
            //mask = new Mat(image.size(), 0);
            //temp.copyTo(mask);
            //Core.add(mask, temp, mask);
            i = new Mat(image.size(), 0);
            //image.copyTo(i, mask);
            //i.copyTo(image, mask);

            workingImage.copyTo(i, temp);

            i.copyTo(workingImage, temp);

            temp = new Mat();
            Core.inRange(hlsImage, new Scalar(h_MIN, l_MIN, s_MIN), new Scalar(h_MAX, l_MAX, s_MAX), temp);
            //mask = new Mat(image.size(), 0);
            //temp.copyTo(mask);
            //Core.add(mask, temp, mask);
            i = new Mat(image.size(), 0);
            //image.copyTo(i, mask);
            //i.copyTo(image, mask);

            workingImage.copyTo(i, temp);

            i.copyTo(workingImage, temp);*/

            Imgproc.cvtColor(workingImage, gray, Imgproc.COLOR_RGB2GRAY);
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.GaussianBlur(gray, gray, new Size(9, 9), 2, 2);
            Imgproc.findContours(gray ,contours,new Mat(),Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

            Imgproc.drawContours(workingImage,contours,-1,new Scalar(230,70,70),2);
            gray.release();
            releaseMats();
        }

}
