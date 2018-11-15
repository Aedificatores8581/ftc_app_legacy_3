package org.firstinspires.ftc.teamcode.Robots.WestBot15.OpModes.Misctests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robots.WestBot15.WestBot15;
import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;

@TeleOp(name = "locationTestWCD", group = "WestBot15")
public class WCD15LocationTest extends WestBot15{
    public double oldLeftEncVal = 0, oldRightEncVal = 0, maxLeftError = 0, maxRightError = 0;
    public double[] times = new double[4];

    @Override
    public void init(){
        usingIMU = false;
        super.init();
        activateGamepad1();
    }

    @Override
    public void start(){
        super.start();
        double time = System.nanoTime() / 10e9;
        times[1] = time;
        times[0] = time;
        times[3] = time;
        times[2] = time;
    }

    @Override
    public void loop(){
        updateGamepad1();
        drivetrain.leftPow = leftStick1.y + rightStick1.x;
        drivetrain.rightPow = leftStick1.y - rightStick1.x;
        if(false) {
            setIncrementalPower(drivetrain.leftFore, drivetrain.leftPow, times[0]);
            times[0] = System.nanoTime() / 10e9;
            setIncrementalPower(drivetrain.leftRear, drivetrain.leftPow, times[1]);
            times[1] = System.nanoTime() / 10e9;
            setIncrementalPower(drivetrain.rightFore, drivetrain.rightPow, times[2]);
            times[2] = System.nanoTime() / 10e9;
            setIncrementalPower(drivetrain.rightRear, drivetrain.rightPow, times[3]);
            times[3] = System.nanoTime() / 10e9;
        } else {
            drivetrain.setLeftPow(drivetrain.leftPow);
            drivetrain.setRightPow(drivetrain.rightPow);
        }

        drivetrain.updateEncoders();

        double leftVal = drivetrain.averageLeftEncoders()- oldLeftEncVal;
        double rightVal = drivetrain.averageRightEncoders() - oldRightEncVal;

        drivetrain.updateLocation(leftVal, rightVal);

        oldLeftEncVal = drivetrain.averageLeftEncoders();
        oldRightEncVal = drivetrain.averageRightEncoders();

        telemetry.addData("location x", drivetrain.position.x);
        telemetry.addData("location y", drivetrain.position.y);
        if (false) {
            telemetry.addData("location angle", UniversalFunctions.normalizeAngleDegrees(Math.toDegrees(drivetrain.position.angle)));
            maxLeftError = UniversalFunctions.maxAbs(drivetrain.lfEncoder.currentPosition - drivetrain.lrEncoder.currentPosition, maxLeftError);
            maxRightError = UniversalFunctions.maxAbs(drivetrain.rfEncoder.currentPosition - drivetrain.rrEncoder.currentPosition, maxRightError);

            telemetry.addData("max left error", maxLeftError);
            telemetry.addData("max right error", maxRightError);
            telemetry.addData("current Left Error", Math.abs(drivetrain.lfEncoder.currentPosition - drivetrain.lrEncoder.currentPosition));
            telemetry.addData("current right Error", Math.abs(drivetrain.rfEncoder.currentPosition - drivetrain.rrEncoder.currentPosition));
        }
    }

    public void setIncrementalPower(DcMotor motor, double desiredPow, double prevTime){
        double  currentPow = motor.getPower(),
                newPow = 0,
                linearIncrement = 100,
                increaseRate = 1.5/1000 * (System.nanoTime()/10e9 - prevTime) * linearIncrement,
                decreaseRate = 2.5/1000 * (System.nanoTime()/10e9 - prevTime) * linearIncrement;
        if (currentPow == desiredPow)
            newPow = currentPow;
        if(desiredPow != 0) {
            if (Math.signum(currentPow) * Math.signum(desiredPow) == -1)
                desiredPow = 0;
            if (Math.abs(desiredPow) > Math.abs(currentPow))
                newPow = currentPow + Math.signum(currentPow) * increaseRate;
            else if (Math.abs(desiredPow) < Math.abs(currentPow))
                newPow = currentPow - Math.signum(currentPow) * decreaseRate;
            if (Math.abs(newPow) < 0.003)
                newPow = 0.003 * -Math.signum(currentPow);
        }

        if(UniversalFunctions.withinTolerance(newPow, desiredPow, -decreaseRate * Math.signum(desiredPow), increaseRate * Math.signum(desiredPow)))
            newPow = desiredPow;

        motor.setPower(newPow);
    }
}

