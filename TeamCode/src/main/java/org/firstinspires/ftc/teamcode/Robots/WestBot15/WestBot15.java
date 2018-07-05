package org.firstinspires.ftc.teamcode.Robots.WestBot15;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.WestCoast15;
import org.firstinspires.ftc.teamcode.Robots.Robot;

 /**
 * Created by Frank Portman on 6/1/2018
 */
public abstract class WestBot15 extends Robot {
    public WestCoast15 drivetrain = new WestCoast15();

    @Override
    public void init(){

        super.init();
        drivetrain.maxSpeed = 0.5;
        drivetrain.initMotors(hardwareMap);
        msStuckDetectInit = 50000;
    }
    @Override
    public void start(){
        super.start();
    }
}
