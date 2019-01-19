package org.techvalleyhigh.frc5881.deepspace.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.DriveControl;

public class StopTipping extends Command {
  private double speed;

  public StopTipping() {
    requires(Robot.driveControl);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    // Drive in the direction of the tipping
    if (Robot.navX.getRawGyroY() > 0) {
      speed = DriveControl.TIPPING_SPEED;
    } else {
      speed = -DriveControl.TIPPING_SPEED;
    }

    System.out.println("StopTipping Command initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    Robot.driveControl.rawArcadeDrive(speed, 0);
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * Since this is a drive command we never want it to end
   */
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.navX.getRawGyroY()) < 10;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("StopTipping - Robot Saved");
  }

  /**
   * Called when another command which requires one or more of the same
   * subsystems is scheduled to run
   */
  @Override
  protected void interrupted() {
    end();
  }
}
