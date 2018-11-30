package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import android.sax.TextElementListener;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
@TeleOp(name = "locationTestWCD", group = "WestBot15")
public class WCD15LocationTest extends WestBot15{
    double prevRight = 0, prevLeft = 0;
    String telemetryString = "";
    boolean hasDetectedBug = false;
    double radius = 0, angle = 0;
    @Override
    public void init(){
        usingIMU = false;
        super.init();
        activateGamepad1();

        drivetrain.position = new Pose(0, 0, 0);
        telemetry.addData("position", drivetrain.position);
    }

    @Override
    public void start(){
        super.start();
        drivetrain.position = new Pose(0, 0, Math.PI / 2);
        telemetry.addData("position", drivetrain.position);
    }

    @Override
    public void loop() {
        hasDetectedBug = false;
        drivetrain.leftPow = gamepad1.left_stick_y + gamepad1.right_stick_x;
        drivetrain.rightPow = gamepad1.left_stick_y - gamepad1.right_stick_x;
        drivetrain.setLeftPow();
        drivetrain.setRightPow();
        drivetrain.updateEncoders();
        double leftChange = drivetrain.averageLeftEncoders() - prevLeft;
        double rightChange = drivetrain.averageRightEncoders() - prevRight;
        prevLeft = drivetrain.averageLeftEncoders();
        prevRight = drivetrain.averageRightEncoders();
        leftChange /= drivetrain.ENC_PER_INCH;
        rightChange /= drivetrain.ENC_PER_INCH;
        angle = 0;
        radius = 0;
        drivetrain.turnVector = new Vector2();
        if (rightChange == leftChange)
            drivetrain.turnVector.setFromPolar(rightChange, drivetrain.position.angle);
        else {
            radius = drivetrain.DISTANCE_BETWEEN_WHEELS / 2 * (leftChange + rightChange) / (rightChange - leftChange);
            angle = (rightChange - leftChange) / (drivetrain.DISTANCE_BETWEEN_WHEELS);
            radius = Math.abs(radius);
            drivetrain.turnVector.setFromPolar(radius, angle);
            drivetrain.turnVector.setFromPolar(radius - drivetrain.turnVector.x, angle);
            if (Math.min(leftChange, rightChange) == -UniversalFunctions.maxAbs(leftChange, rightChange))
                drivetrain.turnVector.x *= -1;
        }
        drivetrain.turnVector.rotate(drivetrain.position.angle);
        drivetrain.position.x += drivetrain.turnVector.x;
        drivetrain.position.y += drivetrain.turnVector.y;
        drivetrain.position.angle += angle;
        telemetry.addData("position", drivetrain.position.toString());
        telemetry.addData("leftposition", drivetrain.leftFore.getCurrentPosition());
        telemetry.addData("rightposition", drivetrain.rightFore.getCurrentPosition());
     }
}