package org.firstinspires.ftc.teamcode.robotUniversal;

/**
 * Created by Frank Portman on 6/2/2018
 */
public class PIDController {
    public double       error,
                        setpoint,
                        processVar,
                        integral      = 0,
                        deltaTime,
                        derivative,
                        prevError,
                        currentOutput,
                        time,
                        TI            = 0,
                        TD            = 0,
                        gain,
                        currentTime;
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
    //Sets TI and TD to the given parameters
    public void setStandardForm(double integralTime, double derivativeTime){
        TI = integralTime;
        TD = derivativeTime;
    }
    //one iteration of an ideal PID loop
    public void idealLoop(){
        if(time + deltaTime >= System.currentTimeMillis()) {
            error = setpoint - processVar;
            integral += error * deltaTime;
            derivative = (error - prevError) / deltaTime;
            time = System.currentTimeMillis();
            double temp = currentOutput;
            currentOutput = KP * error + gain() - DESIRED_SSE + KI * integral + KD * derivative;
            gain = currentOutput - temp;
            prevError = error;
        }
    }
    //one iteration of a standard PID loop
    public void standardLoop(){
        if(time + deltaTime >= currentTime) {
            error = setpoint - processVar;
            if(time + TI >= currentTime)
                integral += error * deltaTime;
            if(time + TD >= currentTime)
                derivative = (error - prevError) / deltaTime;
            time = System.currentTimeMillis();
            double temp = currentOutput;
            currentOutput = KP * error + gain() - DESIRED_SSE + KP / TI * integral + KP * TD * derivative;
            gain = currentOutput - gain;
            prevError = error;
        }
        currentTime = System.currentTimeMillis();
    }
    //calculates the gain of the loop
    public double gain() {
        return transferFunction() / (1 + gain * transferFunction());
    }
    //returns the value returned by the loop's transfer function
    public double transferFunction(){
        return currentOutput / (setpoint - gain * currentOutput);
    }

}
