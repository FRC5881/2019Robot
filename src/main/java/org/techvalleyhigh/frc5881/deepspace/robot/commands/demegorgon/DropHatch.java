package org.techvalleyhigh.frc5881.deepspace.robot.commands.demegorgon;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * its runnable
 */
public class DropHatch extends InstantCommand {
  private static Runnable runnable = () -> Robot.demogorgon.dropHatch();

  public DropHatch() {
    super(Robot.climber, runnable);
  }

}
