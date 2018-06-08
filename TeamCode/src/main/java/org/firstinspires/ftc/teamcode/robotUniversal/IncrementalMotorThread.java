package org.firstinspires.ftc.teamcode.robotUniversal;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.IncrementalMotor;

/**
 * Created by Frank Portman on 6/8/2018
 */
public class IncrementalMotorThread implements Runnable {

    public final int          MILLISECONDS_PER_ITERATION;
    public int                numberOfMotors             = 0;
    public IncrementalMotor[] motors                      = new IncrementalMotor[8];
    public boolean            isEnabled                 ;
    private Thread            incrementalMotorThread    ;
    public IncrementalMotorThread(int mil){
        MILLISECONDS_PER_ITERATION = mil;
    }
    public synchronized void start(){
        incrementalMotorThread = new Thread(this);
        incrementalMotorThread.start();
    }
    @Override
    public synchronized void run(){
        for (int i = 0; i < numberOfMotors; i++) {
            if(isEnabled)
                motors[i].setPower();
            else
                motors[i].stop();
        }
        try {
            Thread.sleep(MILLISECONDS_PER_ITERATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void add(IncrementalMotor motor){
        motors[numberOfMotors++] = motor;
    }
    public void terminate(){
        isEnabled = false;
    }
}
