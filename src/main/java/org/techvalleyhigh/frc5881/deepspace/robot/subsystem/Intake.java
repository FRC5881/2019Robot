package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

  private double GRAB_SPEED = -1;
  private double SHOOT_SPEED = 1;

  public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(7);

  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {

  }

  /**
   * Creates a subsystem. This will set the name to the name of the class.
   */
  public Intake() {
    init();
  }

  public void init() {
    intakeMotor.setName("Intake", "Motor");
    LiveWindow.add(intakeMotor);

    SmartDashboard.putNumber("Intake Speed", 1);
  }

  public void shoot() {
    intakeMotor.set(SHOOT_SPEED * SmartDashboard.getNumber("Intake Speed", 1));
  }

  public void grab() {
    intakeMotor.set(GRAB_SPEED * SmartDashboard.getNumber("Intake Speed", 1));
  }

  public void stop() {
    intakeMotor.stopMotor();
  }
}
