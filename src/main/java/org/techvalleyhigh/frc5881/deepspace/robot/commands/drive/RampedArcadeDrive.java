package org.techvalleyhigh.frc5881.deepspace.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Ramps the voltage of the motors.
 */
public class RampedArcadeDrive extends Command {
  public RampedArcadeDrive() {
    requires(Robot.driveControl);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Ramped arcade drive initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    Robot.driveControl.rampedArcadeDrive();
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * We want this to end when we dock.
   */
  @Override
  protected boolean isFinished() {
    return false;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("Ramped arcade drive command ended");
    Robot.driveControl.rampedArcadeDrive();
  }

  /**
   * Called when another command which requires one or more of the same
   * subsystems is scheduled to run
   */
  @Override
  protected void interrupted() {
    end();
  }
}
