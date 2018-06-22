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
    double leftEncVal = 0, rightEncVal = 0, inner, outer, radius, angle, totalAngle;
    Vector2 currentPos = new Vector2();
    Vector2 angleChange = new Vector2();
    AngleDirection angleDirection;
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
    public void loop(){
        leftEncVal = drivetrain.averageLeftEncoders() - leftEncVal;
        rightEncVal = drivetrain.averageRightEncoders() - rightEncVal;
        if(leftEncVal == rightEncVal)
            angleChange.setFromPolar(leftEncVal, totalAngle);
        else {
            angleDirection = rightEncVal > leftEncVal ? AngleDirection.RIGHT : AngleDirection.LEFT;
            inner = rightEncVal > leftEncVal ? leftEncVal : rightEncVal;
            outer = rightEncVal < leftEncVal ? leftEncVal : rightEncVal;
            radius = (outer + inner) * 9 * drivetrain.ENC_PER_INCH / (outer - inner);
            angle = (outer + inner) / (2 * radius);
            if(angleDirection.equals(AngleDirection.LEFT))
                angle = Math.PI - angle;
            totalAngle = UniversalFunctions.normalizeAngleRadians(totalAngle + angle);
            angleChange.setFromPolar(radius, totalAngle);
        }
        currentPos.add(angleChange);
    }
    public enum AngleDirection{
        RIGHT,
        LEFT
    }
}
