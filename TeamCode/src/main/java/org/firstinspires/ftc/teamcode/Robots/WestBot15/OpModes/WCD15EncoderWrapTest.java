package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

@TeleOp(name = "encoder wrap test", group = "WestBot15")
public class WCD15EncoderWrapTest extends WestBot15{
    double maxEncVal = 0, minEncVal = 0;
    @Override
    public void init(){
        usingIMU = false;
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
        drivetrain.leftPow = leftStick1.y + rightStick1.x;
        drivetrain.rightPow = leftStick1.y - rightStick1.x;
        drivetrain.setLeftPow();
        drivetrain.setRightPow();
        drivetrain.updateEncoders();
        minEncVal = UniversalFunctions.min(drivetrain.lfEncoder.currentPosition, drivetrain.rfEncoder.currentPosition, drivetrain.lrEncoder.currentPosition, drivetrain.rrEncoder.currentPosition, minEncVal);
        maxEncVal = UniversalFunctions.max(drivetrain.lfEncoder.currentPosition, drivetrain.rfEncoder.currentPosition, drivetrain.lrEncoder.currentPosition, drivetrain.rrEncoder.currentPosition, maxEncVal);
        telemetry.addData("Max enc val", maxEncVal);
        telemetry.addData("Min enc val", minEncVal);
    }
}

