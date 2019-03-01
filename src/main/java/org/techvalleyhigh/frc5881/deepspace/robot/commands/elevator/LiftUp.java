package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Makes the elevator go up
 */
public class LiftUp extends Command {
  public LiftUp() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Elevator up initialized");
    Robot.elevator.elevatorUp();
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * Since this is a drive command we never want it to end
   */
  @Override
  protected boolean isFinished() {
    return Robot.elevator.isElevatorSetpointReached() == Robot.elevator.isBarSetpointReached();
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("Elevator up command ended");
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
