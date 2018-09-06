package org.firstinspires.ftc.teamcode.Universal.Map;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;

import java.util.ArrayList;

public class Map2 {
    public Pose location;
    public ArrayList<Component> components = new ArrayList<Component>();
    public Map2(){
        location = new Pose();
    }
    public Map2(double x, double y){
        location = new Pose(x, y, 0);
    }
    public Map2(double x, double y, double angle){
        location = new Pose(x, y, angle);
    }
    public Map2(Pose pose){
        location = pose;
    }
    public void setStartingPose(Pose pose){
        location = pose;
        updatePositions(location);
    }
    public void updatePositions(Pose centerPose){
        for(int i = 0; i < components.size(); i++)
            components.get(i).position.add(centerPose);
    }
    public class Component{
        public Pose startingPosition;
        public Pose position;
        public Component(Pose position){
            this.startingPosition = position;
            this.position = position;
        }
    }
    public double x(){
        return location.x;
    }
    public double y(){
        return location.y;
    }
    public double angle(){
        return location.angle;
    }
    public void add(Component component){
        components.add(component);
    }
    public Map2 switchReferenceMap(Map2 map){
        Map2 output = new Map2(map.x() - x(), map.y() - y(), map.angle() - angle());
        for(Component c : components)
            output.add(c);
        return output;
    }
}
