package ftc.vision;

import android.view.SurfaceView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

public class FrameGrabber implements CameraBridgeViewBase.CvCameraViewListener2  {
    /*public Detector detector = null;
    public enum FrameGrabberMode {
        SINGLE,
        THROWAWAY,
        STOPPED
    }
    private FrameGrabberMode mode = FrameGrabberMode.STOPPED;
    private boolean resultReady = false;

    public FrameGrabber(CameraBridgeViewBase cameraBridgeViewBase, int frameWidthRequest, int frameHeightRequest) {
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);

        cameraBridgeViewBase.setMinimumWidth(frameWidthRequest);
        cameraBridgeViewBase.setMinimumHeight(frameHeightRequest);
        cameraBridgeViewBase.setMaxFrameSize(frameWidthRequest, frameHeightRequest);
        cameraBridgeViewBase.setCvCameraViewListener(this);
    }
    public FrameGrabber(CameraBridgeViewBase c) {
        c.setVisibility(SurfaceView.
                VISIBLE
        );
        c.setCvCameraViewListener(
                this
        );
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if(detector.equals(null) || !detector.isInitialized)
            return inputFrame.rgba();
        else {
            detector.detect(inputFrame.rgba());
            return detector.result();
        }
    }

    public void grabSingleFrame(){
        mode = FrameGrabberMode.SINGLE;
        resultReady = false;
    }
    public void throwAwayFrames(){
        mode = FrameGrabberMode.THROWAWAY;
        resultReady = false;
    }
    public boolean isResultReady(){
        return resultReady;
    }
    public void stopFrameGrabber(){
        mode = FrameGrabberMode.STOPPED;
    }*/
    public Detector detector = null;
    public boolean detectorInitialized = false;
    private boolean resultReady = false;
    public FrameGrabber(CameraBridgeViewBase cameraBridgeViewBase) {

        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);
    }
    public FrameGrabber(CameraBridgeViewBase cameraBridgeViewBase, int frameWidthRequest, int frameHeightRequest) {
        cameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);

        cameraBridgeViewBase.setMinimumWidth(frameWidthRequest);
        cameraBridgeViewBase.setMinimumHeight(frameHeightRequest);
        cameraBridgeViewBase.setMaxFrameSize(frameWidthRequest, frameHeightRequest);
        cameraBridgeViewBase.setCvCameraViewListener(this);
    }
    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if(detector == (null) || !detector.isInitialized)
            return inputFrame.rgba();
        else {
            detector.detect(inputFrame.rgba());
            return detector.result();
        }
    }
}
