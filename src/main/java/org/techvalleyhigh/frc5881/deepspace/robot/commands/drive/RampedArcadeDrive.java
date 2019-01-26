package org.techvalleyhigh.frc5881.deepspace.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class RampedArcadeDrive extends Command {
  public RampedArcadeDrive() {
    requires(Robot.driveControl);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    System.out.println("Ramped arcade drive initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {
    Robot.driveControl.rawArcadeDrive(Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS), Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_X_AXIS));
  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   * Since this is a drive command we never want it to end
   */
  @Override
  protected boolean isFinished() {
    return false;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("Ramped arcade drive command ended... That shouldn't happen");
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
