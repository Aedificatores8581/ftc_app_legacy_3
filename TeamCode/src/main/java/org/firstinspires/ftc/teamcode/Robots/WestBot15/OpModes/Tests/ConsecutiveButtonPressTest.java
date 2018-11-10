package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.Controller.ConsecutiveButtonPress;

public class ConsecutiveButtonPressTest extends WestBot15 {
    ConsecutiveButtonPress consecutiveButton;

    @Override
    public void init() {

    }

    @Override
    public void start() { super.start(); }

    @Override
    public void loop() {
        telemetry.addData("Pressed",
                consecutiveButton.CTLeftBumper(2, 3.2));
    }
}
