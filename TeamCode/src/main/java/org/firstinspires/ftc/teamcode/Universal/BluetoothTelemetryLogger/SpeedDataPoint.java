package org.firstinspires.ftc.teamcode.Universal.BluetoothTelemetryLogger;

public class SpeedDataPoint extends DataPoint {

    private final String nameOfType = "speed";

    private double speed;

    public String toCSV() {
        return nameOfType + ", " + Double.toString(speed);
    }

    public String getType() {
        return nameOfType;
    }

    public void setSpeed(double param) {
        speed = param;
    }

    public double getSpeed() {
        return speed;
    }
}
