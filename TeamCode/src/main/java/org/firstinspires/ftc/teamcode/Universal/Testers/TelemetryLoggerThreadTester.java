package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.Universal.Threads.TelemetryLoggerThread;

import java.io.IOException;


/**
 * Same concept as TelemetryLoggerTester
 *
 * To variables (x and y) increase and different rates, and TelemetryLoggerThread
 * writes that to a log file.
 * */

@Autonomous(name = "Telemetry Logger Tester: Threaded", group = "Tele Test")
public class TelemetryLoggerThreadTester extends OpMode {
    private TelemetryLoggerThread loggerThread;
    long baseTimeMillis; // Current System time when the loop starts
    private int x, y; // The MacGuffins

    @Override
    public void init () {

        x = 0;
        y = 0;

        try {
            loggerThread = new TelemetryLoggerThread("Millis", "X", "Y");
        } catch (IOException e) {
            telemetry.addLine(e.getMessage());
        }
    }

    @Override
    public void start() {
        loggerThread.setTelemetryValues(x, y);
        loggerThread.start();
        baseTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        telemetry.addData("Milliseconds", System.currentTimeMillis() - baseTimeMillis);
        telemetry.addData("X", x);
        telemetry.addData("Y", y);

        if (loggerThread.exceptionThrown()) {
            telemetry.addLine(loggerThread.getExceptionMessage());
        }

        loggerThread.setTelemetryValues(System.currentTimeMillis() - baseTimeMillis, x, y);
    }

    @Override
    public void stop() {
        super.stop();
        loggerThread.running = false;

        if (loggerThread.exceptionThrown()) {
            telemetry.addLine(loggerThread.getExceptionMessage());
        }
    }


}
