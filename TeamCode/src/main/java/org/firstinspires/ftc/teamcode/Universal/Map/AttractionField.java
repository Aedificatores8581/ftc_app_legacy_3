package org.firstinspires.ftc.teamcode.Universal.Map;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

public class AttractionField {
    public Pose location;
    public double strength;
    //I plan to use this class for object avoidance and score-based pathfinding algorithms
    public AttractionField(){
        location = new Pose(0,0,0);
        strength = 1;
    }
    public AttractionField(Pose pose, double strength){
        location = new Pose(pose.x, pose.y, pose.angle);
        this.strength = Math.abs(strength);
        if(Math.signum(strength) == -1)
            location.angle = UniversalFunctions.normalizeAngleRadians(Math.PI + location.angle);
    }
    public AttractionField(Pose pose){
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
    protected Vector2 interact(Vector2 object){
        return interact(new Pose(object.x, object.y, 0));
    }

    public double getStrength(double distance){
        return strength * Math.pow(Math.E, 1 - distance) / Math.pow(Math.E, 1 - strength)/ distance;
    }
    public Vector2 interactWithSlowdown(Pose object, double maxSpeed){
        Vector2 v2 = interact(object);
        if(v2.magnitude() > 1)
            v2.setFromPolar(new Vector2(object.x, object.y).magnitude() / strength, v2.angle());
        return v2;
    }
}
