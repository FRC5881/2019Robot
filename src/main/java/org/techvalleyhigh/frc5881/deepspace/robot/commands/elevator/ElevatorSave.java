package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

import static org.techvalleyhigh.frc5881.deepspace.robot.subsystem.Elevator.none;

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
    System.out.println("Elevator save initialized");
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
    System.out.println("Elevator save command finished");
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
