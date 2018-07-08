package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Moments;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;
import java.util.List;

public class TennisBallDetectionTest extends OpMode{
    VideoCapture video;
    //TODO: find values of the constants
    public final double WIDTH = 0, HEIGHT = 0;
    public final int MAX_NUM_OBJECTS = 50, MIN_OBJECT_AREA = 0, MAX_OBJECT_AREA = 0;
    Mat image;
    Mat hsvImage;
    Mat threshold;
    int H_MIN = 0,
    S_MIN = 0,
    V_MIN = 0,
    H_MAX = 0,
    S_MAX = 0,
    V_MAX = 0;
    double x = 0, y = 0;
    public void init(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        video = new VideoCapture(0);
        video.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, WIDTH);
        video.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, HEIGHT);
    }

    public void initLoop(){
        video.read(image);
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV_FULL);
        Core.inRange(hsvImage, new Scalar(H_MIN,S_MIN,V_MIN), new Scalar(H_MAX,S_MAX,V_MAX),threshold);
        //Maybe get rid of
        Mat erosionFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat dilationFactor = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));

        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.erode(threshold, threshold, erosionFactor);
        Imgproc.dilate(threshold, threshold, dilationFactor);
        Imgproc.dilate(threshold, threshold, dilationFactor);

        Mat temp = new Mat();
        threshold.copyTo(temp);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(temp, contours, hierarchy, 2, 2);
        double referenceArea = 0;
        boolean objectFound = false;

        if (hierarchy.size().area() > 0) {
            List<MatOfPoint> largestContourVec = new ArrayList<MatOfPoint>();
            largestContourVec.add(contours.get(contours.size() - 1));
            double numObjects = hierarchy.size().area();
            if(numObjects<MAX_NUM_OBJECTS){
                for (int index = 0; index >= 0; index = hierarchy.checkVector(index, 0)) {
                    Moments moment = Imgproc.moments((Mat)contours.get(index));
                    double area = moment.get_m00();
                    objectFound = area>MIN_OBJECT_AREA && area<MAX_OBJECT_AREA && area>referenceArea;
                    if(objectFound){
                        x = moment.m10/area;
                        y = moment.m01/area;
                        objectFound = true;
                        referenceArea = area;
                    }
                }
                //TODO: find the size of the object(s)
                if(objectFound ==true) {
                    Point p = new Point(x, y);
                    final Scalar green = new Scalar(0, 255, 0);
                    Imgproc.circle(image, p, 20, green, 2);
                    Imgproc.putText(image, x + "," + y, new Point(x, y + 30), 1, 1, new Scalar(0, 255, 0), 2);
                }
                //TODO: display the image on the RC phone
                double time = System.currentTimeMillis();
                while(time - System.currentTimeMillis() < 50)
                    time = System.currentTimeMillis();
            }
        }

    }
    public void loop(){

    }
}
