package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class SwerveDT extends Drivetrain {
    DcMotor rf, lf, lr, rr;
    Servo rfSwervo, lfSwervo, lrSwervo, rrSwervo;
    public SwerveDT(double brake, double rfSwervoStart, double lfSwervoStart, double lrSwervoStart, double rrSwervoStart){
        super(brake);
    }




}
