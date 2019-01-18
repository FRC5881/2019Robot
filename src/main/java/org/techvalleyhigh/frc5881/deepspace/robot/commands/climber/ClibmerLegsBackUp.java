package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.Climber;

/**
 * Extends back clibmer legs
 */
public class ClibmerLegsBackUp extends InstantCommand {
  private static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Robot.climber.backUp();
    }
  };

  public ClibmerLegsBackUp() {
    super(Robot.climber, runnable);
  }
}
