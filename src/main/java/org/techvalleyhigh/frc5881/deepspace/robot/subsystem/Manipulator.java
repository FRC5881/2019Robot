package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

public class Manipulator {

  private ManipulatorState mode = ManipulatorState.NONE;

  public enum ManipulatorState {
    NONE,
    HATCH,
    CARGO
  }

  public ManipulatorState getMode() {
    return mode;
  }

  public void setMode(ManipulatorState mode) {
    this.mode = mode;
  }
}
