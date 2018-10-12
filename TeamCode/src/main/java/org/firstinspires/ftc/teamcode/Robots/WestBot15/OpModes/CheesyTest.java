package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

@TeleOp(name = "Cheesy Test Drive")
public class CheesyTest extends WestBot15 {
    @Override
    public void init(){
        super.init();
        activateGamepad1();
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        updateGamepad1();
        double turnMult = (1 - leftStick1.magnitude()) * (1 - gamepad1.right_trigger) + leftStick1.magnitude()* gamepad1.right_trigger;
        drivetrain.leftPow = leftStick1.y + turnMult * rightStick1.x;
        drivetrain.rightPow = leftStick1.y - turnMult * rightStick1.x;
        if(gamepad1.left_stick_button)
            drivetrain.maxSpeed = 1;
        else{
            drivetrain.maxSpeed = gamepad1.left_trigger / 5.0;
            if(gamepad1.left_bumper)
                drivetrain.maxSpeed += 0.7;
            else if(gamepad1.right_bumper)
                drivetrain.maxSpeed += 0.3;
            else
                drivetrain.maxSpeed += 0.5;
        }
    }
}
