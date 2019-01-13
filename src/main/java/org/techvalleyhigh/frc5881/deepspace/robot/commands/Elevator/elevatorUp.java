package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ElevatorUp extends Command {
  public ElevatorUp() {
    requires(Robot.elevator);
  }

  protected void executed() {
    Robot.elevator.elevatorUp();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
