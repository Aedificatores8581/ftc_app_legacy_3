package org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.IncrementalMotor;
import org.firstinspires.ftc.teamcode.Universal.Threads.IncrementalMotorThread;

/**
 * Created by Frank Portman on 6/13/2018
 *
 * This is a version of WestCoast15 with incremental motors.
 *
 */

public class WestCoast15Inc extends TankDT {
    public DcMotor rf, lf, lr, rr;
    public IncrementalMotor rightFore, leftFore, leftRear, rightRear;
    public IncrementalMotorThread motorThread;
    public DcMotor.ZeroPowerBehavior zeroPowerBehavior;

    public double maxPow, minPow, accelerationPerSec = 1.5/1000, decelerationPerSec = 2.5/1000;

    public WestCoast15Inc(){
        // zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT; !dead_code
        maxSpeed = 1;
        initAcceleratedMotors();
    }

    public WestCoast15Inc(DcMotor.ZeroPowerBehavior zeroPowBehavior, double speed){
        //zeroPowerBehavior = zeroPowBehavior;
        maxSpeed = speed;
        maxPow = speed;
        initAcceleratedMotors();
    }

    @Override
    public double averageRightEncoders() { return 0; }
    @Override
    public double averageEncoders() { return 0; }
    @Override
    public double averageLeftEncoders() { return 0; }

    public void setLeftPow(double pow) {
        leftFore.setPower(pow * maxSpeed);
        leftRear.setPower(pow * maxSpeed);
        leftPow = pow;
    }

    public void setRightPow(double pow) {
        rightFore.setPower(pow * maxSpeed);
        rightRear.setPower(pow * maxSpeed);
        rightPow = pow;
    }

    public void initMotors(HardwareMap map) {
        rf = map.dcMotor.get("rf");
        lf = map.dcMotor.get("lf");
        lr = map.dcMotor.get("la");
        rr = map.dcMotor.get("ra");

        rf.setDirection(REVERSE);
        rr.setDirection(REVERSE);
        lf.setDirection(FORWARD);
        lr.setDirection(FORWARD);
    }

    public void normalizeMotors() {}

    public void initAcceleratedMotors() {
        motorThread = new IncrementalMotorThread(10);

        rightFore = new IncrementalMotor(rf, accelerationPerSec, decelerationPerSec, minPow);
        motorThread.add(rightFore);

        leftFore = new IncrementalMotor(lf, accelerationPerSec, decelerationPerSec, minPow);
        motorThread.add(leftFore);

        leftRear = new IncrementalMotor(lr, accelerationPerSec, decelerationPerSec, minPow);
        motorThread.add(leftRear);

        rightRear = new IncrementalMotor(rr, accelerationPerSec, decelerationPerSec, minPow);
        motorThread.add(rightRear);

        motorThread.start();
    }

    public void terminate() { motorThread.terminate(); }
}
