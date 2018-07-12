package ftc.vision;

import android.view.SurfaceView;

import org.firstinspires.ftc.robotcore.internal.opengl.models.Geometry;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
public abstract class Detector {
    public boolean isInitialized = false;
    public Detector(){
        isInitialized = true;
    }
    public enum OperatingState{
        TUNING,
        DETECTING
    }

    public abstract void detect(Mat image);

    public abstract Mat result();

}
