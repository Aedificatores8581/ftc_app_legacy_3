package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

//@TeleOp(name = "teleop")
public class RoverRuckusTeleOp extends WestBot15 {
    boolean canSwitch = false;
    boolean markerDropped = false;
    @Override
    public void init(){
        super.init();
        activateGamepad1();
        activateGamepad2();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop(){
        updateGamepad1();
        updateGamepad2();

    }
}
