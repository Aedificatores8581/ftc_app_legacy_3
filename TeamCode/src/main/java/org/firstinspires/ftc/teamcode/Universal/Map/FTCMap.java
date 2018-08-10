package org.firstinspires.ftc.teamcode.Universal.Map;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;

import java.util.ArrayList;

public class FTCMap {
    public Pose startingPose;
    public ArrayList<Component> components = new ArrayList<Component>();
    public FTCMap(){
        startingPose = new Pose();
    }
    public FTCMap(double x, double y){
        startingPose = new Pose(x, y, 0);
    }
    public FTCMap(double x, double y, double angle){
        startingPose = new Pose(x, y, angle);
    }
    public FTCMap(Pose pose){
        startingPose = pose;
    }
    public void setStartingPose(Pose pose){
        startingPose = pose;
        updatePositions(startingPose);
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
}
