package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.google.gson.annotations.JsonAdapter;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import junit.framework.Test;

import org.firstinspires.ftc.teamcode.Universal.JSONAutonGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//Todo: Test!
@Autonomous(name = "JSONVisionInfoSaverTester", group = "tester")
public class JSONVisionInfoSaverTester extends OpMode {
    private JSONAutonGetter jsonAutonGetter;
    private int currentLocationIndex;
    ArrayList<TestLocation> testLocations;
    Gamepad prev1;

    @Override
    public void init() {

        try {
            testLocations = new ArrayList<>(jsonAutonGetter.jsonObject.getJSONArray("locations").length());

            jsonAutonGetter = new JSONAutonGetter("JSONVisionInfoSaverTester.json");

            for (int i = 0; i < jsonAutonGetter.jsonObject.getJSONArray("locations").length(); ++i) {
                telemetry.addLine(jsonAutonGetter.jsonObject.getJSONArray("locations").getJSONObject(i).toString());
                testLocations.add(new TestLocation(jsonAutonGetter.jsonObject.getJSONArray("locations").getJSONObject(i)));
            }

            currentLocationIndex = jsonAutonGetter.jsonObject.getInt("lastSavedLocation");
        } catch (IOException | JSONException | NullPointerException e) {
            telemetry.addLine(e.getMessage());
            stop();
        }
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            // mod operation causes index to wrap around in case currentLocationIndex becomes negative
            currentLocationIndex = (currentLocationIndex - 1) % testLocations.size();
        }

        if (gamepad1.dpad_down) {

            // mod operation causes index to wrap around in case currentLocationIndex becomes negative
            currentLocationIndex = (currentLocationIndex + 1) % testLocations.size();
        }

        if (gamepad1.a && !prev1.a) {
            try {
                jsonAutonGetter.saveToFile();
            } catch (IOException e) {
                telemetry.addData("Issue with File Saving", e.getMessage());
            }
        }


        telemetry.addData("Index",currentLocationIndex);
        telemetry.addData("Name", testLocations.get(currentLocationIndex).name);
        telemetry.addData("X", testLocations.get(currentLocationIndex).x);
        telemetry.addData("Y", testLocations.get(currentLocationIndex).y);

        try {
            prev1.copy(gamepad1);
        } catch (RobotCoreException e) {
            telemetry.addData("Issue with Gamepad", e.getMessage());
        }
    }

    @Override
    public void stop(){
        try {
            jsonAutonGetter.jsonObject.put("lastSavedLocation", currentLocationIndex);
            jsonAutonGetter.saveToFile();
        } catch (JSONException | IOException e) {
            telemetry.addData("Issue with saving currentLocationIndex", e.getMessage());
        }
    }
}

class TestLocation {
    String name;
    int x;
    int y;

    public TestLocation(JSONObject jsonObject) throws JSONException, NullPointerException {
        this.name = jsonObject.getString("name");
        this.x = jsonObject.getInt("x");
        this.y = jsonObject.getInt("y");
    }
}