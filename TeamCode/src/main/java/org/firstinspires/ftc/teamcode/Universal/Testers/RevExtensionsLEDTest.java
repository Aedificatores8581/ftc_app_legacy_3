package org.firstinspires.ftc.teamcode.Universal.Testers;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.openftc.revextensions2.ExpansionHubEx;
import org.openftc.revextensions2.RevExtensions2;

@Autonomous(name = "Rev Led", group = "rev")
public class RevExtensionsLEDTest extends OpMode {
    ExpansionHubEx hub;
    byte r, g, b;

    @Override
    public void init() {
        RevExtensions2.init();
        hub = hardwareMap.get(ExpansionHubEx.class, "Expansion Hub 2");
    }

    @Override
    public void loop() {
        r += 3;
        g += 2;
        b += 1;

        hub.setLedColor(r,g,b);
    }
}
