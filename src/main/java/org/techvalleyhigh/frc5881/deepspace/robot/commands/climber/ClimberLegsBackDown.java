package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Extends back climber legs
 */
public class ClimberLegsBackDown extends InstantCommand {
  private static Runnable runnable = () -> Robot.climber.backDown();

  public ClimberLegsBackDown() {
    super(Robot.climber, runnable);
  }
}
