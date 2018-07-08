package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.robotUniversal.Threads.TelemetryLoggerThread;

import java.io.IOException;


/**
 * Same concept as TelemetryLoggerTester
 *
 * To variables (x and y) increase and different rates, and TelemetryLoggerThread
 * writes that to a log file.
 * */

@Autonomous(name = "Telemetry Logger Tester: Threaded", group = "Tele Test")
public class TelemetryLoggerThreadTester extends SensorBot {
    TelemetryLoggerThread loggerThread;
    long baseTimeMillis; // Current System time when the loop starts
    int x, y; // The MacGuffins

    @Override
    public void init () {
        super.init();

        x = 0;
        y = 0;

        try {
            loggerThread = new TelemetryLoggerThread("X", "Y");
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
        telemetry.addData("Millis", System.currentTimeMillis());
        telemetry.addData("X", x);
        telemetry.addData("Y", y);

        if (loggerThread.exceptionThrown())
            telemetry.addLine(loggerThread.getExceptionMessage());
    }

    @Override
    public void stop() {
        super.stop();
        loggerThread.running = false;

        if (loggerThread.exceptionThrown())
            telemetry.addLine(loggerThread.getExceptionMessage());
    }


}
