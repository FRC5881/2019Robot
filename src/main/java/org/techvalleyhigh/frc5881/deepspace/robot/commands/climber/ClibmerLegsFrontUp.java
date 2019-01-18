package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.Climber;

/**
 * Extends front clibmer legs
 */
public class ClibmerLegsFrontUp extends InstantCommand {
  private static Runnable runnable = () -> Robot.climber.frontUp();

  public ClibmerLegsFrontUp() {
      super(Robot.climber, runnable);
  }
}