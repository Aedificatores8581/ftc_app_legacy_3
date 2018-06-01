package org.firstinspires.ftc.teamcode.robotTemplates;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robotUniversal.UniversalConstants;
import org.firstinspires.ftc.teamcode.robotUniversal.UniversalFunctions;
import org.firstinspires.ftc.teamcode.robotUniversal.Vector2;


public class CrabDT extends HolonomicDT {
    public DcMotor rf, lf, la, ra, cm;
    public Servo clutch;
    public double               drivePow,
                                clutchStartPosition,
                                desiredCMEncoderPos,
                                encToDisengage,
                                cmAngle,
                                desiredCMAngle,
                                angleOfRobot,
                                directionMult,
                                wheelStartAngle,
                                maxPow;
    final double                encRatio,
                                angleThreshhold     = 1;
    int                         encPos              = 0;
    public boolean              turn                = true,
                                wheelModeSet        = false;
    public DriveMode            driveMode;
    public WheelRotationMode    wheelRotationMode;
    public Drivetrain.Direction direction;
    public TankDriveMode        switchMode;
    public TankDT               TankDrive           = new TankDT(brakePow) {
        public void setLeftPow(double pow){
            setPower(lf, pow);
            setPower(la, pow);
            leftPow = pow;
        }
        public void setRightPow(double pow){
            setPower(rf, pow);
            setPower(ra, pow);
            rightPow = pow;
        }
        public void initMotors(){

        }
        public void normalizeMotors(){
            maxPow = UniversalFunctions.maxAbs(rightPow, leftPow);
            rightPow /= maxPow;
            leftPow /= maxPow;
        }
    };
    public CrabDT(double encRat, double brake, double speed){
        super(brake);
        encRatio = encRat;
        maxSpeed = speed;
    }
    public CrabDT(){
        super(0.01);
        encRatio = 0;
    }
    public void initMotors(){
        rf = hardwareMap.dcMotor.get("rf");
        lf = hardwareMap.dcMotor.get("lf");
        la = hardwareMap.dcMotor.get("la");
        ra = hardwareMap.dcMotor.get("ra");
        clutch = hardwareMap.servo.get("bs");
        rf.setDirection(FORWARD);
        lf.setDirection(REVERSE);
        la.setDirection(REVERSE);
        ra.setDirection(FORWARD);
        clutch.setPosition(clutchStartPosition);
    }
    public void initCMMotor(){
        switch(wheelRotationMode){
            case SMOOTH:
                cm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                cm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                break;
            case FAST:
                cm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void refreshMotors(){
        rf.setPower(drivePow - Math.cos(Math.toRadians(angleOfRobot + wheelStartAngle)) * turnPow);
        lf.setPower(drivePow - Math.cos(Math.toRadians(angleOfRobot + 90 + wheelStartAngle)) * turnPow);
        la.setPower(drivePow - Math.cos(Math.toRadians(angleOfRobot + 180 + wheelStartAngle)) * turnPow);
        ra.setPower(drivePow - Math.cos(Math.toRadians(angleOfRobot + 270 + wheelStartAngle)) * turnPow);
    }
    public void refreshMotors(double I, double II, double III, double IV){
        setPower(rf, I);
        setPower(lf, II);
        setPower(la, III);
        setPower(ra, IV);
    }
    @Override
    //Gives the motors holding power
    public void brake(){
        if(rf.getPower() == 0 && lf.getPower() == 0 && la.getPower() == 0 && ra.getPower() == 0){
            rf.setPower(-brakePow);
            ra.setPower(brakePow);
            la.setPower(-brakePow);
            lf.setPower(brakePow);
        }
    }
    //specifies the turn multiplier
    @Override
    public void init(){
        super.init();
        encPos = cm.getCurrentPosition();
        switchMode = TankDriveMode.SHIFT;
    }
    public void loop(){
        updateGamepad1();
        switch(driveMode) {
            case SWERVE:
                angleOfRobot = normalizeGyroAngle();
                setRobotAngle();
                switchTurnState();
                setVelocity(leftStick1);
                refreshMotors();
                break;
            case TANK:
                switch (switchMode) {
                    case SHIFT:
                        switchToTank();
                        break;
                    case TANK:
                        TankDrive.loop();
                }
        }
    }
    //returns the number of encoder ticks coresponding to specified angle
    public double getEncoderRotation(double angle){
        return angle / 360 * encRatio;
    }
    //returns the angle of rotation coresponding to a specified number of encoder ticks
    public double getMotorAngle(double enc){
        return UniversalFunctions.normalizeAngle(enc * 360 / encRatio);
    }
    //returns the number of encoder ticks coresponding to specified change in angle
    public double getEncoderRotation(double desiredAngle, double currentAngle) {
        return UniversalFunctions.normalizeAngle180(desiredAngle, currentAngle) / 360 * encRatio;
    }

    public void setVelocity(double ang, double speed){
        cmAngle = UniversalFunctions.normalizeAngle(getMotorAngle(encPos));
        if (Math.abs(gamepad1.left_stick_y) >= UniversalConstants.Triggered.STICK || Math.abs(gamepad1.left_stick_x) >= UniversalConstants.Triggered.STICK)
            desiredCMAngle = UniversalFunctions.normalizeAngle180(ang, cmAngle);
        switch (direction) {
            case FOR:
                if (Math.abs(desiredCMAngle) > 90 && turn) {
                    direction = Direction.BACK;
                    directionMult *= -1;
                    turn = false;
                } else if (Math.abs(desiredCMAngle) < 90)
                    turn = true;
                break;
            case BACK:
                desiredCMAngle = UniversalFunctions.normalizeAngle(desiredCMAngle + 180);
                if (Math.abs(desiredCMAngle) > 90 && turn) {
                    direction = Direction.FOR;
                    turn = false;
                    directionMult *= -1;
                } else if (Math.abs(desiredCMAngle) < 90)
                    turn = true;
                break;
        }
        desiredCMAngle = UniversalFunctions.normalizeAngle(desiredCMAngle, angleOfRobot);
        desiredCMEncoderPos = getEncoderRotation(desiredCMAngle, cmAngle);
        switch(wheelRotationMode){
            case SMOOTH:
                if(!wheelModeSet) {
                    cm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    wheelModeSet = true;
                }
                encPos = cm.getCurrentPosition();
                cm.setPower(Math.sin(Math.toRadians(getMotorAngle(desiredCMEncoderPos - encPos))));
                break;
            case FAST:
                if(!wheelModeSet) {
                    cm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    wheelModeSet = true;
                }
                encPos += desiredCMEncoderPos;
                cm.setTargetPosition(encPos);
                break;
        }
        drivePow = directionMult * speed;
    }
    public void setVelocity(Vector2 vel){
        setVelocity(vel.angle(), vel.magnitude());
    }
    public void normalizeMotors(){
        maxPow = UniversalFunctions.maxAbs(ra.getPower(), la.getPower(), rf.getPower(), lf.getPower());
        if (maxPow > 1) {
            refreshMotors(rf.getPower() / maxPow, lf.getPower() / maxPow, la.getPower() / maxPow, ra.getPower() / maxPow);
        }
    }
    //resets the position of the wheels and disengages the cm motor
    public void switchToTank(){
        cmAngle = UniversalFunctions.normalizeAngle(getMotorAngle(encPos));
        desiredCMAngle = UniversalFunctions.normalizeAngle(0, angleOfRobot);
        desiredCMEncoderPos = getEncoderRotation(desiredCMAngle, cmAngle);
        encPos = (int) getEncoderRotation(cmAngle);
        cm.setPower(Math.sin(Math.toRadians(encPos)));
        if (Math.abs(desiredCMAngle) < angleThreshhold) {
            clutch.setPosition(encToDisengage);
            switchMode = TankDriveMode.TANK;
        }
    }
    //switches the control system from swerve to non-holonomic
    public enum DriveMode{
        SWERVE,
        TANK
    }
    //switches from the act of switching DriveModes
    public enum TankDriveMode{
        SHIFT,
        TANK
    }
    //switches from two types of turning
    public enum WheelRotationMode{
        FAST,
        SMOOTH
    }
}