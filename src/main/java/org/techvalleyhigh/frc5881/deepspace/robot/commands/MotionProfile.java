package org.techvalleyhigh.frc5881.deepspace.robot.commands;

import com.ctre.phoenix.motion.TrajectoryPoint;
import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.DriveControl;

/**
 * Have drive motors follow a path defined by a PathFinder path
 */
public class MotionProfile extends Command {
    private TrajectoryPoint[] trajectoryPoints;

    public MotionProfile(TrajectoryPoint[] trajectoryPoints) {
        // TODO: uncomment eventually
        requires(Robot.driveControl);

        this.trajectoryPoints = trajectoryPoints;
    }

    /**
     * Called just before this Command runs the first time
     */
    @Override
    protected void initialize() {
        System.out.println("MotionProfile initialized");
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {
        DriveControl.left.pushMotionProfileTrajectory()
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
        System.out.println("TestCommand command ended... That shouldn't happen");
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
