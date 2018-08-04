package org.firstinspires.ftc.teamcode.Universal;

public class Vector2Flow {
    public Pose location;
    public double strength;
    public Vector2Flow(){
        location = new Pose(0,0,0);
    }
    public Vector2Flow(Pose pose, double strength){
        location = new Pose(pose.x, pose.y, pose.angle);
        this.strength = strength;
        if(Math.signum(strength) == -1){
            strength = Math.abs(strength);
            location.angle = UniversalFunctions.normalizeAngleRadians(Math.PI + location.angle);
        }
    }
    public Vector2Flow(Pose pose){
        location = new Pose(pose.x, pose.y, pose.angle);
        strength = 1;
    }
    public Vector2 interact(Pose object){
        object.x -= location.x;
        object.y -= location.y;
        Vector2 temp = new Vector2();
        temp.setFromPolar(getStrength(object.radius()), object.angleOfVector());
        temp.rotate(location.angle);
        return temp;
    }
    public double getStrength(double distance){
        return strength / distance;
    }
}
