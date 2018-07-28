package org.firstinspires.ftc.teamcode.Robots.SensorBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import net.frogbots.ftcopmodetunercommon.opmode.TunableOpMode;

@Autonomous(name = "Tunable OpMode Tester", group = "Test")
public class TunableOpmodeTester extends TunableOpMode {
    String teleTest;

    public void init() {
        teleTest = getString("tele");
    }

    public void loop() {
        teleTest = getString("tele");

        try {
            telemetry.addData("teleTest", teleTest);
        } catch (NullPointerException e) {
            telemetry.addLine("Null pointer, boi");
        }
    }
}
