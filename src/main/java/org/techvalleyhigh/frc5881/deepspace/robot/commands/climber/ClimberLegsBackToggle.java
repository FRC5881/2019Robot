package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * if back legs are up make them down
 * if back legs are down make them up
 */
public class ClimberLegsBackToggle extends InstantCommand {
  private static Runnable runnable = () -> Robot.climber.backToggle();

  public ClimberLegsBackToggle() {
    super(Robot.climber, runnable);
  }
}


