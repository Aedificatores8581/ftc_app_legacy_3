package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import net.frogbots.ftcopmodetunercommon.opmode.TunableOpMode;

public class TunableOpmodeTester extends TunableOpMode {
    String teleTest;

    public void init() {
        teleTest = getString("tele");
    }

    public void loop() {
        try {
            telemetry.addData("teleTest", teleTest);
        } catch (NullPointerException e) {
            telemetry.addLine("Null pointer, boi");
        }
    }
}
