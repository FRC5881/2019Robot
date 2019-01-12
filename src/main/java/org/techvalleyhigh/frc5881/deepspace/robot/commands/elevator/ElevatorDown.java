package org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ElevatorDown extends Command {
  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void execute() {
    Robot.elevator.elevatorDown();
  }
}
