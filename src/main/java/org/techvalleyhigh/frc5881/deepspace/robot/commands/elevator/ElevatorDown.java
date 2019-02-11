package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * When executed moves the elevator down to the next lowest level
 * If it is at the lowest possible level it will do nothing
 */
public class ElevatorDown extends Command {
  private boolean isFirstRun = true;
  public ElevatorDown() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Elevator down initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    if(isFirstRun) {
      Robot.elevator.elevatorDown();
      isFirstRun = false;
    }
  }

  @Override
  protected boolean isFinished() {
    return Robot.elevator.isSetpointReached();
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    isFirstRun = true;
    System.out.println("Elevator down command ended");
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
