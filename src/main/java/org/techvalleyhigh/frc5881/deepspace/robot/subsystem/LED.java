package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LED extends Subsystem {

  /**
   * Variable holds the current state of the elevator
   */
  private LEDState state = LEDState.DISABLED;

  /**
   * Our alliance color
   */
  private DriverStation.Alliance color;


  enum LEDState {
    DISABLED(5),
    TELEOP(10),
    LIFTUP(20),
    LIFTDOWN(30),
    MANIPULATING(40);

    private double value;

    LEDState(double value) {
      this.value = value;
    }

    public double getValue() {
      return value;
    }
  }

  public LED() {
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
  }

  /**
   * Init subsystem variables
   */
  private void init() {
    color = DriverStation.getInstance().getAlliance();
  }

  public void displayColor() {

  }
}
