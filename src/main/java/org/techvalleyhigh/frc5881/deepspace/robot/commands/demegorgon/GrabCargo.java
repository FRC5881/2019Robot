package org.techvalleyhigh.frc5881.deepspace.robot.commands.demegorgon;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class GrabCargo extends InstantCommand {
  private static Runnable runnable = () -> Robot.demogorgon.grabCargo();

  public GrabCargo() {
    super(Robot.climber, runnable);
  }

}
