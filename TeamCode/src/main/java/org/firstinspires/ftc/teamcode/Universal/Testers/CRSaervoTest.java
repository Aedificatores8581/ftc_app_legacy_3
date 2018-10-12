package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.CRSaervo;

@TeleOp(name = "CR Servo test", group = "test")
public class CRSaervoTest extends OpMode {
    CRSaervo saervo;
    CRServo servo;
    double zeroPosition = 0.5;
    boolean canSwitch = true;
    double prevTime;
    public void init(){
        servo = hardwareMap.crservo.get("s1");
        saervo = new CRSaervo(servo, 0);
        prevTime = System.currentTimeMillis();
    }

    @Override
    public void loop(){
        if(System.currentTimeMillis() > prevTime + 125)
            canSwitch = true;
        else {
            prevTime = System.currentTimeMillis();
            zeroPosition -= 1 / 150 * Math.signum(gamepad1.left_stick_y);
        }
    }
}
