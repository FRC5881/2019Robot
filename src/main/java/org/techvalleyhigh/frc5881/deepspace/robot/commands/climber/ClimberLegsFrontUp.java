package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Retracts front climber legs
 */
public class ClimberLegsFrontUp extends InstantCommand {
  private static Runnable runnable = () -> Robot.climber.frontUp();

  public ClimberLegsFrontUp() {
      super(Robot.climber, runnable);
  }
}