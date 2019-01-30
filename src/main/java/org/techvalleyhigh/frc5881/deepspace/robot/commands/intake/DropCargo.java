package org.techvalleyhigh.frc5881.deepspace.robot.commands.intake;

import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *Command to drop cargo
 */
public class DropCargo extends Command{

  public DropCargo() {
    requires(Robot.intake);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("DropCargo Command initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    Robot.intake.spinOutInput();
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
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
    System.out.println("DropCargo ended");
    Robot.intake.stop();
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
