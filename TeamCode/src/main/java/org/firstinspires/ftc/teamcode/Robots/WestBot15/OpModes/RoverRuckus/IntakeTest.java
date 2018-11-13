package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp (name = "intake", group = "")
public class IntakeTest extends OpMode {
    CRServo front, back;

    public void init(){
        front = hardwareMap.crservo.get("front");

        back = hardwareMap.crservo.get("back");
    }
    public void loop(){
        front.setPower(gamepad1.left_trigger * 0.9 * (gamepad1.left_bumper ? 0.9 : -1));
        back.setPower(gamepad1.left_trigger * 0.9 * (gamepad1.left_bumper ? 0.9 : -1));
    }
}
