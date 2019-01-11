package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.TestCommand;

public class TestSubsystem extends Subsystem {
    // Initialize test motor as id 0
    private static WPI_TalonSRX testMotor = new WPI_TalonSRX(0);

    private TestCommand testCommand;

    public TestSubsystem() {
        // Run default subsystem constructor
        super();

        // Run initialization protocol
        init();
    }

    /**
     * Initialize the default command for a subsystem By default subsystems have no default command,
     * but if they do, the default command is set with this method. It is called on all Subsystems by
     * CommandBase in the users program after all the Subsystems are created.
     */
    @Override
    protected void initDefaultCommand() {
        // You're welcome to use this method. Remember each subsystem can only have 1 command require it
        testCommand = new TestCommand();
    }

    /**
     * Init subsystem variables
     */
    private void init() {
        // Set up motor controller
        testMotor.setName("Test", "Motor One");
    }

    /**
     * Drive test motor in percent mode
     * @param percent raw value to drive
     */
    public void rawDrive(double percent) {
        // Set bounds on inputs, motors in percent mode except inputs on [-1, 1]
        if (percent > 1) percent = 1;
        else if (percent < -1) percent = -1;

        // Drive the motor
        testMotor.set(percent);
    }

    /**
     * Drive test motor in percent mode based on Joystick input
     */
    public void driveJoystickInputs() {
        // Get axis reading from controller
        double input = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS);
        rawDrive(input);
    }
}
