package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;


/**
 * Created by Frank Portman on 6/17/2018
 */
@TeleOp(name = "Location tracking test", group = "WestBot15")
public class TankLocationTrackingTest extends WestBot15 {
    double leftEncVal = 0, rightEncVal = 0, radius, angle, totalAngle = 0;
    Vector2 currentPos = new Vector2();
    Vector2 turnVector;
    @Override
    public void init(){
        super.init();
        drivetrain.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop() {
        drivetrain.updateEncoders();
        leftEncVal -= drivetrain.averageLeftEncoders();
        rightEncVal -= drivetrain.averageRightEncoders();
        if(rightEncVal == leftEncVal)
            turnVector.setFromPolar(rightEncVal, 0);
        else {
            radius = drivetrain.ENC_PER_INCH * 9 * (leftEncVal + rightEncVal) / (rightEncVal - leftEncVal);
            angle = (leftEncVal + rightEncVal) / (2 * radius);//angle = (rightEncVal - leftEncVal)  (18 * drivetrain.ENC_PER_INCH);
            radius = Math.abs(radius);
            turnVector.setFromPolar(radius, angle);
            turnVector.setFromPolar(radius - turnVector.x, angle);
            if(Math.min(leftEncVal, rightEncVal) == -UniversalFunctions.maxAbs(leftEncVal, rightEncVal))
                turnVector.x *= -1;
        }
        turnVector.rotate(totalAngle);
        currentPos.add(turnVector);
        totalAngle += angle;
    }
}
