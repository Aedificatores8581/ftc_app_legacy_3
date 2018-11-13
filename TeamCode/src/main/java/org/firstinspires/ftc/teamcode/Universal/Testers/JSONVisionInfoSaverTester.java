package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Universal.JSONAutonGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//Todo: Test!
@Autonomous(name = "JSONVisionInfoSaverTester", group = "tester")
public class JSONVisionInfoSaverTester extends OpMode {
    private JSONAutonGetter jsonAutonGetter;
    private JSONArray locations;
    private int currentLocationIndex;
    private JSONObject currentLocation;

    Gamepad prev1;

    @Override
    public void init() {
        currentLocation = new JSONObject();

        try {
            jsonAutonGetter = new JSONAutonGetter("JSONVisionInfoSaverTester.json");
        } catch (IOException | JSONException e) {
            telemetry.addData("Couldn't open JSONVisionInfoSaverTester.json", e.getMessage());
            stop();
        }

        try {
            locations = new JSONArray(jsonAutonGetter.jsonObject.getJSONArray("locations"));
        } catch (JSONException e) {
            telemetry.addData("Issue with 'locations'", e.getMessage());
            stop();
        }

        try {
            currentLocationIndex = jsonAutonGetter.jsonObject.getInt("lastSavedLocation");
        } catch (JSONException e) {
            telemetry.addData("Issue with lastSavedLocaition", e.getMessage());
            stop();
        }

        try {
            currentLocation = locations.getJSONObject(currentLocationIndex);
        } catch (JSONException | NullPointerException e) {
            telemetry.addData("Issue with currentLocation", e.getMessage());
            stop();
        }
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            try {
                locations.put(currentLocationIndex, currentLocation);

                // mod operation causes index to wrap around in case currentLocationIndex becomes negative
                currentLocationIndex = (currentLocationIndex - 1) % locations.length();
                currentLocation = locations.getJSONObject(currentLocationIndex);
            } catch (JSONException e) {
                telemetry.addData("Issue with currentLocation", e.getMessage());
                stop();
            }
        }

        if (gamepad1.dpad_down) {
            try {
                locations.put(currentLocationIndex, currentLocation);

                // mod operation causes index to wrap around in case currentLocationIndex becomes negative
                currentLocationIndex = (currentLocationIndex + 1) % locations.length();
                currentLocation = locations.getJSONObject(currentLocationIndex);
            } catch (JSONException e) {
                telemetry.addData("Issue with currentLocation", e.getMessage());
                stop();
            }
        }

        if (gamepad1.a && !prev1.a) {
            try {
                jsonAutonGetter.saveToFile();
            } catch (IOException e) {
                telemetry.addData("Issue with File Saving", e.getMessage());
            }
        }


        try {
            telemetry.addData("Index",currentLocationIndex);
            telemetry.addData("Name",currentLocation.getString("name"));
            telemetry.addData("X",currentLocation.getString("x"));
            telemetry.addData("Y",currentLocation.getString("y"));
        } catch (JSONException e) {
            telemetry.addData("Issue with Telemetry", e.getMessage());
        }

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
