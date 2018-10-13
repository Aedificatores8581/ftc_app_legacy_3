package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

@Autonomous(name = "Drive To Point test", group = "WestBot15.2")
public class WestBot15DriveToPointTest extends WestBot15 {
    Pose desiredPose;
    double prevTime;
    @Override
    public void init(){
        usingIMU = false;
        super.init();
        activateGamepad1();
        desiredPose = new Pose();
    }
    @Override
    public void start(){
        super.start();
        prevTime = System.currentTimeMillis();
    }
    public void loop(){
        updateGamepad1();
        if(System.currentTimeMillis() - prevTime > 0.125){
            desiredPose.y += leftStick1.y;
            desiredPose.x += rightStick1.x;
            prevTime = System.currentTimeMillis();
        }
        if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER)
            drivetrain.driveToPoint(desiredPose.x, desiredPose.y, TankDT.Direction.FOR);
        else if(gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER)
            drivetrain.driveToPointCircular(desiredPose.x, desiredPose.y, TankDT.Direction.FOR);
        else{
            drivetrain.setRightPow(0);
            drivetrain.setLeftPow(0);
        }
        telemetry.addData("desired Position", desiredPose);
        telemetry.addData("driving to point: ", gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER || gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER);
        telemetry.addData("driving circular: ", gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER);
    }

}
