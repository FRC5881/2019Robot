package org.techvalleyhigh.frc5881.deepspace.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * When executed moves the lift down to the next lowest level
 * If it is at the lowest possible level it will do nothing
 */
public class LiftDown extends Command {
  public LiftDown() {
    requires(Robot.elevator);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Elevator down initialized");
    Robot.elevator.elevatorDown();
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
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
