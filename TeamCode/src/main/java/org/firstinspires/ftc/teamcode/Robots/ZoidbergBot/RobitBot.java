package org.firstinspires.ftc.teamcode.Robots.ZoidbergBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Components.Mechanisms.Drivetrains.TankDrivetrains.TankDT;
import org.firstinspires.ftc.teamcode.Robots.Robot;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;

/**
 * Writ by Theodore Lovinski on 06/24/2018.
 */

public abstract class RobitBot extends Robot {

    public TankDT drivetrain = new TankDT() {
        public void initMotors(HardwareMap map) {
            leftMotor = map.dcMotor.get("left");
            rightMotor = map.dcMotor.get("right");

            leftMotor.setDirection(FORWARD);
            rightMotor.setDirection(REVERSE);

        }

        public void normalizeMotors() {}

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
            return 0;
        }
        @Override
        public double averageRightEncoders() {
            return 0;
        }
    };

    @Override
    public void start() {super.start();}

    @Override
    public void init() {super.init();}
}