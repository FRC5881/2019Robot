package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.techvalleyhigh.frc5881.deepspace.robot.commands.upsideDown.DropHatch;

/**
 * Houses a majority of the code for "Upside Down" (a.k.a the hatch grabber)
 */
public class UpsideDown extends Subsystem {


  public static DoubleSolenoid mainSolenoid = new DoubleSolenoid(20, 0, 1);
  public UpsideDownMode state = UpsideDownMode.ENGAGED;


  public UpsideDown() {
    super();

    init();
  }

  public enum UpsideDownMode {
    ENGAGED,
    DISENGAGED
  }

  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {
    new DropHatch().start();
  }

  /**
   * Name: Main
   * Subsystem: UpsideDown
   */
  public void init() {
    mainSolenoid.setName("UpsideDown", "Main");
    LiveWindow.add(mainSolenoid);
  }

  /**
   * Drops the hatch
   */
  public void dropHatch() {
    mainSolenoid.set(DoubleSolenoid.Value.kReverse);
    setState(UpsideDownMode.DISENGAGED);
  }

  /**
   * Grabs the hatch
   */
  public void grabHatch(){
    mainSolenoid.set(DoubleSolenoid.Value.kForward);

    setState(UpsideDownMode.ENGAGED);
  }

  /**
   * Changes whether or not upside down is holding a hatch or not.
   */
  public void toggle(){
    if (getState().equals(UpsideDownMode.DISENGAGED)) {
      grabHatch();
    }else if (getState().equals(UpsideDownMode.ENGAGED)) {
      dropHatch();
    }else{
      System.out.println("Upside Down is off");
    }
  }

  public UpsideDownMode getState() {
    return state;
  }

  public void setState(UpsideDownMode state) {
    this.state = state;
  }
}
