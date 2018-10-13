package org.firstinspires.ftc.teamcode.Universal.BluetoothTelemetryLogger;

public abstract class DataPoint {

    //Convert DataPoint to CSV. \n at end of string not needed.
    public abstract String toCSV();

    //Gets the name of the type as a String
    public abstract String getType();

}
