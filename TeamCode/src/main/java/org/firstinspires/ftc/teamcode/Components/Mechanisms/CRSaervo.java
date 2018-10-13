package org.firstinspires.ftc.teamcode.Components.Mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;

/*
This class normalizes CRServo inputs to range from a 1 to -1. At 1 and -1, the CRServo is at its
maximum velocity in either respective direction. When at 0, the speed of the CRServo is 0;
 */
public class CRSaervo {
    public CRServo crServo;
    public final double ZERO_POWER_POSITION;
    public double threshold;
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
    public double getPower(){
        double pow = crServo.getPower();
        if (pow > ZERO_POWER_POSITION)
            return (pow - ZERO_POWER_POSITION) / (1 - ZERO_POWER_POSITION);
        else if (pow < ZERO_POWER_POSITION)
            return -(ZERO_POWER_POSITION - pow) / ZERO_POWER_POSITION;
        return 0;
    }
    public String toString(){
        return getPower() + "";
    }

}
