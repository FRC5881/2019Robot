package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.climber.ClimberDrive;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.climber.ClimberLegsAllUp;

/**
 * Subsystem controls everything to do with our 4 legged climber
 */
public class Climber extends Subsystem {
  public static DoubleSolenoid frontSolenoid = new DoubleSolenoid(20, 2, 3);
  public static DoubleSolenoid backSolenoid = new DoubleSolenoid(20, 4, 5);

  public static WPI_TalonSRX leftMotor = new WPI_TalonSRX(5);
  public static WPI_TalonSRX rightMotor = new WPI_TalonSRX(6);

  // Differential drive to handle arcade drive
  private DifferentialDrive climberDriveBase;

  // Climber arcade drive command
  private ClimberDrive driveCommand;

  // Current mode of the climber
  private ClimberMode state = ClimberMode.DISENGAGED;

  /**
   * Enum for the different modes of the climber
   */
  public enum ClimberMode {
    ENGAGED,
    DISENGAGED
  }

  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {
    // Make sure that all the climber legs are retracted
    new ClimberLegsAllUp().start();
  }

  public Climber(){
    super();

    init();
  }

  public void init() {
    leftMotor.setName("Climber", "Left Motor");
    leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    LiveWindow.add(leftMotor);

    rightMotor.setName("Climber", "Right Motor");
    rightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    LiveWindow.add(rightMotor);

    frontSolenoid.setName ("Climber", "Front Piston");
    LiveWindow.add(frontSolenoid);

    backSolenoid.setName("Climber", "Back Piston");
    LiveWindow.add(backSolenoid);

    SpeedControllerGroup m_left = new SpeedControllerGroup(leftMotor);
    SpeedControllerGroup m_right = new SpeedControllerGroup(rightMotor);
    climberDriveBase = new DifferentialDrive(m_right, m_left);
  }

  /**
   * Pass raw values to arcade drive, don't pass joystick inputs directly
   * @param speed Drive speed -1 backwards -> 1 forward
   * @param turn Turn rate -1 left -> 1 right
   */
  public void rawArcadeDrive(double speed, double turn){
    if (state == ClimberMode.ENGAGED) {
      climberDriveBase.arcadeDrive(speed, turn, true);
    }
  }

  /**
   * Implements arcade drive with joystick inputs
   */
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

  public void frontDown(){
    frontSolenoid.set(DoubleSolenoid.Value.kForward);

    // The front wheels are powered
    setState(ClimberMode.ENGAGED);

    // Start driving
    driveCommand.start();
  }

  public void frontUp(){
    frontSolenoid.set(DoubleSolenoid.Value.kReverse);
    // The front wheels are powered
    setState(ClimberMode.DISENGAGED);
  }

  public void backDown(){
    backSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void backUp(){
    backSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  /**
   * Toggle front piston
   * if the piston is disengaged, engage it
   * if the piston is engaged, disengage it
   */
  public void frontToggle(){
    if (frontSolenoid.get() == DoubleSolenoid.Value.kForward) {
      frontUp();
    } else if (frontSolenoid.get() == DoubleSolenoid.Value.kReverse) {
      frontDown();
    } else {
      System.out.println("Front Solenoid is Off");
    }
  }

  /**
   * Toggle back piston
   * If piston is disengaged, engage it
   * If piston is engaged, disengaged is
   */
  public void backToggle() {
    if (backSolenoid.get() == DoubleSolenoid.Value.kReverse) {
      backDown();
    } else if (backSolenoid.get() == DoubleSolenoid.Value.kForward){
      backUp();
    } else{
      System.out.println("Back Solenoid is off");
    }
  }
  public ClimberMode getState() {
    return state;
  }

  public void setState(ClimberMode state) {
    this.state = state;
  }
}
