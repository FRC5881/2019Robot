package org.techvalleyhigh.frc5881.deepspace.robot.commands.upsideDown;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Changes the "state" of Upside Down.
 */
public class UpsideDownFlip extends InstantCommand {
  private static Runnable runnable = () -> Robot.upsideDown.toggle();

  public UpsideDownFlip() {
    super(Robot.climber, runnable);
  }

}
