package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Houses a majority of the code for "Upside Down" (a.k.a the hatch grabber)
 */
public class UpsideDown extends Subsystem {
  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {

  }

  public static DoubleSolenoid mainSolenoid = new DoubleSolenoid(20, 0, 1);


  public UpsideDown() {
    super();

    init();
  }

  /**
   * Name: Main
   * Subsystem: UpsideDown
   */
  public void init(){
    mainSolenoid.setName ("UpsideDown", "Main");
    LiveWindow.add(mainSolenoid);
  }

  /**
   * Drops the hatch
   */
  public void dropHatch(){
    mainSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  /**
   * Grabs the hatch
   */
  public void grabHatch(){
    mainSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  /**
   * Changes whether or not upside down is holding a hatch or not.
   */
  public void toggle(){
    switch(mainSolenoid.get()){
      case kOff:
        break;
      case kForward:
        mainSolenoid.set(DoubleSolenoid.Value.kReverse);
        break;
      case kReverse:
        mainSolenoid.set(DoubleSolenoid.Value.kForward);
        break;
    }
  }
}
