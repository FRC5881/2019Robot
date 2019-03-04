package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Subsystem controls everything to do with intake
 */
public class Intake extends Subsystem {
  //Only one motor
  public static WPI_TalonSRX intakeMotor = new WPI_TalonSRX(15);
  public static final double SPEED = -.5;

  @Override
  protected void initDefaultCommand() {
  }


  public Intake(){
    super();

    init();
  }

  /**
   * Initialize SmartDashboard and other local variables
   */
  public void init(){
    intakeMotor.setName("Intake", "Motor");
    intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    LiveWindow.add(intakeMotor);
  }

  /**
   * Setting speed of intake when it spins in
   */
  public void spinInInput(){
    intakeMotor.set(SPEED);
  }

  /**
   * Setting speed of intake when it spins out
   */
  public void spinOutInput(){
    intakeMotor.set(-SPEED);
  }

  /**
   * Stops intake motor
   */
  public void stop() {
    intakeMotor.stopMotor();
  }

}
