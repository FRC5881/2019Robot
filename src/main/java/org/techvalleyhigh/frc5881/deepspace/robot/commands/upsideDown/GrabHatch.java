package org.techvalleyhigh.frc5881.deepspace.robot.commands.upsideDown;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Grabs hatch panels.
 */
public class GrabHatch extends InstantCommand {
  private static Runnable runnable = () -> Robot.upsideDown.grabHatch();

  public GrabHatch() {
    super(Robot.climber, runnable);
  }

}
