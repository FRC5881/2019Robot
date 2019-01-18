package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Extends front climber legs
 */
public class ClimberLegsFrontDown extends InstantCommand {
  private static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Robot.climber.frontDown();
    }
  };

  public ClimberLegsFrontDown() {
    super(Robot.climber, runnable);
  }
}