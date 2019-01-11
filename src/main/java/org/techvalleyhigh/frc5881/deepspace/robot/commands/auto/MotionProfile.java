package org.techvalleyhigh.frc5881.deepspace.robot.commands.auto;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.subsystem.DriveControl;

/**
 * Have drive motors follow a path defined by a PathFinder path
 */
public class MotionProfile extends Command {
    private TrajectoryPoint[] leftPoints, rightPoints;

    private BufferedTrajectoryPointStream leftStream = new BufferedTrajectoryPointStream();
    private BufferedTrajectoryPointStream rightStream = new BufferedTrajectoryPointStream();

    public MotionProfile(TrajectoryPoint[] leftPoints, TrajectoryPoint[] rightPoints) {
        requires(Robot.driveControl);

        this.leftPoints = leftPoints;
        this.rightPoints = rightPoints;
    }

    /**
     * Called just before this Command runs the first time
     */
    @Override
    protected void initialize() {
        leftStream.Write(leftPoints);
        rightStream.Write(rightPoints);

        DriveControl.frontLeftMotor.startMotionProfile(leftStream, 50, ControlMode.MotionProfile);
        DriveControl.frontRightMotor.startMotionProfile(rightStream, 50, ControlMode.MotionProfile);

        System.out.println("MotionProfile initialized");
    }

    /**
     * Called repeatedly when this Command is scheduled to run
     */
    @Override
    protected void execute() {
        SmartDashboard.putNumber("Left Error", DriveControl.frontLeftMotor.getClosedLoopError());
        SmartDashboard.putNumber("Right Error", DriveControl.frontRightMotor.getClosedLoopError());
    }

    /**
     * Make this return true when this Command no longer needs to run execute()
     */
    @Override
    protected boolean isFinished() {
        return DriveControl.frontLeftMotor.isMotionProfileFinished()
                && DriveControl.frontRightMotor.isMotionProfileFinished();
    }

    /**
     * Called once after isFinished returns true OR the command is interrupted
     */
    @Override
    protected void end() {
        System.out.println("MotionProfile Finished");

        // Restart drive command
        Robot.driveCommand.start();
    }

    /**
     * Called when another command which requires one or more of the same
     * subsystems is scheduled to run
     */
    @Override
    protected void interrupted() {
        System.out.println("MotionProfile interrupted... That shouldn't happen");
        end();
    }
}
