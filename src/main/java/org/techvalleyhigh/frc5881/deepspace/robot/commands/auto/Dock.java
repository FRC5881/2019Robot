package org.techvalleyhigh.frc5881.deepspace.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.techvalleyhigh.frc5881.deepspace.robot.utils.VisionUtil;

public class Dock extends CommandGroup {
    /**
     * Creates a new {@link CommandGroup CommandGroup}. The name of this command will be set to its
     * class name.
     */
    public Dock() {
        double turnAngle = VisionUtil.getSeparationAngle();
        addSequential(new Turn(turnAngle));
        // Turn to face tape

        // Run motion profile
    }
}
