package org.techvalleyhigh.frc5881.deepspace.robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class GrabCargo extends Command {

    public GrabCargo() {
      requires(Robot.intake);
    }

    /**
     * Called just before this Command runs the first time
     */
    @Override
    protected void initialize() {
      System.out.println("GrabCargo Command initialized");
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {
      Robot.intake.spinInInput();
    }

    /**
     * Make this return true when this Command no longer needs to run execute()
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
      System.out.println("GrabCargo ended");
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
