package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.utils.GyroPIDOutput;

/**
 * Subsystem to control everything that has to do with driving, except motion profiling
 */
public class DriveControl extends Subsystem {
  // Define motors
  public static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(1);
  public static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(2);
  public static WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(3);
  public static WPI_TalonSRX backRightMotor = new WPI_TalonSRX(4);

  // Speed to target when we start tipping
  public static final double TIPPING_SPEED = 0.5;

  // Define robot drive for controls
  private DifferentialDrive robotDrive;

  // Gyro PID for turning
  private PIDController gyroPID;
  private GyroPIDOutput gyroPIDoutput = new GyroPIDOutput();

  /**
   * Starts a command on init of subsystem, defining commands in robot and OI is preferred
   */
  @Override
  protected void initDefaultCommand() {
  }

  /**
   * Create the subsystem with a default name
   */
  public DriveControl() {
    super();

    // Put numbers on SmartDashboard
    SmartDashboard.putNumber("left kP", 2);
    SmartDashboard.putNumber("left kI", 0);
    SmartDashboard.putNumber("left kD", 20);
    SmartDashboard.putNumber("left kF", 0.076);
    SmartDashboard.putNumber("right kP", 2);
    SmartDashboard.putNumber("right kI", 0);
    SmartDashboard.putNumber("right kD", 20);
    SmartDashboard.putNumber("right kF", 0.076);

    SmartDashboard.putNumber("gyro kP", 0);
    SmartDashboard.putNumber("gyro kI", 0);
    SmartDashboard.putNumber("gyro kD", 0);

    init();
  }

  /**
   * Initialize SmartDashboard and other local variables
   */
  public void init() {
    frontLeftMotor.setName("Drive", "Front Left");
    frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    frontLeftMotor.config_kP(0, getLeft_kP(), 10);
    frontLeftMotor.config_kI(0, getLeft_kI(), 10);
    frontLeftMotor.config_kD(0, getLeft_kD(), 10);
    frontLeftMotor.config_kF(0, getLeft_kF(), 10);
    LiveWindow.add(frontLeftMotor);

    frontRightMotor.setName("Drive", "Front Right");
    frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    frontRightMotor.config_kP(0, getRight_kP(), 10);
    frontRightMotor.config_kI(0, getRight_kI(), 10);
    frontRightMotor.config_kD(0, getRight_kD(), 10);
    frontRightMotor.config_kF(0, getRight_kF(), 10);
    LiveWindow.add(frontRightMotor);

    backLeftMotor.setName("Drive", "Back Left");
    backLeftMotor.set(ControlMode.Follower, 1);
    LiveWindow.add(backLeftMotor);

    backRightMotor.setName("Drive", "Back Right");
    backRightMotor.set(ControlMode.Follower, 2);
    LiveWindow.add(backRightMotor);

    SpeedControllerGroup m_left = new SpeedControllerGroup(frontLeftMotor);
    SpeedControllerGroup m_right = new SpeedControllerGroup(frontRightMotor);
    robotDrive = new DifferentialDrive(m_right, m_left);

    robotDrive.setName("Drive");
    LiveWindow.add(robotDrive);

    gyroPID = new PIDController(getGyro_kP(), getGyro_kI(), getGyro_kD(), Robot.navX, gyroPIDoutput);
    gyroPID.setOutputRange(-1, 1);
  }

  /**
   * Pass raw values to arcade drive, don't pass joystick inputs directly
   *
   * @param speed Drive speed -1 backwards -> 1 forward
   * @param turn  Turn rate -1 left -> 1 right
   */
  public void rawArcadeDrive(double speed, double turn) {
    robotDrive.arcadeDrive(speed, turn, true);
  }

  /**
   * Implements arcade drive with joystick inputs
   */
  @SuppressWarnings("Duplicates")
  public void arcadeJoystickInputs() {
    double speed = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS);
    double turn = Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_X_AXIS);

    if (Math.abs(turn) < 0.1) {
      turn = 0;
    }
    if (Math.abs(speed) < 0.1) {
      speed = 0;
    }

    rawArcadeDrive(turn, speed);
  }

  public void stop() {
    robotDrive.stopMotor();
  }

  public void setGyroSetpoint(double setpoint) {
    gyroPID.setSetpoint(setpoint);
  }

  public double getGyroSetpoint() {
    return gyroPID.getSetpoint();
  }

  public double getGyroError() {
    return gyroPID.getError();
  }

  public boolean getGyroOnTarget() {
    return gyroPID.onTarget();
  }

  public double getGyroPIDoutput() {
    return gyroPIDoutput.getOutput();
  }

  public double getLeft_kP() {
    return SmartDashboard.getNumber("left kP", 2.0);
  }
  public double getLeft_kI(){
    return SmartDashboard.getNumber("left kI", 2.0);
  }
  public double getLeft_kD(){
    return SmartDashboard.getNumber("left kD",2.0);
  }
  public double getLeft_kF(){
    return SmartDashboard.getNumber("left kF",2.0);
  }
  public double getRight_kP() {
    return SmartDashboard.getNumber("right kP", 2.0);
  }
  public double getRight_kI(){
    return SmartDashboard.getNumber("right kI", 2.0);
  }
  public double getRight_kD(){
    return SmartDashboard.getNumber("right kD",2.0);
  }
  public double getRight_kF(){
    return SmartDashboard.getNumber("right kF",2.0);
  }

  public double getGyro_kP() {
    return SmartDashboard.getNumber("gyro kP", 0.0);
  }
  public double getGyro_kI() {
    return SmartDashboard.getNumber("gyro kI", 0.0);
  }
  public double getGyro_kD() {
    return SmartDashboard.getNumber("gyro kD", 0.0);
  }
}

