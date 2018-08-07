package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Universal.Math.GyroAngles;

import org.firstinspires.ftc.teamcode.Universal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.Universal.Math.Vector2;

/**
 * Created by Frank Portman on 5/21/2018
 */

public abstract class Robot extends OpMode {
    GyroAngles     gyroangles ;
    Orientation    angles     ;
    public BNO055IMU      imu        ;
    public double  startAngle ;
    //Use this variable to set the angle of the robot which coresponds to zero degrees
    public double  zeroDegreeAngle = 0;
    public boolean usingIMU    = true;
    public Vector2 leftStick1 ,
                   rightStick1,
                   leftStick2 ,
                   rightStick2;
    public Vector2 robotAngle ;
    Robot.Module   module      = Module.REV;
    public Robot(Module mod, boolean isUsingIMU){
        usingIMU = isUsingIMU;
        module = mod;
    }
    public Robot(){
        module = Module.REV;
        usingIMU = true;
    }
    @Override
    //initializes the gyro sensor
    public void init() {
        if(isUsingIMU()) {
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.mode = BNO055IMU.SensorMode.IMU;
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imu = hardwareMap.get(BNO055IMU.class, "imu");
            imu.initialize(parameters);
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, GyroAngles.ORDER, GyroAngles.UNIT);
            gyroangles = new GyroAngles(angles);
            robotAngle = new Vector2();
        }
    }
    @Override
    //sets the start angle of the robot
    public void start(){
        if(isUsingIMU()) {
            startAngle = getGyroAngle() - zeroDegreeAngle;
        }
    }
    //returns the Z value of the gyro sensor`
    public double getGyroAngle(){
        return gyroangles.refreshGyroAnglesZ(imu.getAngularOrientation(AxesReference.INTRINSIC, GyroAngles.ORDER, GyroAngles.UNIT));
    }
    //Sets the start angle of the robot
    public void setStartAngle(){
        startAngle = getGyroAngle();
    }
    //Normalizes the gyro
    public double normalizeGyroAngle(){
        return UniversalFunctions.normalizeAngleDegrees(getGyroAngle(), startAngle);
    }
    //Sets the angle value of robotAngle
    public void setRobotAngle(){
        robotAngle.setFromPolar(1, Math.toRadians(normalizeGyroAngle()));
    }
    //instantiates the vectors representing the first gamepad's sticks
    public void activateGamepad1(){
        leftStick1 = new Vector2();
        rightStick1 = new Vector2();
    }
    //instantiates the vectors representing the second gamepad's sticks
    public void activateGamepad2(){
        leftStick2 = new Vector2();
        rightStick2 = new Vector2();
    }
    //Updates the vectors representing the left stick of the first gamepad
    public void updateLeftStick1(){
        leftStick1.x = gamepad1.left_stick_x;
        leftStick1.y = -gamepad1.left_stick_y;
    }
    //Updates the vectors representing the right stick of the first gamepad
    public void updateRightStick1() {
        rightStick1.x = gamepad1.right_stick_x;
        rightStick1.y = -gamepad1.right_stick_y;
    }
    //Updates the vectors representing the left stick of the second gamepad
    public void updateLeftStick2(){
        leftStick2.x = gamepad2.left_stick_x;
        leftStick2.y = -gamepad2.left_stick_y;
    }
    //Updates the vectors representing the right stick of the second gamepad
    public void updateRightStick2(){
        rightStick2.x = gamepad2.right_stick_x;
        rightStick2.y = -gamepad2.right_stick_y;
    }
    //Updates the vectors representing the sticks of the second gamepad
    public void updateGamepad2(){
        updateLeftStick2();
        updateRightStick2();
    }
    //Updates the vectors representing the sticks of the first gamepad
    public void updateGamepad1(){
        updateLeftStick1();
        updateRightStick1();
    }
    //Updates the vectors representing the sticks of the gamepads
    public void updateGamepads(){
        updateGamepad1();
        updateGamepad2();
    }
    //Represents the type of module used
    public enum Module{
        REV,
        MR
    }
    //Returns true if and only if the robot is using the imu and a REV module
    private boolean isUsingIMU(){
        return module.equals(Module.REV) && usingIMU;
    }

}