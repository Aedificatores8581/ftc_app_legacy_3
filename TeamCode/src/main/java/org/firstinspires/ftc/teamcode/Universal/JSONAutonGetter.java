package org.firstinspires.ftc.teamcode.Universal;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
* Class for reading json files with two hopes in mind
*   1) For easy access to all constants and initial variable values for a given opmode (as they
*       would all be listed in one file)
*   2) For quicker autonomous testing (You can change any constants or initial variable values
*       much faster via adb than through having to rebuild the code everytime to change a single
*       value)
* */
public class JSONAutonGetter {
    private static final String BASE_DIR = Environment.getExternalStorageDirectory().getPath() + "/FIRST/JSON/";

    private BufferedReader br;
    private String jsonBuffer;
    public JSONObject jsonObject;

    public JSONAutonGetter(String path) throws IOException, JSONException {
        File f = new File(BASE_DIR + path);
        jsonBuffer = "";

        if(!f.exists())
            f.createNewFile();

        br = new BufferedReader(new FileReader(f));

        String line;
        while((line = br.readLine()) != null) {
            jsonBuffer = jsonBuffer.concat(line);
        }

        jsonObject = new JSONObject(jsonBuffer);
    }

    public void close() throws IOException {
        br.close();
    }
}
