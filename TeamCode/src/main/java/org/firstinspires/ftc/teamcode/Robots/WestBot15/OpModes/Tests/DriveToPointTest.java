package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Map.AttractionField;
import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

@Autonomous(name = "DriveToPointTest")
public class DriveToPointTest extends WestBot15 {
    double prevLeft = 0;
    double prevRight = 0;

    public void init(){
        usingIMU = false;
        super.init();
        drivetrain.maxSpeed = 0.4;
        drivetrain.direction = TankDT.Direction.FOR;
        drivetrain.position = new Pose(0, 0, 0);
    }

    public void start(){
        super.start();
    }

    public void loop(){
        double rightEnc = drivetrain.averageRightEncoders();
        double leftEnc = drivetrain.averageLeftEncoders();

        drivetrain.updateLocation(leftEnc - prevLeft, rightEnc - prevRight);
        prevLeft = leftEnc;
        prevRight = rightEnc;

        drivetrain.driveToPoint(36, 12, drivetrain.direction);

		AttractionField field = new AttractionField(new Pose(18, 0, 0), 6);

		Vector2 fieldVect = field.interactWithSlowdown(drivetrain.position, drivetrain.maxSpeed);
		Vector2 temp = new Vector2();

		drivetrain.destination.x -= fieldVect.x;
		drivetrain.destination.y -= fieldVect.y;
	}
}
