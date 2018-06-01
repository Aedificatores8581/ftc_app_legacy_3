package org.firstinspires.ftc.teamcode.robotOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robotTemplates.OldBotTemplate;

/**
 * Conjured into existence by The Saminator on 2018-05-06.
 */

@TeleOp(name = "High Five!", group = "why sipp when you can succ")
public class OldBotHighFive extends OldBotTemplate {
    final double JEWEL_HAND_LENGTH = 9.6;
    final double JEWEL_HAND_THRESHOLD = 8;
    final double JEWEL_ARM_POSITION = 0.36;
    final double RADS_TO_DEGS = 180 / Math.PI;
    final double SPEED_MULTIPLIER = 0.42;

    @Override
    public void start() {
        jewelArm.setPosition(0.36);
    }

    @Override
    public void loop() {
        setLeftPow(gamepad1.left_stick_y * SPEED_MULTIPLIER);
        setRightPow(gamepad1.right_stick_y * SPEED_MULTIPLIER);

        // Jewel hand length is 9.6 cm.
        if (dSensorL.getDistance(DistanceUnit.CM) < JEWEL_HAND_THRESHOLD || dSensorR.getDistance(DistanceUnit.CM) < JEWEL_HAND_THRESHOLD) {
            double mult = 1;
            double distance;
            if (dSensorL.getDistance(DistanceUnit.CM) < dSensorR.getDistance(DistanceUnit.CM)) {
                mult *= -1;
                distance = dSensorL.getDistance(DistanceUnit.CM);
            } else distance = dSensorR.getDistance(DistanceUnit.CM);

            double angle = Math.acos(distance / JEWEL_HAND_LENGTH);
            double position = angle / Math.PI;

            jewelFlipper.setPosition(position * mult);

            telemetry.addData("Angle", angle * RADS_TO_DEGS);
            telemetry.addData("Distance", distance);
            telemetry.addData("Position", position);
        }
        telemetry.addData("L Distance", dSensorL.getDistance(DistanceUnit.CM));
        telemetry.addData("R Distance", dSensorR.getDistance(DistanceUnit.CM));

        telemetry.addData("L Speed", gamepad1.left_stick_y * SPEED_MULTIPLIER);
        telemetry.addData("R Speed", gamepad1.right_stick_y * SPEED_MULTIPLIER);
    }
}
