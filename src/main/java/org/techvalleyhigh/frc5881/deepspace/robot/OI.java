package org.techvalleyhigh.frc5881.deepspace.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.climber.ClimberLegsBackToggle;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.climber.ClimberLegsFrontToggle;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator.LiftDown;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.elevator.LiftUp;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.groups.SwitchRobotMode;

/**
 * Controls operator interfaces, such as controllers (and a few buttons)
 */
public class OI {
    public final GenericHID driverController;

    public final JoystickButton driveControllerButtonA;
    public final JoystickButton driveControllerButtonB;
    public final JoystickButton driveControllerButtonX;
    public final JoystickButton driveControllerButtonY;
    public final JoystickButton driveControllerBackButton;
    public final JoystickButton driveControllerStartButton;
    public final JoystickButton driveControllerLeftBumper;
    public final JoystickButton driveControllerRightBumper;

    // Joysticks
    public static final int XBOX_LEFT_X_AXIS = 0;
    public static final int XBOX_LEFT_Y_AXIS = 1;
    public static final int XBOX_RIGHT_TRIGGER = 2;
    public static final int XBOX_LEFT_TRIGGER = 3;
    public static final int XBOX_RIGHT_X_AXIS = 4;
    public static final int XBOX_RIGHT_Y_AXIS = 5;

    public static final int BUTTON_A = 1;
    public static final int BUTTON_B = 2;
    public static final int BUTTON_X = 3;
    public static final int BUTTON_Y = 4;
    public static final int BUTTON_LEFT_BUMPER = 5;
    public static final int BUTTON_RIGHT_BUMPER = 6;
    public static final int BUTTON_BACK = 7;
    public static final int BUTTON_START = 8;


    public OI() {
        /*
        Drive is left thumb stick (forward and backward)
        Turning is on the right thumb stick (left and right)
        "Y" swaps the arm mode (DONE)
        Right bumper moves elevator up (DONE)
        Left bumper moves elevator down (DONE)
        "A" grabs hatch/ cargo
        "B" drops hatch/ cargo
        Back toggles front climber
        Start toggles back climber
        Right thumb stick drives climber (up and down)
        "X" aborts the docking
         */

        // Define controllers as joysticks
        driverController = new Joystick(0);

        // Assign EACH XBox controller button
        driveControllerButtonA = new JoystickButton(driverController, BUTTON_A);
        driveControllerButtonB = new JoystickButton(driverController, BUTTON_B);
        driveControllerButtonX = new JoystickButton(driverController, BUTTON_X);
        driveControllerButtonY = new JoystickButton(driverController, BUTTON_Y);
        driveControllerLeftBumper = new JoystickButton(driverController, BUTTON_LEFT_BUMPER);
        driveControllerRightBumper = new JoystickButton(driverController, BUTTON_RIGHT_BUMPER);
        driveControllerBackButton = new JoystickButton(driverController, BUTTON_BACK);
        driveControllerStartButton = new JoystickButton(driverController, BUTTON_START);

        // Turns the rumble off in case it was left on
        driverController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        driverController.setRumble(GenericHID.RumbleType.kRightRumble, 0);

        // When the "A" button is pressed grab hatch/ cargo
        // TODO: Finish upsideDown/ hatch and cargo grabber code

        // When the "B" button is pressed drop the hatch/ cargo
        // TODO: Finish upsideDown/ hatch and cargo dropping code

        // When the "X" button is pressed abort docking
        // TODO: Make a command to abort docking

        // When the "Y" button is pressed change the arm mode
        driveControllerButtonY.whenPressed(new SwitchRobotMode());

        // When the left/ right bumper is pressed lower or raise the lift
        driveControllerLeftBumper.whenPressed(new LiftDown());
        driveControllerRightBumper.whenPressed(new LiftUp());

        // When the "Back" button is pressed toggle front climber
        driveControllerBackButton.whenPressed(new ClimberLegsFrontToggle());

        // When the "Start" button is pressed toggle back climber
        driveControllerStartButton.whenPressed(new ClimberLegsBackToggle());
    }

    /**
     * Applies deadZone to input and scales output
     * @param input the input to apply a deadzone to
     * @param deadZone the deadzone to apply
     * @return 0 if absolute value of the input is less than dead zone or the signed input squared if otherwise
     */
    public static double applyDeadZone(double input, double deadZone) {
        double output;

        if (Math.abs(input) < deadZone) {
            output = 0;
        } else {
            // If we're above the joystick deadZone, square the inputs but keep the sign
            output = input < 0 ? -input * input : input * input;
        }

        return output;
    }
}
