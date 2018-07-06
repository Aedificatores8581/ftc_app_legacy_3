package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CVObjectDetectionTest extends OpMode{
    VideoCapture video;
    public void init(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        video = new VideoCapture(0);
    }

    public void loop(){
        
    }
}
