package org.techvalleyhigh.frc5881.deepspace.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.lift.LiftFlip;

/**
 * Will change the mode of the lift.
 */
public class SwitchRobotMode extends CommandGroup {
  public SwitchRobotMode() {
    addParallel(new LiftFlip());
  }
}