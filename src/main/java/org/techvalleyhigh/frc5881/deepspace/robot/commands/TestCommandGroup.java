package org.techvalleyhigh.frc5881.deepspace.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Command;

public class TestCommandGroup extends CommandGroup {
    /**
     * Creates a new {@link CommandGroup CommandGroup}. The name of this command will be set to its
     * class name.
     */
    public TestCommandGroup() {
        super();
    }

    /**
     * Returns true if all the {@link Command Commands} in this group have been started and have
     * finished.
     *
     * <p> Teams may override this method, although they should probably reference super.isFinished()
     * if they do. </p>
     *
     * @return whether this {@link CommandGroup} is finished
     */
    @Override
    protected boolean isFinished() {
        return super.isFinished();
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
