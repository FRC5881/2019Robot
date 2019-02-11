package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Makes the elevator go up
 */
public class LiftUp extends Command {
  private boolean isFirstRun;
  public LiftUp() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    isFirstRun = true;
    System.out.println("Elevator up initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    if(isFirstRun) {
      Robot.elevator.elevatorUp();
      isFirstRun = false;
    }
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * Since this is a drive command we never want it to end
   */
  @Override
  protected boolean isFinished() {
    return Robot.elevator.isElevatorSetpointReached();
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("Elevator up command ended");
    isFirstRun = true;
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
