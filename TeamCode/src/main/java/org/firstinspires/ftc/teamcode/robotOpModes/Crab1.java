package org.firstinspires.ftc.teamcode.robotOpModes;

import org.firstinspires.ftc.teamcode.robotTemplates.CrabDT;

public class Crab1 extends CrabDT {
    public Crab1(){
        super(0.0 , 0.01, 1);
    }
    public Crab1(double encRat, double brake, double speed){
        super(encRat, brake, speed);
    }
    @Override
    public void init(){
        usingIMU = true;
        super.init();
    }
    @Override
    public void start(){
        super.start();
    }
    @Override
    public void loop(){
        super.loop();
    }
}
