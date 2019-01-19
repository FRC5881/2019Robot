package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Retracts back climber legs
 */
public class ClimberLegsBackUp extends InstantCommand {
  private static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Robot.climber.backUp();
    }
  };

  public ClimberLegsBackUp() {
    super(Robot.climber, runnable);
  }
}
