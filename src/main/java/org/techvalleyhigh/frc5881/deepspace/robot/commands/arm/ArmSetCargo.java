package org.techvalleyhigh.frc5881.deepspace.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ArmSetCargo extends InstantCommand {
  private static Runnable runnable = () -> Robot.arm.setToCargoTicks();

  public ArmSetCargo() {
    super(Robot.arm, runnable);
  }
}
