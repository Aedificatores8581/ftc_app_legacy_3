package org.firstinspires.ftc.teamcode.robotUniversal.Threads;

import org.firstinspires.ftc.teamcode.robotUniversal.TelemetryLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TelemetryLoggerThread extends Thread {
    private TelemetryLogger logger;
    private ArrayList<Object> telemetryValues;
    public volatile boolean running;
    private long baseTimeMillis; // Current System time when the thread starts

    ExceptionMessage exceptionMessage;

    private static final long UPDATE_SPEED_MILLIS = 50;

    /**
     * "labels" refer to the axes titles and the labels for the lines on a graph.
     * This graph will be created using the .csv file which TelemetryLogger creates
     * */
    public TelemetryLoggerThread (String... labels) throws IOException{

        logger = new TelemetryLogger();
        telemetryValues = new ArrayList<>();
        exceptionMessage = new ExceptionMessage();

        logger.writeToLogInCSV("Millis", labels);
        running = true;
    }

    public void setTelemetryValues(Object... data) {

        try {
            this.telemetryValues.clear();
            exceptionMessage.message = "";
            exceptionMessage.thrown = false;
        } catch (NullPointerException e) {
            exceptionMessage.message = e.getMessage();
            exceptionMessage.thrown = true;
        }
        this.telemetryValues.addAll(Arrays.asList(data));

    }

    public boolean exceptionThrown(){
        return exceptionMessage.thrown;
    }

    public String getExceptionMessage(){
        return exceptionMessage.message;
    }

    @Override
    public void run(){
        baseTimeMillis = System.currentTimeMillis();
        while (running) {

            if (((System.currentTimeMillis() - baseTimeMillis) % UPDATE_SPEED_MILLIS) == 0) {
                // The shenanigans with the "toArray" method are required since vararg methods
                // don't take ArrayLists;
                try {
                    logger.writeToLogInCSV( System.currentTimeMillis() - baseTimeMillis,
                            telemetryValues.toArray(new Object[telemetryValues.size()]));
                    exceptionMessage.message = "";
                    exceptionMessage.thrown = false;

                } catch (IOException e){
                    exceptionMessage.message = e.getMessage();
                    exceptionMessage.thrown = true;
                }
            }
        }
        try {
            logger.close();
            exceptionMessage.message = "";
            exceptionMessage.thrown = false;
        } catch(IOException e) {
            exceptionMessage.message = e.getMessage();
            exceptionMessage.thrown = true;
        }
    }
}

/**
 * I'm probably just being dumb, but this is the best way I can think of
 * for the main op mode to access an exception occuring inside the thread
 * */
class ExceptionMessage {
    String message;
    boolean thrown;

    ExceptionMessage () {
        this.thrown = false;
    }

}