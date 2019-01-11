package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control Hatch and Cargo manipulator
 */
public class Manipulator extends Subsystem {
    // Current state of the the Manipulator
    private ManipulatorMode mode = ManipulatorMode.NONE;

    /**
     * Enum for the different modes of the manipulator
     */
    public enum ManipulatorMode {
        NONE,
        HATCH,
        CARGO
    }

    /**
     * Initialize the default command for a subsystem By default subsystems have no default command,
     * but if they do, the default command is set with this method. It is called on all Subsystems by
     * CommandBase in the users program after all the Subsystems are created.
     */
    @Override
    protected void initDefaultCommand() {

    }

    public ManipulatorMode getMode() {
        return mode;
    }

    public void setMode(ManipulatorMode mode) {
        this.mode = mode;
    }
}
