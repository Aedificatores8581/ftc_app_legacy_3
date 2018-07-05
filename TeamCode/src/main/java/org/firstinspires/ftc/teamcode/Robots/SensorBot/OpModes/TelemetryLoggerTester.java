package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import org.firstinspires.ftc.teamcode.Robots.SensorBot.SensorBot;
import org.firstinspires.ftc.teamcode.robotUniversal.TelemetryLogger;

import java.io.IOException;

public class TelemetryLoggerTester extends SensorBot {

    TelemetryLogger logger;
    int x, y;
    long baseTimeMillis; // Current System time when the start method finishes running


    @Override
    public void init () {
        super.init();

        x = 0;
        y = 0;

        try {
            logger = new TelemetryLogger();
            logger.writeToLogInCSV("Millis", "X", "Y");
        } catch (IOException e) {
            telemetry.addLine(e.getMessage());
        }
    }

    @Override
    public void start() {
        baseTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        telemetry.addData( "Millis", System.currentTimeMillis() - baseTimeMillis);
        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        x += 1;
        y += 2;
    }

    @Override
    public void stop() {
        super.stop();
    }
}
