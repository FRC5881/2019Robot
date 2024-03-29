package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * "Saves" the bot from tipping by lowering the elevator when we are tipping and not climbing
 */
public class ElevatorSave extends Command {
  public ElevatorSave() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("elevator save initialized");
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
   * Since this is just an elevator command it should finish eventually
   */
  @Override
  protected boolean isFinished() {
    return Robot.elevator.isSetpointReached();
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("elevator save command finished");
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
