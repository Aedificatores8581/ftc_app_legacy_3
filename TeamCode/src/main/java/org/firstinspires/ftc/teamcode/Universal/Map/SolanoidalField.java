package org.firstinspires.ftc.teamcode.Universal.Map;


import org.firstinspires.ftc.teamcode.Universal.Math.Pose;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

public class SolanoidalField extends AttractionField{
    public SolanoidalField(){
        super();
    }
    public SolanoidalField(Pose pose){
        super(pose);
    }
    public SolanoidalField(Pose pose, double strength){
        super(pose, strength);
    }
    @Override
    public Vector2 interact(Pose object){
        Vector2 temp = new Vector2(object.x, object.y);
        temp.x -= location.x;
        temp.y -= location.y;
        temp.rotate(location.angle - Math.PI / 2);
        double radius = (temp.y * temp.y - temp.x * temp.x) / (2 * temp.x);
        double theta = Math.signum(Math.cos(temp.angle())) * Math.PI / 2;
        AttractionField attractionField = new AttractionField(new Pose(-radius, 0, theta), strength);
        temp = attractionField.interact(temp);
        temp.rotate(Math.PI / 2 - location.angle);
        return temp;
    }
}
