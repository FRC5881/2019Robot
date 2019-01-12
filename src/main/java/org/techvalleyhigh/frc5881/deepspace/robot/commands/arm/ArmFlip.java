package org.techvalleyhigh.frc5881.deepspace.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ArmFlip extends InstantCommand {
  private static Runnable runnable = () -> Robot.arm.flip();

  public ArmFlip() {
    super(Robot.arm, runnable);
  }

}


