package org.techvalleyhigh.frc5881.deepspace.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ArmSetHatch extends InstantCommand {
  private static Runnable runnable = () -> Robot.arm.setToHatchTicks();

  public ArmSetHatch() {
    super(Robot.arm, runnable);
  }

}
