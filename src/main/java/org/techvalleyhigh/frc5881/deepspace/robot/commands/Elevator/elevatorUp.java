package org.techvalleyhigh.frc5881.deepspace.robot.commands.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class elevatorUp extends Command {
    public elevatorUp(){
        requires(Robot.elevator);
    }

    protected void executed(){
        Robot.elevator.elevatorUp();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
