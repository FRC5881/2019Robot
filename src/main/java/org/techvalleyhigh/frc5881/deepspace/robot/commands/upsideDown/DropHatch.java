package org.techvalleyhigh.frc5881.deepspace.robot.commands.upsideDown;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * its runnable
 */
public class DropHatch extends InstantCommand {
  private static Runnable runnable = () -> Robot.upsideDown.dropHatch();

  public DropHatch() {
    super(Robot.climber, runnable);
  }

}
