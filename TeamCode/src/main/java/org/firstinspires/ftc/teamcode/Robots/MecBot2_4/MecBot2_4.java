package org.firstinspires.ftc.teamcode.Robots.MecBot2_4;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.HolonomicDrivetrains.Mecanum2_4;
import org.firstinspires.ftc.teamcode.Robots.Robot;

public abstract class MecBot2_4 extends Robot {
    public Mecanum2_4 drivetrain = new Mecanum2_4();
    @Override
    public void init(){
        super.init();
        drivetrain.initMotors(hardwareMap);
    }
    public void init_loop(){}
    @Override
    public void start(){
        super.start();
    }
}
