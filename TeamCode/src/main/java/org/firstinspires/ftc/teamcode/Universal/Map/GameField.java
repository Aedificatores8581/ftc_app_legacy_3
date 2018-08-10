package org.firstinspires.ftc.teamcode.Universal.Map;

import org.firstinspires.ftc.teamcode.Universal.Math.Pose;

import java.util.ArrayList;

public abstract class GameField extends FTCMap {
    public FTCMap knownScoringElements;
    public ArrayList<int[]> scoringElementPositions = new ArrayList<int[]>();
    public int[] numElements;
    public GameField(int[] maxScoringElements){
        super();
        knownScoringElements = new FTCMap();
        numElements = new int[maxScoringElements.length];
        for(int maxOfType : maxScoringElements){
            scoringElementPositions.add(new int[maxOfType]);
        }
    }
    public void addScoringElement(Pose pose, int type){
        scoringElementPositions.get(type)[numElements[type] - 1] = knownScoringElements.components.size();
        knownScoringElements.components.add(new Component(pose));
        numElements[type]++;
    }
    public void resetScoringElementPositions(){
        int[] maxSizes = new int[scoringElementPositions.size()];
        for(int i = 0; i < maxSizes.length; i++)
            maxSizes[i] = scoringElementPositions.get(i).length;
        scoringElementPositions = new ArrayList<int[]>();
        for(int i = 0; i < maxSizes.length; i++)
            scoringElementPositions.add(new int[maxSizes[i]]);
        knownScoringElements = new FTCMap();
    }
}
