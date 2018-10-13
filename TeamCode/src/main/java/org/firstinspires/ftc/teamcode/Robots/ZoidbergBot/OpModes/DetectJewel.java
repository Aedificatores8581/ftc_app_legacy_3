package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.Robots.ZoidbergBot.RobitBot;
@Disabled
@Autonomous(name = "Jewel Thing")
public class DetectJewel extends RobitBot {

    enum State {
        armdown, detecting, goforward, gobackward
    }

    State state;
    double redValue;
    double blueValue;

    public void init() {
        super.init();
        state = State.detecting;

    }

    public void start() {

    }

    public void loop() {


        arm.setPosition(1.0);

        switch (state) {
            case armdown:
                break;
            case detecting:
                redValue = colorSensor.red();
                blueValue = colorSensor.blue();
                if ( redValue > 300 && redValue > blueValue) {
                    state = State.goforward;
                } else if (blueValue > 300 && blueValue > redValue){
                    state = State.gobackward;
                }
                break;

            case goforward:
                drivetrain.setLeftPow(1.0);
                drivetrain.setRightPow(1.0);
                break;

            case gobackward:
                drivetrain.setLeftPow(-1.0);
                drivetrain.setRightPow(-1.0);
                break;

        }


        telemetry.addData("Arm", arm.getPosition());
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Blue", colorSensor.blue());
    }
}
