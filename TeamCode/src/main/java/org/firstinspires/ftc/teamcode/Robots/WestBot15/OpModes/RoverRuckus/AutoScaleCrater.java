package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Components.Sensors.Cameras.MotoG4;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

import static org.firstinspires.ftc.teamcode.Universal.UniversalConstants.MS_STUCK_DETECT_INIT_DEFAULT;

@Autonomous(name = "ScaleCrater", group = "Auto Testing")
public class AutoScaleCrater extends WestBot15 {
    MotoG4 motoG4;

    @Override
    public void init() {
        msStuckDetectInit = MS_STUCK_DETECT_INIT_DEFAULT;

        motoG4 = new MotoG4();
        motoG4.setLocationAndOrientation();
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

    }
}
