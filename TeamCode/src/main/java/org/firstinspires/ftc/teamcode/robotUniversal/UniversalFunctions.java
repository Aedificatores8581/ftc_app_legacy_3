package org.firstinspires.ftc.teamcode.robotUniversal;

import org.opencv.core.Mat;

import java.util.Arrays;

/**
 * Created by Frank Portman on 4/1/2018.
 */
public abstract class UniversalFunctions {
    public static double clamp(double min, double test, double max) {
        if (max < min) {
            double temp = min;
            min = max;
            max = temp;
        }
        return Math.max(Math.min(max, test), min);
    }
    public static boolean withinTolerance(double test, double centerPoint, double lowerLimit, double upperLimit) {
        return test == clamp(centerPoint - lowerLimit, test, centerPoint + upperLimit);
    }
    public static double round(double d) {
        if (d < 0) {
            return Math.floor(d);
        }
        return Math.ceil(d);
    }
    public static double normalizeAngle(double angle, double newStartAngle) {
        angle -= newStartAngle;
        double a2 = Math.abs(angle) % 360;
        if (Math.abs(angle) != angle) {
            return 360 - a2;
        }
        return a2;
    }
    public static double normalizeAngle(double angle) {
        double a2 = Math.abs(angle) % 360;
        if (Math.abs(angle) != angle) {
            return 360 - a2;
        }
        return a2;
    }
    public static double normalizeAngle180(double angle, double newStartAngle) {
        double ang = normalizeAngle(angle, newStartAngle);
        if(ang > 180){
            ang = -180 + ang % 360;
        }
        return ang;
    }
    public static double normalizeAngle180(double angle) {
        double ang = normalizeAngle(angle);
        if(ang > 180){
            ang = -180 + ang % 360;
        }
        return ang;
    }


    public static double max(double... ds) {
        switch(ds.length){
            case 0: return 0.0;
            case 1: return ds[0];
            case 2: return Math.max(ds[0], ds[1]);
            default: return Math.max(ds[0],
                    max(Arrays.copyOfRange(ds, 1, ds.length)));
        }
    }

    public static double maxAbs(double... ds) {
        for (int i = 0; i < ds.length; ++i){
            ds[i] = Math.abs(ds[i]);
        }

        switch(ds.length){
            case 0: return 0.0;
            case 1: return ds[0];
            case 2: return Math.max(ds[0], ds[1]);
            default: return Math.max(ds[0],
                    max(Arrays.copyOfRange(ds, 1, ds.length)));
        }
    }

    public static double min(double... ds){
        switch(ds.length){
            case 0: return 0.0;
            case 1: return ds[0];
            case 2: return Math.min(ds[0], ds[1]);
            default: return Math.min(ds[0],
                    min(Arrays.copyOfRange(ds,1, ds.length)));
        }
    }

    public static double minAbs(double... ds){
        for (int i = 0; i < ds.length; ++i){
            ds[i] = Math.abs(ds[i]);
        }
        switch(ds.length){
            case 0: return 0.0;
            case 1: return ds[0];
            case 2: return Math.min(ds[0], ds[1]);
            default: return Math.min(ds[0],
                    min(Arrays.copyOfRange(ds,1, ds.length)));
        }
    }
    public static String[] formatArrayStr(String str, int len){
        String[] ret = new String[len];
        int i = 0, ind = str.indexOf(","), next;
        while (ind != -1) {
            next = str.indexOf(",", ind + 1);
            if (next == -1)
                next = str.length();
            ret[i] = str.substring(ind, next);
        }
        return ret;
    }
    public static int sign(double input){
        return input >= 0 ? 1 : -1;
    }

}
