package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;


/**
 * Created by Frank Portman on 6/17/2018
 */
@TeleOp(name = "Location tracking test", group = "WestBot15")
public class TankLocationTrackingTest extends WestBot15 {
    double leftEncVal = 0, rightEncVal = 0, radius, angle, totalAngle = 0;

    Vector2 turnVector;

    @Override
    public void init(){
        totalAngle = 90;
        super.init();
        drivetrain.hardResetEncoders();
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop() {
        drivetrain.updateEncoders();
        leftEncVal = drivetrain.averageLeftEncoders() - leftEncVal;
        rightEncVal = drivetrain.averageRightEncoders() - rightEncVal;
        if(rightEncVal == leftEncVal)
            turnVector.setFromPolar(rightEncVal, 0);
        else {
            radius = drivetrain.ENC_PER_INCH * drivetrain.DISTANCE_BETWEEN_WHEELS / 2 * (leftEncVal + rightEncVal) / (rightEncVal - leftEncVal);
            angle = (rightEncVal - leftEncVal) / (drivetrain.DISTANCE_BETWEEN_WHEELS * drivetrain.ENC_PER_INCH);
            radius = Math.abs(radius);
            turnVector.setFromPolar(radius, angle);
            turnVector.setFromPolar(radius - turnVector.x, angle);
            if(Math.min(leftEncVal, rightEncVal) == -UniversalFunctions.maxAbs(leftEncVal, rightEncVal))
                turnVector.x *= -1;
        }

        turnVector.rotate(totalAngle);
        drivetrain.position.add(turnVector);
        totalAngle += angle;
    }
}
