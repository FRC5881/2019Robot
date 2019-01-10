package org.techvalleyhigh.frc5881.deepspace.robot.commands.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.Elevator;

public class ElevatorSave extends Command {

  public ElevatorSave() {
  }

  protected void executed(){
    Robot.elevator.setSetpoint(Elevator.bottomTicks);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
