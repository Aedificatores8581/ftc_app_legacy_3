package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Tests;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Ratchet;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

public class RatchetTest extends WestBot15{
    private Ratchet ratchet;
    private Gamepad gamepad1;

    @Override
    public void init() {
        ratchet.setRatchetState(Ratchet.RatchetState.DISENGAGED);
        activateGamepad1();
    }

    @Override
    public void start() { super.start(); }

    @Override
    public void loop() {
        ratchet.setPower(gamepad1.left_stick_y);

        if (gamepad1.dpad_up) {
            ratchet.setRatchetState(Ratchet.RatchetState.UP);
        } else if (gamepad1.dpad_down) {
            ratchet.setRatchetState(Ratchet.RatchetState.DOWN);
        } else if (gamepad1.dpad_left) {
            ratchet.setRatchetState(Ratchet.RatchetState.DISENGAGED);
        } else if (gamepad1.dpad_right) {
            ratchet.setRatchetState(Ratchet.RatchetState.STOPPED);
        }

        telemetry.addData("Ratchet State", ratchet.toString());
    }
}
