package ftc.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TennisBallDetector extends Detector {
    public int H_MIN = 39,
            S_MIN = 42,
            V_MIN = 59,
            H_MAX = 190,
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

    Mat workingImage = new Mat(), hsvImage= new Mat(), threshold= new Mat();
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
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_RGB2HSV_FULL);
        Core.inRange(hsvImage, new Scalar(H_MIN, S_MIN, V_MIN), new Scalar(H_MAX, S_MAX, V_MAX), threshold);

        Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dilationFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.dilate(threshold, threshold, dilationFactor);
        threshold.copyTo(workingImage);
    }
}
