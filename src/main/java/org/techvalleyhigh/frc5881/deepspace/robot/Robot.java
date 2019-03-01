package org.techvalleyhigh.frc5881.deepspace.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator.LiftSave;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.drive.ArcadeDrive;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.drive.DriveSave;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Define OI and subsystems
  public static OI oi;
  public static TestSubsystem testSubsystem;
  public static Climber climber;
  public static DriveControl driveControl;
  public static Elevator elevator;
  public static Intake intake;
  public static UpsideDown upsideDown;
  public static LED led;

  public static AHRS navX;

  // Commands
  public static ArcadeDrive driveCommand;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // Init subsystems
    testSubsystem = new TestSubsystem();
    climber = new Climber();
    driveControl = new DriveControl();
    elevator = new Elevator();
    intake = new Intake();
    upsideDown = new UpsideDown();

    /*
    OI must be constructed after subsystems. If the OI creates Commands
    (which it very likely will), subsystems are not guaranteed to be
    constructed yet. Thus, their requires() statements may grab null
    pointers. Bad news. Don't move it.
     */
    oi = new OI();

    driveCommand = new ArcadeDrive();

    try {
      SPI.Port port = SPI.Port.kMXP;
      navX = new AHRS(port);
    } catch (RuntimeException ex) {
      System.err.println(ex);
    }


    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();

    SmartDashboard.putNumber("X accel", navX.getRawAccelX());
    SmartDashboard.putNumber("Y accel", navX.getRawAccelY());
    SmartDashboard.putNumber("Z accel", navX.getRawAccelZ());

    /* Display 6-axis Processed Angle Data                                      */
    SmartDashboard.putBoolean("IMU_Connected", navX.isConnected());
    SmartDashboard.putBoolean("IMU_IsCalibrating", navX.isCalibrating());
    SmartDashboard.putNumber("IMU_Yaw", navX.getYaw());
    SmartDashboard.putNumber("IMU_Pitch", navX.getPitch());
    SmartDashboard.putNumber("IMU_Roll", navX.getRoll());

    /* Display tilt-corrected, Magnetometer-based heading (requires             */
    /* magnetometer calibration to be useful)                                   */

    SmartDashboard.putNumber("IMU_CompassHeading", navX.getCompassHeading());

    /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
    SmartDashboard.putNumber("IMU_FusedHeading", navX.getFusedHeading());

    /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
    /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP            */

    SmartDashboard.putNumber("IMU_TotalYaw", navX.getAngle());
    SmartDashboard.putNumber("IMU_YawRateDPS", navX.getRate());

    /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

    SmartDashboard.putNumber("IMU_Accel_X", navX.getWorldLinearAccelX());
    SmartDashboard.putNumber("IMU_Accel_Y", navX.getWorldLinearAccelY());
    SmartDashboard.putBoolean("IMU_IsMoving", navX.isMoving());
    SmartDashboard.putBoolean("IMU_IsRotating", navX.isRotating());

    /* Display estimates of velocity/displacement.  Note that these values are  */
    /* not expected to be accurate enough for estimating robot position on a    */
    /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
    /* of these errors due to single (velocity) integration and especially      */
    /* double (displacement) integration.                                       */

    SmartDashboard.putNumber("Velocity_X", navX.getVelocityX());
    SmartDashboard.putNumber("Velocity_Y", navX.getVelocityY());
    SmartDashboard.putNumber("Displacement_X", navX.getDisplacementX());
    SmartDashboard.putNumber("Displacement_Y", navX.getDisplacementY());

    /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
    /* NOTE:  These values are not normally necessary, but are made available   */
    /* for advanced users.  Before using this data, please consider whether     */
    /* the processed data (see above) will suit your needs.                     */

    SmartDashboard.putNumber("RawGyro_X", navX.getRawGyroX());
    SmartDashboard.putNumber("RawGyro_Y", navX.getRawGyroY());
    SmartDashboard.putNumber("RawGyro_Z", navX.getRawGyroZ());
    SmartDashboard.putNumber("RawAccel_X", navX.getRawAccelX());
    SmartDashboard.putNumber("RawAccel_Y", navX.getRawAccelY());
    SmartDashboard.putNumber("RawAccel_Z", navX.getRawAccelZ());
    SmartDashboard.putNumber("RawMag_X", navX.getRawMagX());
    SmartDashboard.putNumber("RawMag_Y", navX.getRawMagY());
    SmartDashboard.putNumber("RawMag_Z", navX.getRawMagZ());
    SmartDashboard.putNumber("IMU_Temp_C", navX.getTempC());

    /* Omnimount Yaw Axis Information                                           */
    /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
    AHRS.BoardYawAxis yaw_axis = navX.getBoardYawAxis();
    SmartDashboard.putString("YawAxisDirection", yaw_axis.up ? "Up" : "Down");
    SmartDashboard.putNumber("YawAxis", yaw_axis.board_axis.getValue());

    /* Sensor Board Information                                                 */
    SmartDashboard.putString("FirmwareVersion", navX.getFirmwareVersion());

    /* Quaternion Data                                                          */
    /* Quaternions are fascinating, and are the most compact representation of  */
    /* orientation data. All of the Yaw, Pitch and Roll Values can be derived   */
    /* from the Quaternions. If interested in motion processing, knowledge of   */
    /* Quaternions is highly recommended.                                       */
    SmartDashboard.putNumber("QuaternionW", navX.getQuaternionW());
    SmartDashboard.putNumber("QuaternionX", navX.getQuaternionX());
    SmartDashboard.putNumber("QuaternionY", navX.getQuaternionY());
    SmartDashboard.putNumber("QuaternionZ", navX.getQuaternionZ());

    /* Connectivity Debugging Support                                           */
    SmartDashboard.putNumber("IMU_Byte_Count", navX.getByteCount());
    SmartDashboard.putNumber("IMU_Update_Count", navX.getUpdateCount());

    // Puts the Elevator encoder position into Smart Dashboard
    SmartDashboard.putNumber("Elevator Encoder", elevator.getElevatorEncoderPosition());
    // Puts the Elevator error value into the Smart Dashboard
    SmartDashboard.putNumber("Elevator Error", elevator.getElevatorError());
    // Puts the Elevator set point value into the Smart Dashboard
    SmartDashboard.putNumber("Elevator Set Point", elevator.getElevatorSetpoint());

    // Puts the Bar encoder position into Smart Dashboard
    SmartDashboard.putNumber("Bar Encoder", elevator.getBarEncoderPosition());
    // Puts the Bar error value into the Smart Dashboard
    SmartDashboard.putNumber("Bar Error", elevator.getBarError());
    // Puts the Bar set point value into the Smart Dashboard
    SmartDashboard.putNumber("Bar Set Point", elevator.getBarSetpoint());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * Initialization code for teleop mode should go here.
   */
  @Override
  public void teleopInit() {
    elevator.init();

    // Start the drive command
    driveCommand.start();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // If the bot is at an angle of greater than 30 degrees then run stop tipping
    if (Math.abs(navX.getRawGyroY()) > 30) {
      DriveSave driveSave = new DriveSave();
      driveSave.start();
    }
    // If the bot is at an angle of greater than 30 degrees then do elevator save.
    if (navX.getRawGyroY() > 30) {
      LiftSave liftSave = new LiftSave();
      liftSave.start();
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
