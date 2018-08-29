package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevBulkData;
import org.openftc.revextensions2.RevExtensions2;

@Autonomous(name = "Rev Bulk Tester", group = "rev")
public class RevBulkTester extends OpMode{

    private static final byte ANALOG_IN_SIZE = 4;
    private static final byte DIGITAL_IN_SIZE = 8;

    private ExpansionHubEx hub;
    private RevBulkData bulkData;

    private AnalogInput a[];
    private DigitalChannel d[];


    @Override
    public void init() {
        RevExtensions2.init();
        hub = hardwareMap.get(ExpansionHubEx.class, "Expansion Hub 2");

        a = new AnalogInput[ANALOG_IN_SIZE];

        for (byte i = 0; i < ANALOG_IN_SIZE; ++i) {
            a[i] = hardwareMap.analogInput.get("a" + i);
        }

        d = new DigitalChannel[DIGITAL_IN_SIZE];

        for (byte i = 0; i < ANALOG_IN_SIZE; ++i) {
            d[i] = hardwareMap.digitalChannel.get("d" + i);
        }
    }

    @Override
    public void loop() {
        for (byte i = 0; i < ANALOG_IN_SIZE; ++i) {
            telemetry.addData("Analog Input " + i, a[i]);
        }
        for (byte i = 0; i < DIGITAL_IN_SIZE; ++i) {
            telemetry.addData("Digital Channel" + i, d[i]);
        }
    }
}
