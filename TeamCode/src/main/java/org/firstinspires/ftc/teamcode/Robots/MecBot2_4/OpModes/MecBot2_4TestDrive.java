package org.firstinspires.ftc.teamcode.Robots.MecBot2_4.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.MecBot2_4.MecBot2_4;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;

/**
 * Created by Frank Portman on 6/1/2018
 */
@Disabled
@TeleOp(name = "Mecanum 2.4 Test Drive", group = "Mecanum 2.4")
public class MecBot2_4TestDrive extends MecBot2_4 {
    boolean switchMode = false,
            canSwitch  = false;

    @Override
    public void init(){
        super.init();
        activateGamepad1();
    }

    @Override
    public void start(){
        super.start();
    }
    public void loop() {
        setRobotAngle();
        updateGamepad1();
        drivetrain.teleOpLoop(leftStick1, rightStick1, robotAngle);
        switch (drivetrain.turnState) {
            case ARCADE:
                if (switchMode) {
                    drivetrain.turnState = drivetrain.turnState.FIELD_CENTRIC;
                    switchMode = false;
                    canSwitch = false;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchMode = false;
                    canSwitch = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch)
                    switchMode = true;
                break;

            case FIELD_CENTRIC:
                if (switchMode) {
                    drivetrain.turnState = drivetrain.turnState.ARCADE;
                    switchMode = false;
                    canSwitch = false;
                } else if (gamepad1.right_trigger < UniversalConstants.Triggered.TRIGGER) {
                    switchMode = false;
                    canSwitch = true;
                } else if (gamepad1.right_trigger > UniversalConstants.Triggered.TRIGGER && canSwitch)
                    switchMode = true;
                break;
        }
    }
}
