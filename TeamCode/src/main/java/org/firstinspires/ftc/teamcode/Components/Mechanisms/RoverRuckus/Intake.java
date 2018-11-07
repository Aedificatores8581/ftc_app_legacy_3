package org.firstinspires.ftc.teamcode.Components.Mechanisms.RoverRuckus;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public CRServo frontRoller, backRoller;
    public Servo dispensor;
    public final double CLOSED_DISPENSOR_POSITION = 0, OPEN_DISPENSOR_POSITION = 1;
    public IntakeMode intakeMode;

    public Intake(){
        intakeMode = IntakeMode.GOLD;
    }
    public Intake(IntakeMode mode){
        intakeMode = mode;
    }
    public void init(HardwareMap hardwareMap){
        frontRoller = hardwareMap.crservo.get("fRol");
        backRoller = hardwareMap.crservo.get("bRol");
        dispensor = hardwareMap.servo.get("disp");
    }
    public double getPower(){
        return (frontRoller.getPower() + backRoller.getPower()) / 2;
    }
    //between 0 and 1
    public void setModePower(double pow){
        if(intakeMode == IntakeMode.GOLD)
            pow *= -1;
        setPowerDirectly(pow);
    }
    public void setPowerDirectly(double pow){
        frontRoller.setPower(pow);
        backRoller.setPower(pow);
        if(pow > 0)
            intakeMode = IntakeMode.SILVER;
        else if (pow < 0)
            intakeMode = IntakeMode.GOLD;
    }
    public void dispense(boolean dispensing){
        dispensor.setPosition(dispensing ? OPEN_DISPENSOR_POSITION : CLOSED_DISPENSOR_POSITION);
    }
    enum IntakeMode{
        GOLD,
        SILVER
    }
    public String toString(){
        String dispensorState = "closed";
        if(dispensor.getPosition() == OPEN_DISPENSOR_POSITION)
            dispensorState = "open";
        return frontRoller.getPower() + " " + intakeMode + ", " + "dispensor " + dispensorState;
    }
}
