package org.techvalleyhigh.frc5881.deepspace.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.lift.LiftSave;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.drive.ArcadeDrive;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.drive.DriveSave;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.lift.ManualLift;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Define OI and subsystems
  public static OI oi;
  public static Climber climber;
  public static DriveControl driveControl;
  public static Elevator elevator;
  public static Intake intake;
  public static UpsideDown upsideDown;
  public static LED led;

  public static AHRS navX;

  // Commands
  public static ArcadeDrive driveCommand;
  public static ManualLift liftCommand;

  private UsbCamera camera;
  private CameraServer server;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // Init subsystems
    climber = new Climber();
    driveControl = new DriveControl();
    elevator = new Elevator();
    intake = new Intake();
    upsideDown = new UpsideDown();
    led = new LED();

    camera = CameraServer.getInstance().startAutomaticCapture(0);

    /*
    OI must be constructed after subsystems. If the OI creates Commands
    (which it very likely will), subsystems are not guaranteed to be
    constructed yet. Thus, their requires() statements may grab null
    pointers. Bad news. Don't move it.
     */
    oi = new OI();

    driveCommand = new ArcadeDrive();
    liftCommand = new ManualLift();

    try {
      SPI.Port port = SPI.Port.kMXP;
      navX = new AHRS(port);
    } catch (RuntimeException ex) {
      System.err.println(ex);
    }
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

    SmartDashboard.putNumber("Bar Current", elevator.getBarCurrent());
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
    // We don't have auto, start tele-op instead
    teleopInit();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Use normal drive control
    teleopPeriodic();
  }

  /**
   * Initialization code for teleop mode should go here.
   */
  @Override
  public void teleopInit() {
    elevator.init();

    // Start the drive commands
    driveCommand.start();
    liftCommand.start();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // If the bot is at an angle of greater than 30 degrees then run stop tipping

    // TODO: Save commands
    if (Math.abs(navX.getRawGyroY()) > 30) {
      //DriveSave driveSave = new DriveSave();
      //driveSave.start();
    }
    // If the bot is at an angle of greater than 30 degrees then do lift save.
    if (navX.getRawGyroY() > 30) {
      //LiftSave liftSave = new LiftSave();
      //liftSave.start();
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }

  /**
   * Periodic code for disabled mode should go here.
   */
  @Override
  public void disabledPeriodic() {

  }
}
