package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Universal.JSONAutonGetter;
import org.json.JSONException;

import java.io.IOException;

/*
* A Tester Opmode for JSONAutonGetter
* TODO: MAKE BETTER CLASS NAME FOR "JSONAutonGetter"
* */
@Autonomous(name = "Autonomous with JSON", group = "testers")
public class JSONAutonomousTester extends OpMode {
    JSONAutonGetter jsonHandler;
    int x, y;


    public void init(){
        try {
            jsonHandler = new JSONAutonGetter("JSONAutonomousTester.json");
        } catch(IOException | JSONException e) {
            telemetry.addLine("Couldn't init JSON file handler: " + e.getMessage());
        }

        try {
            x = jsonHandler.jsonObject.getInt("x");
        } catch (JSONException e) {
            telemetry.addLine("Couldn't access 'x': " + e.getMessage());
        }

        try {
            y = jsonHandler.jsonObject.getInt("y");
        } catch (JSONException e) {
            telemetry.addLine("Couldn't access 'y': " + e.getMessage());
        }
    }

    public void loop(){
        telemetry.addData("X + Y = ", x+y);
    }

    public void stop() {
        try {
            jsonHandler.close();
        } catch (IOException e) {
            telemetry.addLine("Couldn't close buffered reader in jsonHandler: " + e.getMessage());
        }
    }
}

