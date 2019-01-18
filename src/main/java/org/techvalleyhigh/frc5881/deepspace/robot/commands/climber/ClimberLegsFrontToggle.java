package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * if front legs are down make them up
 * if they are up make them down
 */
public class ClimberLegsFrontToggle extends InstantCommand {
  public static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Robot.climber.frontToggle();
    }
  };
  public ClimberLegsFrontToggle() {
    super(Robot.climber, runnable);
  }
}


