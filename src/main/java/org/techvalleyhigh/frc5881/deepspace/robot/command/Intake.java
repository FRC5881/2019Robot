package org.techvalleyhigh.frc5881.deepspace.robot.command;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class Intake extends Command {
    public Intake() {
        requires(Robot.cargo);
    }

    /**
     * Called just before this Command runs the first time
     */
    @Override
    protected void initialize() {
        System.out.println("Intake Command initialized");
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {
        Robot.cargo.startIntake();
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
        Robot.cargo.stopMotor();
        System.out.println("Intake ended");
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
