package org.firstinspires.ftc.teamcode.robotUniversal;

import android.os.SystemClock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelemetryLogger {
    private File teleLog;
    private FileOutputStream os;


    public TelemetryLogger() throws FileNotFoundException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd  hh-mm-ss");

        teleLog = new File("/TeleLogs/" + dateFormat.format(new Date()) + ".csv");

        os = new FileOutputStream(teleLog);
    }

    /**
     * Accepts variables in a similar vain to telemetry.addData(), but instead of writing it
     * to the console, it writes it to a file
     */
    public void writeToLogInCSV(Object... data) throws IOException{
        for (int i = 0; i < data.length; ++i) {
            this.os.write((data[i].toString() + ",").getBytes());
        }

        this.os.write((byte)'\n');
    }

    public void close() throws IOException {
        os.close();
    }
}
