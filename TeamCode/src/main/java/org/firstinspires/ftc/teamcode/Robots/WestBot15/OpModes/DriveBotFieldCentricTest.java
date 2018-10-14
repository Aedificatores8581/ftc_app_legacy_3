package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.Drivetrain;
import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalConstants;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

/**
 * Created by Frank Portman on 6/17/2018
 */
@TeleOp(name = "WestBotFieldCentricTest", group = "")
public class DriveBotFieldCentricTest extends WestBot15 {
    boolean switchControlState    = false,
            canSwitchControlState = false,
            switchTurnState       = false,
            canSwitchTurnState    = false;

    @Override
    public void init(){
        super.init();
        activateGamepad1();
        drivetrain.controlState = TankDT.ControlState.TANK;
        drivetrain.turnState = TankDT.FCTurnState.FAST;
        drivetrain.direction = TankDT.Direction.FOR;
    }

    @Override
    public void start(){
        super.start();
    }

    @Override
    public void loop(){
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rad = Math.sqrt(x * x + y * y);
        double angleBetween = Math.toRadians(UniversalFunctions.normalizeAngleDegrees(Math.toDegrees(Math.atan2(y, x)), UniversalFunctions.normalizeAngleDegrees(getGyroAngle())));

        if(rad < UniversalConstants.Triggered.STICK) {
            drivetrain.setLeftPow(0.001);
            drivetrain.setRightPow(0.001);
        } else {
            switch (drivetrain.direction) {
                case FOR:
                    double sin = Math.sin(angleBetween);
                    if (Math.sin(angleBetween) < 0 && drivetrain.turn) {
                        drivetrain.direction = Drivetrain.Direction.BACK;
                        drivetrain.directionMult *= -1;
                        drivetrain.turn = false;
                    } else if (Math.sin(angleBetween) >= 0)
                        drivetrain.turn = true;
                    break;
                case BACK:
                    sin = Math.sin(angleBetween);
                    if (Math.sin(angleBetween) > 0 && drivetrain.turn) {
                        drivetrain.direction = Drivetrain.Direction.FOR;
                        drivetrain.turn = false;
                        drivetrain.directionMult *= -1;
                    } else if (Math.sin(angleBetween) <= 0)
                        drivetrain.turn = true;
                    break;
            }

            double cos = Math.cos(angleBetween);

            switch(drivetrain.turnState){
                case FAST:
                    double fsTurn = Math.abs(cos) + 1;
                    double lp = -rad * drivetrain.directionMult - drivetrain.directionMult* fsTurn * cos;
                    double rp = -rad * drivetrain.directionMult + drivetrain.directionMult * fsTurn * cos;

                    drivetrain.setLeftPow(lp);
                    drivetrain.setRightPow(rp);

                    if(switchTurnState){
                        drivetrain.turnState = TankDT.FCTurnState.SMOOTH;
                        switchTurnState = false;
                        canSwitchTurnState = false;
                    } else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                        switchTurnState = false;
                        canSwitchTurnState = true;
                    } else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                        switchTurnState = true;
                    break;

                case SMOOTH:
                    if (cos > 0) {
                        lp = drivetrain.directionMult * rad;
                        rp = drivetrain.directionMult * -Math.cos(2 * angleBetween) * rad;
                    } else {
                        lp = drivetrain.directionMult * rad;
                        rp = drivetrain.directionMult * -Math.cos(2 * angleBetween) * rad;
                    }

                    if(switchTurnState){
                        drivetrain.turnState = TankDT.FCTurnState.FAST;
                        switchTurnState = false;
                        canSwitchTurnState = false;
                    } else if(gamepad1.left_trigger < UniversalConstants.Triggered.TRIGGER){
                        switchTurnState = false;
                        canSwitchTurnState = true;
                    } else if(gamepad1.left_trigger > UniversalConstants.Triggered.TRIGGER && canSwitchControlState)
                        switchTurnState = true;
                    break;
            }

            double fsTurn = Math.abs(cos) + 1;
            double lp = -rad * drivetrain.directionMult - drivetrain.directionMult* fsTurn * cos;
            double rp = -rad * drivetrain.directionMult + drivetrain.directionMult * fsTurn * cos;

            drivetrain.setLeftPow(lp);
            drivetrain.setRightPow(rp);
        }

        telemetry.addData("leftvect1", x + " " + y);
        telemetry.addData("leftPower", drivetrain.leftPow);
        telemetry.addData("rightPower", drivetrain.rightPow);
        telemetry.addData("direction", drivetrain.direction);
        telemetry.addData("turn", drivetrain.turn);
        telemetry.addData("sin", Math.sin(angleBetween));
        telemetry.addData("angleBetween", angleBetween);
    }

    public void refreshStartAngle() {
        if (gamepad1.left_stick_button) {
            startAngle = Math.toDegrees(leftStick1.angleBetween(robotAngle));
            leftStick1.x = 0;
            leftStick1.y = 0;
        }
    }
}
