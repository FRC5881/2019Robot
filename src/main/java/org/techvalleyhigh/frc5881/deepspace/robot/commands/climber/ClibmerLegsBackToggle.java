package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class ClibmerLegsBackToggle extends InstantCommand {
  public static Runnable runnable = new Runnable() {
    @Override
    public void run() {
      Robot.climber.frontToggle();
    }
  };
  public ClibmerLegsBackToggle() {
    super(Robot.climber, runnable);
  }
}


