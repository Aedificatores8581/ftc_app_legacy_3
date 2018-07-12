package org.firstinspires.ftc.teamcode.robotUniversal;

public class Position {
    public double x, y, angle;

    public Position(){
        x = 0;
        y = 0;
        angle = 0;
    }

    public Position(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    public void add(Vector2 v){
        x += v.x;
        y += v.y;
    }
    public double radius(){
        return Math.hypot(x, y);
    }
    public double angleOfVector(){
        return Math.atan2(y, x);
    }
}
