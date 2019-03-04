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
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;



/**
 * Subsystem to control everything that has to do with driving, except motion profiling
 */
public class DriveControl extends Subsystem {
  // Define motors
  public static CANSparkMax frontLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax frontRightMotor = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax backLeftMotor = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);

  // Speed to target when we start tipping
  public static final double TIPPING_SPEED = 0.5;

  public double currentVoltage = 0;

  private static final String X_AXIS_SENSITIVITY = "X axis sensitivity";
  private static final String Y_AXIS_SENSITIVITY = "Y axis sensitivity";

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
`   * Create the subsystem with a default name
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
    backLeftMotor.follow(frontLeftMotor);

    backRightMotor.follow(frontRightMotor);


    SmartDashboard.putNumber("Low dV per every 20 milliseconds", 1);
    SmartDashboard.putNumber("Middle dV per every 20 milliseconds", 1);
    SmartDashboard.putNumber("High dV per every 20 milliseconds", 1);

    SmartDashboard.putNumber(X_AXIS_SENSITIVITY, .8);
    SmartDashboard.putNumber(Y_AXIS_SENSITIVITY, -1);

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

  /**
   * Ramps the voltage of the motors
   */
  public void rampedArcadeDrive(){
    double speed = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS) * getYAxisSensitivity();
    double turn = Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_X_AXIS);

    // Slow down turn rate linearly to elevator height
    turn *= (1.4 - Robot.elevator.getSetpoint());

    // Cap turn rate to XAxisSensitivity
    turn = Math.min(turn, getXAxisSensitivity());

    double diffVoltage = currentVoltage - 12 * speed;

    // Init the maximum change in voltage
    double dV;

    switch (Robot.elevator.elevatorState) {
      case FLOOR:
        // Sets the dV to the low dV value
        dV = getLowDV();
        break;
      case LOW_HATCH:
        // Sets the dV to the low dV value
        dV = getLowDV();
        break;
      case LOW_CARGO:
        // Sets the dV to the low dV value
        dV = getLowDV();
        break;
      case MIDDLE_HATCH:
        // Sets the dV to the middle dV value
        dV = getMiddleDV();
        break;
      case MIDDLE_CARGO:
        // Sets the dV to the middle dV value
        dV = getMiddleDV();
        break;
      case HIGH_HATCH:
        // Sets the dV to the high dV value
        dV = getHighDV();
        break;
      case HIGH_CARGO:
        // Sets the dV to the high dV value
        dV = getHighDV();
        break;
      default:
        // Sets the dV to the low dV value
        dV = getLowDV();
    }

    if (Math.abs(diffVoltage) < dV) {
      currentVoltage = 12 * speed;
    } else {
      // Add voltage to accelerate forward, decrease voltage to accelerate backwards
      if (diffVoltage >= 0) {
        currentVoltage += dV;
      } else {
        currentVoltage -= dV;
      }
    }

    rawArcadeDrive(currentVoltage / 12, turn);
  }

  public double getLowDV() {
    return SmartDashboard.getNumber("Low dV per every 20 milliseconds", 1);
  }

  public double getMiddleDV(){
    return SmartDashboard.getNumber("Middle dV per every 20 milliseconds", 1);
  }

  public double getHighDV(){
    return SmartDashboard.getNumber("High dV per every 20 milliseconds", 1);
  }

  public double getXAxisSensitivity() {
    return SmartDashboard.getNumber(X_AXIS_SENSITIVITY, -1);
  }

  public double getYAxisSensitivity() {
    return SmartDashboard.getNumber(Y_AXIS_SENSITIVITY, 1);
  }

  public static final double TIPPING_SPEED = 0.5;

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
