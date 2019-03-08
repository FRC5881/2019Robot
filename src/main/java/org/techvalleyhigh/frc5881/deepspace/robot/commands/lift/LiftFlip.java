package org.techvalleyhigh.frc5881.deepspace.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Changes the lifts mode accordingly to the expected game piece to be receiving
 */
public class LiftFlip extends Command {

  public LiftFlip() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Elevator flip initialized");
    Robot.elevator.elevatorFlip();
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return Robot.elevator.isElevatorSetpointReached() && Robot.elevator.isBarSetpointReached();
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("Elevator flip command ended");
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
