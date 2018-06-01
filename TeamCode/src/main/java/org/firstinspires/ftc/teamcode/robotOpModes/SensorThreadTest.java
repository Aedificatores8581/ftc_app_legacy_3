package org.firstinspires.ftc.teamcode.robotOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.robotUniversal.SensorThread;

/*
 * Tests Sensor Thread using a color sensor (pretty self explanatory)
 *
 * Author: Mister-Minister-Master
 *
 * Created: 2018 May 12
 */
@Autonomous(name = "Sensor Thread Test", group = "Universal Testing")
public class SensorThreadTest extends OpMode{
    NormalizedColorSensor cs;
    SensorThread<NormalizedRGBA> csThread = new SensorThread<>(() -> cs.getNormalizedColors());

    public void init(){
        cs = hardwareMap.get(NormalizedColorSensor.class, "cs");
    }

    public void loop(){
        telemetry.addLine(colorf(cs.getNormalizedColors()));
    }

    // Formatted Color to String Creator Extraordinaire
    private String colorf(NormalizedRGBA rgba){
        return  "\n\tNormalized Colors\n" +
                "\t--------------------------------\n" +
                "\t|Red:\t" + rgba.red + "\n" +
                "\t|Green:\t" + rgba.green + "\n" +
                "\t|Blue:\t" + rgba.blue + "\n" +
                "\t|Alpha:\t" + rgba.alpha + "\n" +
                "\t--------------------------------\n";

    }
}
