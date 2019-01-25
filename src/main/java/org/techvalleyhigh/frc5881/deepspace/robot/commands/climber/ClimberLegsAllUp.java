package org.techvalleyhigh.frc5881.deepspace.robot.commands.climber;

import edu.wpi.first.wpilibj.command.InstantCommand;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Brings all climber legs up
 */
public class ClimberLegsAllUp extends InstantCommand {
    private static Runnable runnable = () -> {
        Robot.climber.frontUp();
        Robot.climber.backUp();
    };

    public ClimberLegsAllUp() {
        super(Robot.climber, runnable);
    }
}
