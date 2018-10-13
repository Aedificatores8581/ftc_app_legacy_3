package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Frank Portman on 5/21/2018
 */
public abstract class Drivetrain {
    public static final DcMotor.Direction FORWARD  = DcMotor.Direction.FORWARD,
                                          REVERSE  = DcMotor.Direction.REVERSE;
    public double minTurn;
    public double maxSpeed = 1;

    public enum Direction {FOR, BACK}

    //initializes the motors
    public abstract void initMotors(HardwareMap map);

    //normalizes the motor values
    public abstract void normalizeMotors();

    public void resetEncoders() {}
}