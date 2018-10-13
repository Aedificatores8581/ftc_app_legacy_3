package org.firstinspires.ftc.teamcode.Universal.Math;



import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 * Conjured into existence by The Saminator on 11-24-2017.
 */
//
public class GyroAngles {
    public static final AxesOrder ORDER = AxesOrder.ZYX;
    public static final AngleUnit UNIT  = AngleUnit.DEGREES;

    private double z, y, x;

    public GyroAngles(Orientation angles) {
        z = AngleUnit.DEGREES.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.DEGREES.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.DEGREES.fromUnit(UNIT, angles.thirdAngle);
    }
    //Returns the z value recorded during the last update of the sensor
    public double getZ() {
        return z;
    }

    //Returns the y value recorded during the last update of the sensor
    public double getY() {
        return y;
    }

    //Returns the x value recorded during the last update of the sensor
    public double getX() {
        return x;
    }

    //Returns the z value recorded during the last update of the sensor in degrees
    public double refreshGyroAnglesZ(Orientation angles){
        z = AngleUnit.DEGREES.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.DEGREES.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.DEGREES.fromUnit(UNIT, angles.thirdAngle);
        return z;
    }
    //Returns the z value recorded during the last update of the sensor in radians
    public double refreshGyroAnglesRadiansZ(Orientation angles){
        z = AngleUnit.RADIANS.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.RADIANS.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.RADIANS.fromUnit(UNIT, angles.thirdAngle);
        return z;
    }
    //Returns the x value recorded during the last update of the sensor in degrees
    public double refreshGyroAnglesX(Orientation angles){
        z = AngleUnit.DEGREES.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.DEGREES.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.DEGREES.fromUnit(UNIT, angles.thirdAngle);
        return x;
    }
    //Returns the x value recorded during the last update of the sensor in radians
    public double refreshGyroAnglesRadiansX(Orientation angles){
        z = AngleUnit.RADIANS.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.RADIANS.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.RADIANS.fromUnit(UNIT, angles.thirdAngle);
        return x;
    }
    //Returns the y value recorded during the last update of the sensor in degrees
    public double refreshGyroAnglesY(Orientation angles){
        z = AngleUnit.DEGREES.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.DEGREES.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.DEGREES.fromUnit(UNIT, angles.thirdAngle);
        return y;
    }
    //Returns the y value recorded during the last update of the sensor in radians
    public double refreshGyroAnglesRadiansY(Orientation angles){
        z = AngleUnit.RADIANS.fromUnit(UNIT, angles.firstAngle);
        y = AngleUnit.RADIANS.fromUnit(UNIT, angles.secondAngle);
        x = AngleUnit.RADIANS.fromUnit(UNIT, angles.thirdAngle);
        return y;
    }
    //Formats the angle for telemetry in degrees
    public static String formatAngleDegrees(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }
    //Formats the angle for telemetry in degrees
    public static String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
    //Formats the angle for telemetry in degrees
    public static String formatAngleRadians(AngleUnit angleUnit, double angle) {
        return formatRadians(AngleUnit.RADIANS.fromUnit(angleUnit, angle));
    }
    //Formats the angle for telemetry in degrees
    public static String formatRadians(double radians) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.RADIANS.normalize(radians));
    }
}
