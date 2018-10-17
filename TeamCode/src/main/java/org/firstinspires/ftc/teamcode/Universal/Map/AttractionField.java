package org.firstinspires.ftc.teamcode.Universal.Map;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

public class AttractionField {
    public Pose location;
    public double strength;

    public AttractionField(){
        location = new Pose(0,0,0);
        strength = 1;
    }

    /*
     * Constuctors
     * */
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

    /** Finds the distance between an object and the attraction field and returns a vector based
    * on the strength of the attraction field on the object and the angle of the object
    * */
    public Vector2 interact(Pose obj){
        Pose object = new Pose(obj);
        object.x -= location.x;
        object.y -= location.y;
        Vector2 temp = new Vector2();
        temp.setFromPolar(getStrength(object.radius()), object.angleOfVector());
        temp.rotate(location.angle);
        return temp;
    }

    /** Finds the distance between an object and the attraction field and returns a vector based
     * on the strength of the attraction field on the object and the angle of the object
     * */
    protected Vector2 interact(Vector2 object){
        return interact(new Pose(object.x, object.y, 0));
    }

    /** Returns the strength of an attraction field on an object based on the distance between the
     * field and the object
     * */
    public double getStrength(double distance){
        return strength * Math.pow(Math.E, strength - distance)/ distance;
    }


    public Vector2 interactWithSlowdown(Pose object, double maxSpeed){
        Vector2 v2 = interact(object);
        if(v2.magnitude() > 1)
            v2.setFromPolar(new Vector2(object.x, object.y).magnitude() / strength, v2.angle());
        return v2;
    }
}
