package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LED extends Subsystem {

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

  public void ledDocking(){
    // TODO: Code this.
  }

  /**
   * Init subsystem variables
   */
  private void init() {
  }

}
