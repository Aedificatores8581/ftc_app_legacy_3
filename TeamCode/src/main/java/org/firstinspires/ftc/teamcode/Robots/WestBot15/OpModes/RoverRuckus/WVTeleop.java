package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.Sensors.TouchSensor;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

@TeleOp(name = "Tele op")
public class WVTeleop extends WestBot15 {
    TouchSensor topLiftSensor;
    TouchSensor bottomLiftSensor;
    DcMotor liftMotor, intakeMotor;
    CRServo frontIntakeMotor, backIntakeMotor;
    Servo dispensor, topRatchetServo, sideRatchetServo;
    boolean isDespensing = false;
    double dispensorOpenPosition = 0;
    double dispensorPartiallyOpenPosition = 0;
    double dispensorClosedPosition = 0;
    public void init(){
        usingIMU = false;
        super.init();
        topLiftSensor = new TouchSensor();
        topLiftSensor.init(hardwareMap, "tts");
        bottomLiftSensor = new TouchSensor();
        bottomLiftSensor.init(hardwareMap, "bts");
        frontIntakeMotor = hardwareMap.crservo.get("bis");
        backIntakeMotor = hardwareMap.crservo.get("fis");
        sideRatchetServo = hardwareMap.servo.get("br");
        topRatchetServo = hardwareMap.servo.get("tr");
        liftMotor = hardwareMap.dcMotor.get("lift");
        intakeMotor = hardwareMap.dcMotor.get("im");
    }

    public void start(){ }
    public void loop(){
        drivetrain.leftPow = gamepad1.right_trigger - gamepad1.left_trigger + gamepad1.left_stick_x;
        drivetrain.rightPow = gamepad1.right_trigger - gamepad1.left_trigger - gamepad1.left_stick_x;
        drivetrain.setLeftPow();
        drivetrain.setRightPow();
        frontIntakeMotor.setPower(gamepad2.left_trigger - gamepad2.right_trigger);
        backIntakeMotor.setPower(gamepad2.left_trigger - gamepad2.right_trigger);
        if(gamepad2.x)
            isDespensing = true;
        else
            isDespensing = false;
        if(gamepad2.left_trigger > gamepad2.right_trigger && !isDespensing)
            dispensor.setPosition(dispensorPartiallyOpenPosition);
        else if(!isDespensing)
            dispensor.setPosition(dispensorClosedPosition);
        else
            dispensor.setPosition(dispensorOpenPosition);
    }
}
