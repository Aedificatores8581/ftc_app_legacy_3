package org.firstinspires.ftc.teamcode.robotTemplates;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.qualcomm.robotcore.util.Range;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 * Conjured into existence by The Saminator on 06-29-2017.
 */
public abstract class DriveBotTestTemplate extends OpMode {


    protected static class Constants {
        public static final DcMotor.Direction LEFT_FORE_DIR = DcMotor.Direction.REVERSE;
        public static final DcMotor.Direction LEFT_REAR_DIR = DcMotor.Direction.REVERSE;
        public static final DcMotor.Direction RIGHT_FORE_DIR = DcMotor.Direction.FORWARD;
        public static final DcMotor.Direction RIGHT_REAR_DIR = DcMotor.Direction.FORWARD;

        public static final DcMotor.Direction INTAKE_LEFT_DIR = DcMotor.Direction.FORWARD;
        public static final DcMotor.Direction INTAKE_RIGHT_DIR = DcMotor.Direction.FORWARD;

        public static final DcMotor.Direction GLYPH_LIFT_DIR = DcMotor.Direction.FORWARD;

        public static final DcMotor.Direction BELT1_DIR = DcMotor.Direction.FORWARD;
        public static final DcMotor.Direction BELT2_DIR = DcMotor.Direction.REVERSE;

        public static final double RED_THRESHOLD = 0.4;
        public static final double BLUE_THRESHOLD = 0.3;

        public static final double JEWEL_ARM_UP_POSITION = 0.025;

        public static final double JEWEL_ARM_DOWN_POSITION = 0.74;

        public static final double CENTER_FINGER = 0.48;

        public static final double JEWEL_ARM_DETECT_POSITION = 0.56;

        public static final double DISPENSE_POSITION = 1.0;

        public static final double RETRACT_DISPENSE_POSITION = 1.0;

        public static final double LEVEL_DISPENSER = 0.5;

        public static final double RAMP_SPEED = 0.0625;

        public static final double LEFT_FORE_SPEED = 1.0;
        public static final double LEFT_REAR_SPEED = 1.0;
        public static final double RIGHT_FORE_SPEED = 1.0;
        public static final double RIGHT_REAR_SPEED = 1.0;

        public static final double EMPTY_INTAKE_VALUE = (double) Double.NaN;

        public static final double DISTANCE_TO_CENTER = 8;
        public static final int ENC_TO_PASS_COLUMN = 100;


        private final static double INITIAL_SPEED_FACTOR = 0.2; // As a factor of maxPower in Smooth Drive;
    }

    public enum IntakeState {
        RETRIEVING, SPITTING, NULL
    }

    public enum GlyphInOutIntakeState {
        INSIDE, OUTSIDE
    }

    IntakeState intakeState;
    GlyphInOutIntakeState glyphInOutIntakeState;
    GlyphInOutIntakeState prevGlyphInOutIntakeState;
    int glyphCount;

    private enum SmoothDriveState {
        SPEED_UP,
        MAX_SPEED,
        SLOW_DOWN,
        STOP
    }


    DcMotor leftFore, leftRear, rightFore, rightRear;
    DcMotor dispenserLinearSlide;
    DcMotor relicArm;

    DcMotor glyphLift;
    DigitalChannel glyphLiftHigh, glyphLiftLow;
    DistanceSensor intakeSensorRange;
    NormalizedColorSensor intakeSensorColor;
    protected double prevIntakeSensorRangeVal;

    //power port 5v

    DcMotor intakeLeft, intakeRight;


    CRServo belt1, belt2;

    Servo jewelArm, jewelFlipper, relicHand, relicFingers, glyphOutput, rIntake, lIntake;

    NormalizedColorSensor color, colorL;
    NormalizedRGBA colors;


    BNO055IMU imu;
    AnalogInput ampSensor;

    protected Orientation angles;
    protected Acceleration gravity;

    private boolean dancing;

    public double angleAtStart;

    public DigitalChannel magFront;
    public DigitalChannel magBack;

    public boolean prox, possibleProx;

    public DistanceSensor dSensorR, dSensorL;

    @Override
    public void init() {

        this.msStuckDetectInit = 60000;

        intakeState = IntakeState.NULL;
        glyphInOutIntakeState = GlyphInOutIntakeState.OUTSIDE;
        glyphCount = 0;

        dancing = false;

        //region Configuration section
        dSensorR = hardwareMap.get(DistanceSensor.class, "dsR");
        dSensorL = hardwareMap.get(DistanceSensor.class, "dsL");
        leftFore = hardwareMap.dcMotor.get("lfm"); // port 2
        leftRear = hardwareMap.dcMotor.get("lrm"); // port 3
        rightFore = hardwareMap.dcMotor.get("rfm"); // port 0
        rightRear = hardwareMap.dcMotor.get("rrm"); // port 1

        glyphLift = hardwareMap.dcMotor.get("gl");
        glyphLiftHigh = hardwareMap.digitalChannel.get("tts");
        glyphLiftLow = hardwareMap.digitalChannel.get("bts");

        intakeSensorRange = hardwareMap.get(DistanceSensor.class, "range");
        intakeSensorColor = hardwareMap.get(NormalizedColorSensor.class, "range");

        intakeLeft = hardwareMap.dcMotor.get("iml");
        intakeRight = hardwareMap.dcMotor.get("imr");

        belt1 = hardwareMap.crservo.get("vm1");
        belt2 = hardwareMap.crservo.get("vm2");

        jewelArm = hardwareMap.servo.get("ja");
        jewelFlipper = hardwareMap.servo.get("jf");

        relicArm = hardwareMap.dcMotor.get("ra");
        relicHand = hardwareMap.servo.get("rh");
        relicFingers = hardwareMap.servo.get("rf");

        rIntake = hardwareMap.servo.get("rir");

        lIntake = hardwareMap.servo.get("lir");


        glyphOutput = hardwareMap.servo.get("gd");
        color = hardwareMap.get(NormalizedColorSensor.class, "jcolorR");

        colorL = hardwareMap.get(NormalizedColorSensor.class, "jcolorL");

        magFront = hardwareMap.digitalChannel.get("mf");
        magBack = hardwareMap.digitalChannel.get("mb");
        ampSensor = hardwareMap.analogInput.get("amp");

        //endregion

        //angleAtStart = new GyroAngles(angles).getZ();

        magFront.setMode(DigitalChannel.Mode.INPUT);
        magBack.setMode(DigitalChannel.Mode.INPUT);

        leftFore.setDirection(Constants.LEFT_FORE_DIR);
        leftRear.setDirection(Constants.LEFT_REAR_DIR);
        rightFore.setDirection(Constants.RIGHT_FORE_DIR);
        rightRear.setDirection(Constants.RIGHT_REAR_DIR);

        glyphLift.setDirection(Constants.GLYPH_LIFT_DIR);
        glyphLiftHigh.setMode(DigitalChannel.Mode.INPUT);
        glyphLiftLow.setMode(DigitalChannel.Mode.INPUT);

        intakeLeft.setDirection(Constants.INTAKE_LEFT_DIR);
        intakeRight.setDirection(Constants.INTAKE_RIGHT_DIR);

        belt1.setDirection(Constants.BELT1_DIR);
        belt2.setDirection(Constants.BELT2_DIR);

        leftFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        if (needsGyroSensor()) {
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.mode = BNO055IMU.SensorMode.IMU;
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            imu = hardwareMap.get(BNO055IMU.class, "imu");
            imu.initialize(parameters);
        }

    }

    public void start() {

    }

    @Override
    public void stop() {
        setLeftPow(0.0);
        setRightPow(0.0);
    }

    GlyphInOutIntakeState checkGlyphIntakeStatus() {
        GlyphInOutIntakeState returnState;

        if (!(Double.isNaN(intakeSensorRange.getDistance(DistanceUnit.CM)))) {
            returnState = GlyphInOutIntakeState.INSIDE;

        } else {
            returnState = GlyphInOutIntakeState.OUTSIDE;
        }
        return returnState;
    }

    // Returns the change in the number of glyphs in the intake system.
    int checkGlyphCount(IntakeState paramIntakeState, GlyphInOutIntakeState paramGlyphInOutIntakeState, GlyphInOutIntakeState paramPrevGlyphInOutIntakeState) {

        if (!paramGlyphInOutIntakeState.equals(paramPrevGlyphInOutIntakeState)) { //
            if ((paramGlyphInOutIntakeState == GlyphInOutIntakeState.INSIDE) && paramIntakeState == IntakeState.RETRIEVING) {
                return 1;
            } else if ((paramGlyphInOutIntakeState == GlyphInOutIntakeState.OUTSIDE) && (paramIntakeState == IntakeState.SPITTING)) {
                return -1;
            }

        }
        return 0;
    }

    // Returns the current intake state based on the power being provided to the intake motors
    IntakeState checkIntakeState(double paramPower, IntakeState paramIntakeState) {
        if (paramPower > 0) {
            return IntakeState.RETRIEVING;
        } else if (paramPower < 0) {
            return IntakeState.SPITTING;
        } else {
            return paramIntakeState;
        }
    }

    public void succ(double power) {
        intakeLeft.setPower(-power);
        intakeRight.setPower(power);

        glyphInOutIntakeState = checkGlyphIntakeStatus();
        intakeState = checkIntakeState(power, intakeState);
        glyphCount += checkGlyphCount(intakeState, glyphInOutIntakeState, prevGlyphInOutIntakeState);
    }

    public void belt(double power) {
        belt1.setPower(power);
        belt2.setPower(power);
    }

    public void setLeftPow(double pow) {
        leftFore.setPower(pow * Constants.LEFT_FORE_SPEED);
        leftRear.setPower(pow * Constants.LEFT_REAR_SPEED);
    }


    public void setRightPow(double pow) {
        rightFore.setPower(pow * Constants.RIGHT_FORE_SPEED);
        rightRear.setPower(pow * Constants.RIGHT_REAR_SPEED);
    }

    public boolean checkLeftEncoder(int ticks) {
        int distance = Math.abs(ticks);
        int leftForeDist = Math.abs(leftFore.getCurrentPosition());
        int leftRearDist = Math.abs(leftRear.getCurrentPosition());

        boolean mtrsHere = (distance <= leftForeDist)
                || (distance <= leftRearDist);

        return mtrsHere;
    }

    public boolean checkRightEncoder(int ticks) {
        int distance = Math.abs(ticks);
        int rightForeDist = Math.abs(rightFore.getCurrentPosition());
        int rightRearDist = Math.abs(rightRear.getCurrentPosition());

        boolean mtrsHere = (distance <= rightForeDist)
                || (distance <= rightRearDist);

        return mtrsHere;
    }

    public boolean checkEncoders(int ticks) {
        return checkLeftEncoder(ticks) || checkRightEncoder(ticks);
    }

    public boolean checkEncodersReverse(int ticks) {
        int distance = Math.abs(ticks);
        int leftForeDist = Math.abs(leftFore.getCurrentPosition());
        int leftRearDist = Math.abs(leftRear.getCurrentPosition());
        int rightForeDist = Math.abs(rightFore.getCurrentPosition());
        int rightRearDist = Math.abs(rightRear.getCurrentPosition());

        boolean mtrsHere = (distance >= leftForeDist)
                || (distance >= leftRearDist)
                || (distance >= rightForeDist)
                || (distance >= rightRearDist);

        return mtrsHere;
    }

    public boolean triggered(double value) {
        return value >= 0.15;
    }

    public void resetEncoders() {
        setLeftPow(0);
        setRightPow(0);

        leftFore.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFore.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void reinitMotors(double leftSpeed, double rightSpeed) {
        leftFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFore.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        setLeftPow(leftSpeed);
        setRightPow(rightSpeed);
    }

    public double csDistance(NormalizedRGBA ds) {
        return (ds.red + ds.blue + ds.green);
    }

    public enum ColorType {
        RED, GREEN, BLUE, TOTAL;
    }

    public double detectColor(NormalizedRGBA cs, ColorType col) {
        switch (col) {
            case RED:
                return cs.red / csDistance(cs);
            case GREEN:
                return cs.blue / csDistance(cs);
            case BLUE:
                return cs.green / csDistance(cs);
            case TOTAL:
                return csDistance(cs);
            default:
                // Return one googol. This not a typo but an actual name for a one followed by 100 zeroes.
                // Don't call it a grave. It's the future you chose.
                return 1E100;
        }
    }

    private boolean drivingSlow;
    private double driveSlowAccPower;
    private int driveSlowEncoder;

    public void driveSlow(double sPower, double aPower, int enc) {
        drivingSlow = true;
        driveSlowAccPower = aPower;
        driveSlowEncoder = enc;

        //ENCODER RESET
        resetEncoders();
        reinitMotors(sPower, sPower);
    }

    public void driveSlowCheck() {
        if (drivingSlow) {
            if (checkEncoders(driveSlowEncoder * 2 / 3)) {
                setLeftPow(driveSlowAccPower);
                setRightPow(driveSlowAccPower);
            } else if (checkEncoders(driveSlowEncoder))
                drivingSlow = false;
        }
    }

    private boolean turningSlow;
    private double turnSlowAccPower;
    private int turnSlowEncoder;

    // CW is positive, CCW is negative.
    public void turnSlow(double sPower, double aPower, int enc) {
        turningSlow = true;
        turnSlowAccPower = aPower;
        turnSlowEncoder = enc;

        //ENCODER RESET
        resetEncoders();
        reinitMotors(sPower, -sPower);
    }

    public void turnSlowCheck() {
        if (turningSlow) {
            if (checkEncoders(turnSlowEncoder * 2 / 3)) {
                setLeftPow(turnSlowAccPower);
                setRightPow(-turnSlowAccPower);
            } else if (checkEncoders(turnSlowEncoder))
                turningSlow = false;
        }
    }

    public static boolean timeReached(long time, long wait) {
        return System.currentTimeMillis() - time >= wait;
    }

    public boolean runWithArmDistance(DistanceSensor dSensor) {

        if (String.format(Locale.US, "%.02f", dSensor.getDistance(DistanceUnit.CM)).equals("NaN")) {
            if (dSensor.getDistance(DistanceUnit.CM) != (double) Double.NaN) {
                return true;
            }
        }

        return false;
    }

    public double getPowerSmootheDrive(int currentEncoders, int totalEncoders, double percentTimeChangingSpeedPerState, double maxPower) {
        double power = 0.0;
        byte negation; // We couldn't afford 3 extra bytes of storage, so we are using byte :-)
        int encodersSpentChanging;

        negation = (totalEncoders < 0) ? (byte) -1 : 1;
        encodersSpentChanging = (int) (percentTimeChangingSpeedPerState * totalEncoders);

        if (Math.abs(currentEncoders) < Math.abs(encodersSpentChanging)) {
            power = negation * ((((1 - Constants.INITIAL_SPEED_FACTOR) * maxPower) / (totalEncoders * percentTimeChangingSpeedPerState))
                    * (currentEncoders) + (Constants.INITIAL_SPEED_FACTOR * maxPower));
        } else if ((Math.abs(currentEncoders) >= Math.abs(encodersSpentChanging))
                && (Math.abs(currentEncoders) < Math.abs(totalEncoders - encodersSpentChanging))) {
            power = negation * maxPower;
        } else if (Math.abs(currentEncoders) >= Math.abs(totalEncoders - encodersSpentChanging)) {
            power = negation * (-(maxPower / (totalEncoders * percentTimeChangingSpeedPerState))
                    * (currentEncoders - (totalEncoders - encodersSpentChanging)) + maxPower);
        } else {
            power = 0;
        }

        return power;
    }

    public void hitLeftJewel() {
        jewelFlipper.setPosition(1.0);
    }

    public void hitRightJewel() {
        jewelFlipper.setPosition(0.0);
    }

    // This is here for not loading the gyro sensor when in teleop.
    public boolean needsGyroSensor() {
        return true;
    }

    public boolean smoothTurn(double currentAngle, double targetAngle) {
        double error = currentAngle - targetAngle;

        if (error > 180)
            error = error - 360;
        if (error < -180)
            error = -error + 360;
        double turnRang = error / 100;
        turnRang = Range.clip(turnRang, -0.2, 0.2);
        /*if(turnRang > 0) {
            turnRang = Range.clip(turnRang,0.03,0.2);
        }
        else if(turnRang < 0) {
            turnRang = Range.clip(turnRang,-0.2,-0.03);
        }*/
        setLeftPow(turnRang);
        setRightPow(-turnRang);
        if (Math.abs(error) < 1) {
            setLeftPow(0);
            setRightPow(0);
            return true;
        } else {
            return false;
        }
    }/*
    public boolean adjust(float currentAngle,float targetAngle, double targetSpeed) {
        float diff;
        gyroHeading = currentAngle;
        if (targetAngle == 0) {
            if(gyroHeading > 180) {
                diff = gyroHeading - 360;
            } else {
                diff = gyroHeading - 0;
            }
        } else {
            diff = gyroHeading - targetAngle;
        }
        leftDrivePower = targetSpeed+diff/20;      // we were at 20 before we started playing
        rightDrivePower = targetSpeed-diff/20;
        if(leftDrivePower > 1.0){
            leftDrivePower = 1.0;
        }
        if(leftDrivePower < -1.0) {
            leftDrivePower = -1.0;
        }
        if(rightDrivePower > 1.0) {
            rightDrivePower = 1.0;
        }
        if(rightDrivePower < -1.0) {
            rightDrivePower = -1.0;
        }
        setPower(leftDrivePower, rightDrivePower);
        return true;
    }
*/
}