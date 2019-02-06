package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

/**
 * Contains methods to get the error of the motors, set the PIDs and even move the elevator and lift!
 */
public class Elevator extends Subsystem {

    public boolean isFired = false;

    public ElevatorState elevatorState = ElevatorState.HIGH_HATCH;

    // TODO: Change the "deviceNumber" to whatever the actual number on the talon is.
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(5);
    //  ||                                          ||
    //  \/ is the talon for the four bar lift motor \/
    private WPI_TalonSRX liftMasterMotor = new WPI_TalonSRX(6);

  /*
    The order of heights is: (greatest to least)

    1. High cargo

    2. High hatch

    3. Middle cargo

    4. Middle hatch

    5. Low cargo

    6. Low hatch
     */

  /**
   * Is the state of the elevator
   */
  public enum ElevatorState {
    FLOOR,

    LOW_HATCH,

    LOW_CARGO,

    MIDDLE_HATCH,

    MIDDLE_CARGO,

    HIGH_HATCH,

    HIGH_CARGO
    }

  // TODO: We need to figure out what the actual heights of these things will be.
  /**
   * For all of the following double[]'s the first double value is the required height for the elevator to move
   * and the second value is the height that the lift is required to move.
   */
  public static final double[] Floor = {0, 0};

  public static final double[] Low_Hatch = {500, 500};

  public static final double[] Low_Cargo = {750 , 50};

  public static final double[] Mid_Hatch = {1000, 250};

  public static final double[] Mid_Cargo = {1250, 250};

  public static final double[] High_Hatch = {1750, 500};

  public static final double[] High_Cargo = {2000, 200};

  public static final double[] Top = {3500, 500};

 /**
  * Does the normal stuff but also adds the PID values to Smart Dashboard.
  */
  public Elevator() {
        super();

        // Put numbers on SmartDashboard
        SmartDashboard.putNumber("elevator kP", 2);
        SmartDashboard.putNumber("elevator kI", 0);
        SmartDashboard.putNumber("elevator kD", 20);
        SmartDashboard.putNumber("elevator kF", 0.076);
        SmartDashboard.putNumber("lift kP", 2);
        SmartDashboard.putNumber("lift kI", 0);
        SmartDashboard.putNumber("lift kD", 20);
        SmartDashboard.putNumber("lift kF", 0.076);

        init();
    }

  /**
   * Adds the encoder to the motor/ Talon
   * Also sets the PID values
   */
  private void init(){
      liftMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
      elevatorMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

      elevatorMasterMotor.config_kP(0, getElevator_kP(), 10);
      elevatorMasterMotor.config_kI(0, getElevator_kI(), 10);
      elevatorMasterMotor.config_kD(0, getElevator_kD(), 10);
      elevatorMasterMotor.config_kF(0, getElevator_kF(), 10);

      liftMasterMotor.config_kP(0, getLift_kP(), 10);
      liftMasterMotor.config_kI(0, getLift_kI(), 10);
      liftMasterMotor.config_kD(0, getLift_kD(), 10);
      liftMasterMotor.config_kF(0, getLift_kF(), 10);
    }

    @Override
    protected void initDefaultCommand() {

    }

  /**
   * Moves the elevator up to the next possible level.
   */
  public void elevatorUp(){
    // Checks if the arm mode is "hatch"
    if(true) {
      // Checks to see if the elevator state is "Floor"
        if(ElevatorState.FLOOR.equals(elevatorState)){
          setElevator(Low_Hatch);
        } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {
          setElevator(Mid_Hatch);
          // Checks if the elevator state is "middle hatch"
        } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){
          setElevator(High_Hatch);
        }
        // Checks if the arm mode is "cargo"
      } else if(false){
        // If the elevator state is "low cargo" then proceed
        if(ElevatorState.LOW_CARGO.equals(elevatorState)){
          setElevator(Mid_Cargo);
          // Checks if the elevator state is "middle cargo"
        }  else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
          setElevator(High_Cargo);
        }
      }
    }

  /**
   * Moves the elevator down to the next possible state.
   */
  public void elevatorDown(){
    // Check if the arm mode is "hatch"
    if (true) {
        // If the elevator's height is "high hatch" then proceed
      if (ElevatorState.HIGH_HATCH.equals(elevatorState)) {
        setElevator(Mid_Hatch);
        // Check if the elevator state is middle hatch
      } else if (ElevatorState.MIDDLE_HATCH.equals(elevatorState)) {
        setElevator(Low_Hatch);
        // Checks if the elevator's state is "low hatch"
      } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {
        setElevator(Floor);
      }
      // Checks if the arm mode is "cargo"
    } else if (false){
        // Checks if the elevator state is "high cargo"
      if(ElevatorState.HIGH_CARGO.equals(elevatorState)) {
        setElevator(Mid_Cargo);
        // Checks if the elevator state is "middle cargo"
      } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
        setElevator(Low_Cargo);
        // Checks if elevator state is "low cargo"
      } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){
        setElevator(Floor);
      }
    }
  }

  /**
   * Sets the elevator and lift height to the lowest setting as to prevent a high center of mass
   */
  public void saveElevator(){
    setElevator(Floor);
  }

  /**
   * Gets the total target height of the elevator and lift together
   * @return Returns the sum of the elevator target height and the lift target height to get the total desired height
   */
  public double overallTarget(){
    return elevatorTarget() + liftTarget();
  }

  /**
   * Checks to see if the elevator and lift are at the desired location
   * @return Returns true if it has reached destination, returns false if it has not reached destination
   */
  public boolean isSetpointReached(){
    double goal = getSetpoint();
    double goalError = getSetpoint() + 5;
    double goalError2 = getSetpoint() - 5;
    if(overallTarget() == goal && overallTarget() <= goalError && overallTarget() >= goalError2){
      return true;
    } else {
      return false;
    }
  }

  /**
   * If elevatorFlip is equal to one thing than set elevator to that height
   */
  public void elevatorFlip(){
    switch (elevatorState) {
      case FLOOR:
        setElevator(ElevatorState.FLOOR);
        isFired = true;
        break;
      case LOW_HATCH:
        setElevator(ElevatorState.LOW_HATCH);
        isFired = true;
        break;
      case LOW_CARGO:
        setElevator(ElevatorState.LOW_CARGO);
        isFired = true;
        break;
      case MIDDLE_HATCH:
        setElevator(ElevatorState.MIDDLE_HATCH);
        isFired = true;
        break;
      case MIDDLE_CARGO:
        setElevator(ElevatorState.MIDDLE_CARGO);
        isFired = true;
        break;
      case HIGH_HATCH:
        setElevator(ElevatorState.HIGH_HATCH);
        isFired = true;
        break;
      case HIGH_CARGO:
        setElevator(ElevatorState.HIGH_CARGO);
        isFired = true;
        break;
    }
  }

  /**
   * Checks to see if the amount of error in the elevator motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  public boolean isElevatorAllowableError(){
    double error = 5;
    if(getElevatorError() <= error && getElevatorError() >= -error){
      return true;
    } else {
      return false;
    }
  }

  /**
   * Gets the target height of the elevator
   * @return Returns the desired height of the elevator
   */
  public double elevatorTarget(){
    return elevatorMasterMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the target height of the lift
   * @return Returns the desired height of the lift
   */
  public double liftTarget(){
    return liftMasterMotor.getClosedLoopTarget(0);
  }

  /**
   * Checks to see if te amount of error in the lift motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  public boolean isLiftAllowableError(){
    double error = 5;
    return getLiftError() <= error && getLiftError() >= -error;
  }

  /**
   * Tells the elevator to go to the specified height, also sets elevatorState
   * @param state Is the state of which the elevator is wanted to go to
   */
  public void setElevator(ElevatorState state) {
    switch (state) {
      case FLOOR:
        setElevator(Floor);
        elevatorState = ElevatorState.FLOOR;
        break;
      case LOW_HATCH:
        setElevator(Low_Hatch);
        elevatorState = ElevatorState.LOW_HATCH;
        break;
      case LOW_CARGO:
        setElevator(Low_Cargo);
        elevatorState = ElevatorState.LOW_CARGO;
        break;
      case MIDDLE_HATCH:
        setElevator(Mid_Hatch);
        elevatorState = ElevatorState.MIDDLE_HATCH;
        break;
      case MIDDLE_CARGO:
        setElevator(Mid_Cargo);
        elevatorState = ElevatorState.MIDDLE_CARGO;
        break;
      case HIGH_HATCH:
        setElevator(High_Hatch);
        elevatorState = ElevatorState.HIGH_HATCH;
        break;
      case HIGH_CARGO:
        setElevator(High_Cargo);
        elevatorState = ElevatorState.HIGH_CARGO;
        break;
    }
  }

  /**
   * "Runs" setSetpointElevator and setSetpointLift with the appropriate parameters.
   * @param setpoints double[2] both
   */
  public void setElevator(double[] setpoints) {
    setSetpointLift(setpoints[0]);
    setSetpointElevator(setpoints[1]);
  }

  /**
   * Sets the height of the elevator
   * Will not move the elevator if you want to move it below 0 ticks or above the Top ticks number
   * @param setpoint is the height (in ticks) of which you want the elevator to go to
   */
  private void setSetpointElevator(double setpoint) {
        // Checks to see if the elevator is within safe operating heights
      if(getSetpointElevator() >= Floor[1] && getSetpointElevator() <= Top[1]) {
        elevatorMasterMotor.set(ControlMode.Position, setpoint);
      }
    }

  /**
   * Sets the height of the lift
   * @param setpoint Is the height to which is need to get to
   */
  private void setSetpointLift(double setpoint){
      // Checks to see if the lift is within safe operation heights
    if(getSetpointLift() >= Floor[2] && getSetpointLift() <= Top[2]) {
      liftMasterMotor.set(ControlMode.Position, setpoint);
    }
  }

  /**
   * Gets the error of the elevator motor
   * @return Returns the error of the elevator motor
   */
  public double getElevatorError(){
    return elevatorMasterMotor.getClosedLoopError(2);
  }

  /**
   * Gets the error of the lift motor
   * @return Returns the error of the lift motor
   */
  public double getLiftError(){
    return liftMasterMotor.getClosedLoopError(3);
  }

  /**
   * Gets the total height of the elevator and lift together
   * @return /\
   *         ||
   */
  public double getSetpoint(){
    return getSetpointElevator() + getSetpointLift();
  }

  /**
   * Gets the value of Setpoint
   * @return returns the value of Setpoint
   */
  public double getSetpointElevator(){
      return elevatorMasterMotor.getSelectedSensorPosition();
    }
  /**
   * Gets the value of Setpoint
   * @return returns the value of Setpoint
   */
  public double getSetpointLift(){
    return liftMasterMotor.getSelectedSensorPosition();
  }

  /**
   * Gets the value of Elevator_kP from Smart Dashboard
   * @return Returns the value of Elevator_kP
   */
  private double getElevator_kP() {
      return SmartDashboard.getNumber("elevator kP", 2.0);
    }

  /**
   * Gets the value of Elevator_kI from Smart Dashboard
   * @return Returns the value of Elevator_kI
   */
  private double getElevator_kI() {
      return SmartDashboard.getNumber("elevator kI", 0);
    }

  /**
   * Gets the value of Elevator_kD from Smart Dashboard
   * @return Returns the value of Elevator_kD
   */
  private double getElevator_kD(){
      return SmartDashboard.getNumber("elevator kD", 20);
    }

  /**
   * Gets the value of Elevator_kF from Smart Dashboard
   * @return Returns the value of Elevator_kF
   */
  private double getElevator_kF() {
      return SmartDashboard.getNumber("elevator kF", 0.076);
    }

  /**
   * Gets the value of Lift_kP from Smart Dashboard
   * @return Returns the value of Lift_kP
   */
  private double getLift_kP() {
     return SmartDashboard.getNumber("lift kP", 2.0);
    }

  /**
   * Gets the value of Lift_kI from Smart Dashboard
   * @return Returns the value of Lift_kI
   */
  private double getLift_kI() {
      return SmartDashboard.getNumber("lift kI", 0);
    }

  /**
   * Gets the value of Lift_kD from Smart Dashboard
   * @return Returns the value of Lift_kD
   */
  private double getLift_kD(){
     return SmartDashboard.getNumber("lift kD", 20);
    }

  /**
   * Gets the value of Lift_kF from Smart Dashboard
   * @return Returns the value of Lift_kF
   */
  private double getLift_kF() {
      return SmartDashboard.getNumber("lift kF", 0.076);
    }
  /**
   * Tells you how many ticks you need to turn a motor to go a certain amount of inches.
   * @param inches is the amount of inches you want to convert to ticks.
   * @return Returns the amount of ticks it takes to go the certain amount of inches.
   */
  private double getInchesToTicks(double inches){
      // The amount of ticks that the given amount of inches will be
      double ticks;
      // Is the amount of inches per amount of ticks per rotation
      double ticksPerRotation = 1000;
      double inchesMovedPerRotation = 0.5;

      // The part in parenthesis (hopefully) calculates how many rotations you would need to get to the next "closest" number
      // Then it is multiplied by the amount of ticks per rotation as to make it into the amount of ticks.
      ticks = (inches / inchesMovedPerRotation) * ticksPerRotation;

      return ticks;
  }
}
