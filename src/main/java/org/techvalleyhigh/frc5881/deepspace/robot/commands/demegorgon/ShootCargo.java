package org.techvalleyhigh.frc5881.deepspace.robot.commands.demegorgon;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * its runnable
 */
public class ShootCargo extends InstantCommand {
  private static Runnable runnable = () -> Robot.demogorgon.shootCargo();

  public ShootCargo() {
    super(Robot.climber, runnable);
  }

}
