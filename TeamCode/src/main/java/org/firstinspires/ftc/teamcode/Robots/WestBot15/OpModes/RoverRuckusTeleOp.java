package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;

//@TeleOp(name = "teleop")
public class RoverRuckusTeleOp extends WestBot15 {
    boolean canSwitch = false;
    boolean markerDropped = false;
    @Override
    public void init(){
        super.init();
        activateGamepad1();
        activateGamepad2();
    }
    @Override
    public void start(){
        super.start();
    }

    int intakeDirection = 1;

    @Override
    public void loop(){
        updateGamepad1();
        updateGamepad2();

        //driving
        if(gamepad1.left_stick_button)
            drivetrain.maxSpeed = 1;
        else{
            drivetrain.maxSpeed = gamepad1.left_trigger / 5.0;
            if(gamepad1.left_bumper)
                drivetrain.maxSpeed += 0.3;
            else
                drivetrain.maxSpeed += 0.6;
        }
        double maxTurn = 1;
        if(!gamepad1.right_bumper)
            maxTurn = 2.0/3;
        double turnMult = (1 - leftStick1.magnitude() * maxTurn) * (1 - gamepad1.right_trigger) + leftStick1.magnitude() * gamepad1.right_trigger;
        drivetrain.leftPow = leftStick1.y + turnMult * rightStick1.x;
        drivetrain.rightPow = leftStick1.y - turnMult * rightStick1.x;
        drivetrain.setRightPow();
        drivetrain.setLeftPow();

        if(gamepad2.right_trigger > 0){
            if(gamepad2.right_trigger < 0.1)
                canSwitch = true;
            if(canSwitch) {
                //intake.goldMode();
            }
            if(gamepad2.left_bumper) {
                //intake.silverMode();
                canSwitch = false;
            }
            //intake.setPower(gamepad2.right_trigger);
        }
        //intake.open(gamepad2.b);

        // insert has marker been dropped code
        if(/*markerDropper.isDropper()*/ true) {
            if (gamepad2.a) {
                //markerDropper.drop();
            }
        }
        if(gamepad2.y){
            //intake.rotate(intake.currentPositionOrdinal + 1);
        }
        if(gamepad2.x){
            //intake.rotate(intake.currentPositionOrdinal - 1);
        }

    }
}
