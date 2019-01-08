package org.techvalleyhigh.frc5881.deepspace.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.TestCommand;
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
  public static Manipulator manipulator;

  public static AHRS navX;

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
    manipulator = new Manipulator();

    /*
    OI must be constructed after subsystems. If the OI creates Commands
    (which it very likely will), subsystems are not guaranteed to be
    constructed yet. Thus, their requires() statements may grab null
    pointers. Bad news. Don't move it.
     */
    oi = new OI();

    SPI.Port port = SPI.Port.kOnboardCS0;
    navX = new AHRS(port);

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
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    SmartDashboard.putNumber("X accel", navX.getRawAccelX());
    SmartDashboard.putNumber("Y accel", navX.getRawAccelY());
    SmartDashboard.putNumber("Z accel", navX.getRawAccelZ());

    SmartDashboard.putNumber("X gyro", navX.getRawGyroX());
    SmartDashboard.putNumber("Y gyro", navX.getRawGyroY());
    SmartDashboard.putNumber("Z gyro", navX.getRawGyroZ());
  }
}
