package org.techvalleyhigh.frc5881.deepspace.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * "Saves" the bot from tipping by lowering the lift when we are tipping and not climbing
 */
public class LiftSave extends Command {
  public LiftSave() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("lift save initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    Robot.elevator.saveElevator();
  }

  /**
   * Make this return true when this Command has done it's job
   * Since this is just an lift command it should finish eventually
   */
  @Override
  protected boolean isFinished() {
    return true;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("lift save command finished");
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
