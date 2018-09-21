package org.firstinspires.ftc.teamcode.Components.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class CRSaervo {
    public CRServo crServo;
    public final double ZERO_POWER_POSITION;
    public CRSaervo(CRServo crServo, double zeroPowerPosition){
        this.crServo = crServo;
        ZERO_POWER_POSITION = zeroPowerPosition;
    }
    //pow is between 1 and -1
    public void setPower(double pow){
        if(pow > 0)
            crServo.setPower(ZERO_POWER_POSITION + (1 - ZERO_POWER_POSITION) * pow);
        else if (pow < 0)
            crServo.setPower(ZERO_POWER_POSITION * (1 + pow));
        else
            crServo.setPower(ZERO_POWER_POSITION);

    }

}
