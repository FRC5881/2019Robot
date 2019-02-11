package org.techvalleyhigh.frc5881.deepspace.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator.ElevatorFlip;

/**
 * Will change the mode of the arm.
 */
public class SwitchRobotMode extends CommandGroup {
  public SwitchRobotMode() {
    addParallel(new ElevatorFlip());
  }
}