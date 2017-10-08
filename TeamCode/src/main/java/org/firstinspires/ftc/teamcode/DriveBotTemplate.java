package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by The Saminator on 06-29-2017.
 */
public abstract class DriveBotTemplate extends OpMode {

    MediaPlayer wilhelmScream;

    public static class Constants {
        public static final DcMotor.Direction LEFT_FORE_DIR = DcMotor.Direction.FORWARD;
        public static final DcMotor.Direction LEFT_REAR_DIR = DcMotor.Direction.FORWARD;
        public static final DcMotor.Direction RIGHT_FORE_DIR = DcMotor.Direction.REVERSE;
        public static final DcMotor.Direction RIGHT_REAR_DIR = DcMotor.Direction.REVERSE;
        
        public static final double LEFT_FORE_SPEED = 1.0;
        public static final double LEFT_REAR_SPEED = 1.0;
        public static final double RIGHT_FORE_SPEED = 1.0;
        public static final double RIGHT_REAR_SPEED = 1.0;
    }

    DcMotor leftFore, leftRear, rightFore, rightRear;

    @Override
    public void init() {
        leftFore = hardwareMap.dcMotor.get("lfm");
        leftRear = hardwareMap.dcMotor.get("lrm");
        rightFore = hardwareMap.dcMotor.get("rfm");
        rightRear = hardwareMap.dcMotor.get("rrm");

        leftFore.setDirection(Constants.LEFT_FORE_DIR);
        leftRear.setDirection(Constants.LEFT_REAR_DIR);
        rightFore.setDirection(Constants.RIGHT_FORE_DIR);
        rightRear.setDirection(Constants.RIGHT_REAR_DIR);

        wilhelmScream = MediaPlayer.create(hardwareMap.appContext, R.raw.scream);
    }

    @Override
    public void stop() {
        setLeftPow(0.0);
        setRightPow(0.0);

        wilhelmScream.release();
        wilhelmScream = null;
    }

    protected void scream() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                wilhelmScream.start();
            }
        });
    }

    protected void setLeftPow(double pow) {
        leftFore.setPower(pow * Constants.LEFT_FORE_SPEED);
        leftRear.setPower(pow * Constants.LEFT_REAR_SPEED);
    }

    protected void setRightPow(double pow) {
        rightFore.setPower(pow * Constants.RIGHT_FORE_SPEED);
        rightRear.setPower(pow * Constants.RIGHT_REAR_SPEED);
    }

    protected boolean checkEncoder(int ticks) {
        int distance = Math.abs(ticks);
        int leftForeDist = Math.abs(leftFore.getCurrentPosition());
        int leftRearDist = Math.abs(leftRear.getCurrentPosition());
        int rightForeDist = Math.abs(rightFore.getCurrentPosition());
        int rightRearDist = Math.abs(rightRear.getCurrentPosition());

        return (distance <= leftForeDist)
            || (distance <= leftRearDist)
            || (distance <= rightForeDist)
            || (distance <= rightRearDist);
    }
}
