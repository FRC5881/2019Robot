package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Subsystem to control everything that has to do with driving, except motion profiling
 */
public class DriveControl extends Subsystem {
  // Define motors
  public static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(1);
  public static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(2);
  public static WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(3);
  public static WPI_TalonSRX backRightMotor = new WPI_TalonSRX(4);

  // Define robot drive for controls
  private DifferentialDrive robotDrive;

  /**
   * Starts a command on init of subsystem, defining commands in robot and OI is preferred
   */
  @Override
  protected void initDefaultCommand() {}

  /**
   * Create the subsystem with a default name
   */
  public DriveControl() {
    super();

    init();
  }

  /**
   * Initialize SmartDashboard and other local variables
   */
  public void init() {
    frontLeftMotor.setName("Drive", "Front Left");
    frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    LiveWindow.add(frontLeftMotor);

    frontRightMotor.setName("Drive", "Front Right");
    frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
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
  }

  /**
   * Pass raw values to arcade drive, don't pass joystick inputs directly
   * @param speed Drive speed -1 backwards -> 1 forward
   * @param turn Turn rate -1 left -> 1 right
   */
  public void rawArcadeDrive(double speed, double turn){
    robotDrive.arcadeDrive(speed, turn, true);
  }

  /**
   * Implements arcade drive with joystick inputs
   */
  public void arcadeJoystickInputs (){
    double speed = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS);
    double turn = Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_X_AXIS);

    if(Math.abs(turn) < 0.1 || Math.abs(speed) < 0.1) {
      speed = 0 ;
      turn = 0;
    }

    rawArcadeDrive(turn, speed);
  }
  public static final double TIPPING_SPEED = 0.5;


}
