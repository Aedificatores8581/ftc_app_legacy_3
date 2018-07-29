package org.firstinspires.ftc.teamcode.Robots.MecBot2_4.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robots.MecBot2_4.MecBot2_4;
import org.firstinspires.ftc.teamcode.Universal.Vector2;
import org.firstinspires.ftc.teamcode.Components.Sensors.MotorEncoder;

/**
 * Created  by Frank Portman on 6/24/2018
 */
@TeleOp(name = "MecanumLocationTrackingTest", group = "Mecanum 2.4")
public class MecanumLocationTrackingTest extends MecBot2_4 {
    double prevTheta;
    Vector2 currentPos = new Vector2();
    Vector2 turnVector = new Vector2();
    Vector2 velocity = new Vector2();
    MotorEncoder xEncL, xEncR, yEnc;
    @Override
    public void init(){
        super.init();
        drivetrain.leftFore.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.rightFore.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drivetrain.leftFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.rightFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drivetrain.rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        xEncL.resetEncoder();
        yEnc.resetEncoder();
        xEncR.resetEncoder();
        xEncR.updateEncoder();
        xEncL.updateEncoder();
        yEnc.updateEncoder();
        double angle = (xEncR.currentPosition - xEncL.currentPosition);
    }
}
