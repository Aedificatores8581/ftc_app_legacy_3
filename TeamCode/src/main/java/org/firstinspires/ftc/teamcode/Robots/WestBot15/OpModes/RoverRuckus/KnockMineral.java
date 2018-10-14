package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.RoverRuckus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Vision.TennisBallDetector;

/**
 * Written 13/10/18 by Theodore Lovinski.
 *
 * This is a test of knocking a mineral using computer vision.
 */

@Autonomous(name = "Mineral Knocking", group = "Auto Testing")
public class KnockMineral extends WestBot15 {
    @Override
    public void init() {
        super.init();
        activateGamepad1();
    }

    @Override
    public void start() {super.start();}

    @Override
    public void loop() {
        updateGamepad1();
    }
}