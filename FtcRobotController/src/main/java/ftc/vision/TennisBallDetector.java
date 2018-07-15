package ftc.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TennisBallDetector extends Detector {
    public int H_MIN = 0,
            S_MIN = 0,
            V_MIN = 0,
            H_MAX = 255,
            S_MAX = 255,
            V_MAX = 255;
    Mat workingImage, hsvImage, threshold;
    public OperatingState opState = OperatingState.DETECTING;

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
        threshold.copyTo(workingImage);
    }
}
