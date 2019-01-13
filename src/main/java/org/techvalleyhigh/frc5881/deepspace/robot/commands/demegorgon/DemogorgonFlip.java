package org.techvalleyhigh.frc5881.deepspace.robot.commands.demegorgon;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class DemogorgonFlip extends InstantCommand {
  private static Runnable runnable = () -> Robot.demogorgon.flip();

  public DemogorgonFlip() {
    super(Robot.climber, runnable);
  }

}
