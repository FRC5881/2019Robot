package org.techvalleyhigh.frc5881.deepspace.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.Arm;

/**
 * Sets arm to be positioned to pick up cargo
 */
public class ArmSetCargo extends Command {
  public ArmSetCargo() {
    requires(Robot.arm);
  }

  /**
   * Called just before this Command runs the first time
   */
  @Override
  protected void initialize() {
    Robot.arm.setToCargoTicks();
    System.out.println("ArmSetCargo Command initialized");
  }

  /**
   * Called repeatedly when this Command is scheduled to run
   */
  @Override
  protected void execute() {  }

  /**
   * Make this return true when this Command no longer needs to run execute()
   */
  @Override
  protected boolean isFinished() {
    return Robot.arm.getError() < Arm.MAX_ERROR;
  }

  /**
   * Called once after isFinished returns true OR the command is interrupted
   */
  @Override
  protected void end() {
    System.out.println("ArmSetCargo ended");
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
