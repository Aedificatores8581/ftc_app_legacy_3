package org.firstinspires.ftc.teamcode.Components.Sensors;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Frank Portman on 6/8/2018
 */
//TODO: Make sure a runtime error isn't thrown when the killPower() method is called
public class AmpSensor {
    private AnalogInput amperage;
    public double limit;
    HardwareDevice mechanism;
    public Type type;

    public AmpSensor(DcMotor hardwareDevice, double lim){
        limit = lim;
        type = Type.MOTOR;
        mechanism = hardwareDevice;
    }

    public AmpSensor(Servo hardwareDevice, double lim){
        limit = lim;
        type = Type.SERVO;
        mechanism = hardwareDevice;
    }

    public AmpSensor(CRServo hardwareDevice, double lim){
        limit = lim;
        type = Type.CRSERVO;
        mechanism = hardwareDevice;
    }

    //Initializes the sensor
    public void init(HardwareMap hardwareMap, String name){
        hardwareMap.analogInput.get(name);
    }
    //Sets the power to zero if the amperage is over the limit
    public void killPower() {
        if(isOverLimit())
            switch(type){
                case MOTOR:
                    DcMotor.class.cast(mechanism).setPower(0);
                    break;
                case SERVO:
                    Servo.class.cast(mechanism).resetDeviceConfigurationForOpMode();
                case CRSERVO:
                    CRServo.class.cast(mechanism).setPower(0.5);
        }
    }
    //Returns a boolean representing whether the current amperage is over the limit
    public boolean isOverLimit(){
        return amperage.getVoltage() > limit;
    }

    //Type of hardware device being used
    public enum Type {
        MOTOR,
        SERVO,
        CRSERVO
    }

    public String toString(){
        return type + " voltage: " + amperage.getVoltage() + ", is " + (isOverLimit()? "" : "not") + "over the limit";
    }
}
