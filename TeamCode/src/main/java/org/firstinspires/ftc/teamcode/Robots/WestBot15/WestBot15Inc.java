package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15Inc;
import org.firstinspires.ftc.teamcode.Robots.Robot;

/**
 * Created by Frank Portman on 6/13/2018
 */
//This is a version of WestBot15 with incremental motors
public abstract class WestBot15Inc extends Robot {
    public WestCoast15Inc drivetrain = new WestCoast15Inc();

    @Override
    public void init(){
        super.init();
        msStuckDetectInit = Integer.MAX_VALUE;
        drivetrain.maxSpeed = 0.5;
        drivetrain.initMotors(hardwareMap);
        drivetrain.initAcceleratedMotors();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void stop() {
        drivetrain.motorThread.terminate();
        super.stop();
    }
}
