package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;

public abstract class CrabDT extends Drivetrain {
    DcMotor rf, lf, lr, rr, cm;
    Servo clutch;
    double speed, dir = 1;
    final double encRatio;
    TurnMode tm;
    TurnDir td;
    public CrabDT(double encRat, double brake, double sped){
        super(brake);
        encRatio = encRat;
        speed = sped;
        clutch = hardwareMap.servo.get("bs");
    }
    @Override
    public DcMotor[] motors(){
        DcMotor[] motors = {rf, lf, lr, rr, cm};
        return motors;
    }
    @Override
    public String[] names(){
        String[] names = {"rf", "lf", "lr", "rr", "cm"};
        return names;
    }
    @Override
    public DcMotor.Direction[] dir(){
        DcMotor.Direction[] dir = {DcMotor.Direction.FORWARD, DcMotor.Direction.REVERSE, DcMotor.Direction.REVERSE, DcMotor.Direction.FORWARD ,DcMotor.Direction.FORWARD};
        return dir;
    }
    //sets motor power within the specified parameters
    protected void refreshMotors(double I, double II, double III, double IV){
        lf.setPower(speed * II);
        lr.setPower(speed * III);
        rf.setPower(speed * I);
        rr.setPower(speed * IV);
    }
    @Override
    //Gives the motors holding power
    public void brake(){
        if(rf.getPower() == 0 && lf.getPower() == 0 && lr.getPower() == 0 && rr.getPower() == 0){
            rf.setPower(-brakePow);
            rr.setPower(brakePow);
            lr.setPower(-brakePow);
            lf.setPower(brakePow);
        }
    }
    //specifies the turn multiplier
    public enum TurnMode{
        ARCADE,
        FIELD_CENTRIC;
    }
    //returns the number of encoder ticks coresponding to specified angle
    public double getEncoderRotation(double angle){
        return angle / 360 * encRatio;
    }
    //returns the angle of rotation coresponding to a specified number of encoder ticks
    public double getMotorAngle(double enc){
        return UniversalFunctions.normalizeAngle(enc * 360 / encRatio);
    }
    //normalizes getMotorAngle from -180 to 180 degrees
    public double getMotorAngle180(double enc){
        return UniversalFunctions.normalizeAngle180(enc * 360 / encRatio);
    }
    //returns the number of encoder ticks coresponding to specified change in angle
    public double getEncoderRotation(double desiredAngle, double currentAngle) {
        return UniversalFunctions.normalizeAngle180(desiredAngle, currentAngle) / 360 * encRatio;
    }

    public double setWheelVelocity(double ang, double sped){
        switch(td){
            case FORWARD:
                if(Math.sin(Math.toRadians(getEncoderRotation(ang, normalizeGyroAngle()))) < 0){
                    dir *= -1;
                    ang += 180;
                    td = TurnDir.BACKWARD;
                }
                break;
            case BACKWARD:
                if(Math.sin(Math.toRadians(getEncoderRotation(ang, normalizeGyroAngle()))) < 0){
                    dir *= -1;
                    ang += 180;
                    td = TurnDir.FORWARD;
                }
                break;
        }
        return ang;
    }
    //switches the control system from swerve to non-holonomic
    public enum DriveMode{
        SWERVE,
        TANK;
    }
    //switches from the act of switching DriveModes
    public enum TankDriveMode{
        SHIFT,
        TANK;
    }
}