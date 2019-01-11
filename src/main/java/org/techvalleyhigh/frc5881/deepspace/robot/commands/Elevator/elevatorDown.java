package org.techvalleyhigh.frc5881.deepspace.robot.commands.Elevator;

import edu.wpi.first.wpilibj.command.Command;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class elevatorDown extends Command {
    public elevatorDown(){
    requires(Robot.elevator);
}

    protected void executed(){
        Robot.elevator.elevatorDown();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
