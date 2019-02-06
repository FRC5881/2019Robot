package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Demogorgon extends Subsystem {
  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {

  }

  public static DoubleSolenoid mainSolenoid = new DoubleSolenoid(20, 6, 7);

  public Demogorgon() {
    super();

    init();
  }

  /**
   * Name: Main
   * Subsystem: Demogorgon
   */
  public void init(){
    mainSolenoid.setName ("Demogorgon", "Main");
    LiveWindow.add(mainSolenoid);
  }

  public void dropHatch(){
  }

  public void grabHatch(){
  }

  public void flip(){
  }
}
