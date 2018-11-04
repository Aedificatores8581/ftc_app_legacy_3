package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Universal.JSONAutonGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@Autonomous(name = "JSONVisionInfoSaverTester", group = "tester")
public class JSONVisionInfoSaverTester extends OpMode {
    private JSONAutonGetter jsonAutonGetter;
    private JSONArray locations;
    private int currentLocationIndex;
    private JSONObject currentLocation;



    @Override
    public void init() {
        try {
            jsonAutonGetter = new JSONAutonGetter("JSONVisionInfoSaverTester.json");
        } catch (IOException | JSONException e) {
            telemetry.addData("Couldn't open JSONVisionInfoSaverTester.json", e.getMessage());
        }

        try {
            locations = new JSONArray(jsonAutonGetter.jsonObject.getJSONArray("locations"));
        } catch (JSONException e) {
            telemetry.addData("Issue with 'locations'", e.getMessage());
        }

        try {
            currentLocationIndex = jsonAutonGetter.jsonObject.getInt("lastSavedLocation");
        } catch (JSONException e) {
            telemetry.addData("Issue with lastSavedLocaition", e.getMessage());
        }

        try {
            currentLocation = locations.getJSONObject(currentLocationIndex);
        } catch (JSONException e) {
            telemetry.addData("Issue with currentLocation", e.getMessage());
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
            }
        }

        

        try {
            telemetry.addData("Index",currentLocationIndex);
            telemetry.addData("Name",currentLocation.getString("name"));
            telemetry.addData("X",currentLocation.getString("x"));
            telemetry.addData("Y",currentLocation.getString("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
