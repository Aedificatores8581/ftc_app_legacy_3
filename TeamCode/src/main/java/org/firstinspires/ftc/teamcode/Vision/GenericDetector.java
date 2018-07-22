package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
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

        public Mat workingImage = new Mat(), hsvImage= new Mat(), threshold= new Mat(), i = new Mat(), thresh = new Mat(),
                invert = new Mat(), hsv = new Mat(), r = new Mat(), g = new Mat(), b = new Mat();
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
        public Mat result(){
            return workingImage;
        }
        public void tune(Mat image) {

            threshold = new Mat();
            hsvImage = new Mat();
            hsv = new Mat();
            thresh = new Mat();
            i = new Mat();
            invert = new Mat();
            Mat gray = new Mat();
            Mat labImage = new Mat();
            //Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGBA2BGR);
            Imgproc.cvtColor(image, labImage, Imgproc.COLOR_RGB2Lab);
            //Core.inRange(labImage, new Scalar(L_MIN, a_MIN, b_MIN), new Scalar(L_MAX, a_MAX, b_MAX), threshold);
            List<Mat> channels = new ArrayList<Mat>();
            Core.split(labImage, channels);
            Imgproc.threshold(channels.get(0), threshold, L_MIN, L_MAX, Imgproc.THRESH_BINARY);

            Imgproc.threshold(channels.get(1), thresh, a_MIN, a_MAX, Imgproc.THRESH_BINARY);

            Imgproc.threshold(channels.get(2), hsvImage, b_MIN, b_MAX, Imgproc.THRESH_BINARY);
            Mat temp = new Mat();
            Core.bitwise_and(thresh, threshold, temp);
            Core.bitwise_and(temp, hsvImage, i);
            //Imgproc.resize(threshold, i, image.size());
            i.copyTo(workingImage);
        }

}
