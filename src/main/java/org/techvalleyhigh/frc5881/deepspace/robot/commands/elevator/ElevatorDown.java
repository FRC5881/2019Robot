package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * When executed moves the elevator down to the next lowest level
 * If it is at the lowest possible level it will do nothing
 */
public class ElevatorDown extends Command {
  // TODO: Figure out what we actually want this number to be
  // errorMax is the maximum error that we want from anything
  private static final double errorMax = 1;

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
    Robot.elevator.elevatorDown();
  }

  @Override
  protected boolean isFinished() {
    if(Robot.elevator.setpointReached(Robot.elevator.overallTarget())){
      return true;
    } else {
      return false;
    }
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
