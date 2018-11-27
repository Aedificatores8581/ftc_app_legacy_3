package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Components.Sensors.REVColorDistanceSensor;
import org.firstinspires.ftc.teamcode.Robots.Robot;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

/**
 * Writ by Theodore Lovinski on 06/24/2018.
 */

public abstract class RobitBot extends Robot {

    public ColorSensor colorSensor;
    public Servo arm, leftGrabber, rightGrabber;
    public DcMotor lift;

    public TankDT drivetrain = new TankDT() {
        public void initMotors(HardwareMap map) {
            leftMotor = map.dcMotor.get("lm");
            rightMotor = map.dcMotor.get("rm");

            leftMotor.setDirection(FORWARD);
            rightMotor.setDirection(REVERSE);

        }

        @Override
        public void normalizeMotors() {

        }

        @Override
        public void resetEncoders() {
            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public DcMotor leftMotor, rightMotor;

        public void teleOpLoop(Vector2 leftVect, Vector2 rightVect, Vector2 angle) {
            switch (controlState) {
                case ARCADE:
                    turnMult = 1 - leftVect.magnitude() * (1 - maxTurn);
                    leftPow = leftVect.y + turnMult * rightVect.x;
                    rightPow = leftVect.y - turnMult * rightVect.x;
                    break;

                case TANK:
                    leftPow = leftVect.y;
                    rightPow = rightVect.y;
                    break;
            }
            setLeftPow(-leftPow);
            setRightPow(-rightPow);
        }

        //TODO_OVERRIDE!
        public Direction setDirection(){
            if(leftPow + rightPow > 0)
                direction = Direction.FOR;
            else if(leftPow != rightPow)
                direction = Direction.BACK;
            return direction;
        }

        public void setLeftPow(double pow) {
            leftPow = pow;
            leftMotor.setPower(leftPow);
        }

        public void setRightPow(double pow) {
            rightPow = pow;
            rightMotor.setPower(rightPow);
        }

        @Override
        public double averageLeftEncoders() {
            return leftMotor.getCurrentPosition();
        }

        @Override
        public double averageRightEncoders() {
            return rightMotor.getCurrentPosition();
        }

    };

    @Override
    public void start() {super.start();}

    @Override
    public void init() {
        super.init();
        activateGamepad1();

        drivetrain.initMotors(hardwareMap);

        colorSensor = hardwareMap.colorSensor.get("cs");
        arm = hardwareMap.get(Servo.class, "arm");
        leftGrabber = hardwareMap.get(Servo.class, "lg");
        rightGrabber = hardwareMap.get(Servo.class, "rg");

        lift = hardwareMap.dcMotor.get("lift");
    }
}