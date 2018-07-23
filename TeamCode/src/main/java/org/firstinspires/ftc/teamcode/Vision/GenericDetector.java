package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
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
        }
        public Mat result(){
            return workingImage;
        }
        public void tune(Mat image) {
            rgbR.release();
            rgbR = new Mat();
            rgbG = new Mat();
            rgbB = new Mat();

            labL = new Mat();
            labA = new Mat();
            labB = new Mat();

            hsvH = new Mat();
            hsvS = new Mat();
            hsvV = new Mat();

            yuvY = new Mat();
            yuvU = new Mat();
            yuvV = new Mat();

            labImage = new Mat();
            hsvImage = new Mat();
            hlsImage = new Mat();

            Imgproc.cvtColor(image, labImage, Imgproc.COLOR_RGB2Lab);
            List<Mat> channels = new ArrayList<Mat>();
            Core.split(labImage, channels);
            Imgproc.threshold(channels.get(0), labL, L_MIN, L_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(1), labA, a_MIN, a_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(2), labB, b_MIN, b_MAX, Imgproc.THRESH_BINARY);
            temp = new Mat();
            Core.bitwise_and(labL, labA, temp);
            Core.bitwise_and(temp, labB, i);

            channels = new ArrayList<Mat>();
            Core.split(image, channels);
            Imgproc.threshold(channels.get(0), rgbR, R_MIN, B_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(1), rgbG, G_MIN, G_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(2), rgbB, B_MIN, R_MAX, Imgproc.THRESH_BINARY);
            temp = new Mat();
            Core.bitwise_and(rgbR, rgbG, temp);
            temp2 = new Mat();
            Core.bitwise_and(temp, rgbB, temp2);
            i2 = new Mat();
            Core.bitwise_and(temp2, i, i2);

            Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
            channels = new ArrayList<Mat>();
            Core.split(image, channels);
            Imgproc.threshold(channels.get(0), hsvH, H_MIN, H_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(1), hsvS, S_MIN, S_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(2), hsvV, V_MIN, V_MAX, Imgproc.THRESH_BINARY);
            temp = new Mat();
            Core.bitwise_and(hsvH, hsvS, temp);
            temp2 = new Mat();
            Core.bitwise_and(temp, hsvV, temp2);
            Core.bitwise_and(temp2, i2, i);
            i.copyTo(workingImage);

            Imgproc.cvtColor(image, hlsImage, Imgproc.COLOR_RGB2HLS);
            channels = new ArrayList<Mat>();
            Core.split(image, channels);
            hlsH = new Mat();
            hlsL = new Mat();
            hlsS = new Mat();
            Imgproc.threshold(channels.get(0), hlsH, h_MIN, h_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(1), hlsL, l_MIN, l_MAX, Imgproc.THRESH_BINARY);
            Imgproc.threshold(channels.get(2), hlsS, s_MIN, s_MAX, Imgproc.THRESH_BINARY);
            temp = new Mat();
            Core.bitwise_and(hlsH, hlsL, temp);
            temp2 = new Mat();
            Core.bitwise_and(temp, hlsS, temp2);
            Core.bitwise_and(temp2, i, i2);
            i2.copyTo(workingImage);
            releaseMats();



        }

}
