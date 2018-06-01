package org.firstinspires.ftc.teamcode.robotTemplates;

/**
 * Created by Frank Portman on 5/21/2018
 */
public class Mecanum2 extends MecanumDT {
    public double turnMult, angleBetween;
    public Mecanum2(){
        super(0.01);
        maxSpeed = 1;
    }
    public Mecanum2(double brakePow, double sped) {
        super(brakePow);
        maxSpeed = sped;
    }
    @Override
    public void init() {
        usingIMU = true;
        super.init();
        initMotors();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop() {
        super.loop();
    }
}
