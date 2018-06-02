package org.firstinspires.ftc.teamcode.robotUniversal;

/**
 * Created by Frank Portman on 6/2/2018
 */
public class PIDController {
    public double       error,
                        setpoint,
                        processVar,
                        integral = 0,
                        deltaTime,
                        derivative,
                        prevError,
                        currentOutput,
                        time,
                        gain;
    public final double KP;
    public final double KI;
    public final double KD;
    public final double DESIRED_SSE;
    public PIDController(double kp, double ki, double kd, double tc, double sse){
        KP = kp;
        KI = ki;
        KD = kd;
        deltaTime = tc;
        DESIRED_SSE = sse;
    }
    public double loop(double setpoint){
        error = setpoint - processVar;
        integral += error * deltaTime;
        derivative = (error - prevError) / deltaTime;
        currentOutput = KP * error + KI * integral + KD * derivative;
        time = System.currentTimeMillis();
        prevError = error;
        return currentOutput;
    }
    public void loop(){
        if(time + deltaTime >= System.currentTimeMillis()) {
            error = setpoint - processVar;
            integral += error * deltaTime;
            derivative = (error - prevError) / deltaTime;
            time = System.currentTimeMillis();
            gain = currentOutput;
            currentOutput = KP * error + gain() - DESIRED_SSE + KI * integral + KD * derivative;
            gain = currentOutput - gain;
            prevError = error;
        }
        loop(currentOutput);
    }
    public double gain(){
        return transferFunction() / (1 + gain * transferFunction());
    }
    public double transferFunction(){
        return currentOutput / (setpoint - gain * currentOutput);
    }

}
