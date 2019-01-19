package org.techvalleyhigh.frc5881.deepspace.robot.commands.led;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class LEDDocking extends Command {
  public LEDDocking() {
    requires(Robot.led);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("LED docking initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * Since this is a subsystem it should end at some point
   */
  @Override
  protected boolean isFinished() {
    // TODO: Figure out how we are going to know when we are done docking
    return false;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("LED docking command ended");
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
