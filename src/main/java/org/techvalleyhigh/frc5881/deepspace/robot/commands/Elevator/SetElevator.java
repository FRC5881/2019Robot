package org.techvalleyhigh.frc5881.deepspace.robot.commands.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class SetElevator extends Command {

  /**
   * Is the double for the height of the elevator to go to.
   */
  private double setpoint;

  @Override
  protected boolean isFinished() {
    return false;
  }

  protected void executed(){
    Robot.elevator.setSetpoint(setpoint);
  }
}
