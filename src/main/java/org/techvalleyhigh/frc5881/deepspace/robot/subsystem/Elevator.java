package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Arrays;

/**
 * Contains methods to get the error of the motors, set the PIDs and even move the elevator and lift!
 */
public class Elevator extends Subsystem {
  private ElevatorState elevatorState = ElevatorState.FLOOR;
  private ElevatorMode elevatorMode = ElevatorMode.HATCH;

  // TODO: Change the "deviceNumber" to whatever the actual number on the talon is.
  private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(5);
  //  \/ is the talon for the four bar lift motor \/
  private WPI_TalonSRX liftMasterMotor = new WPI_TalonSRX(3);

  /**
   * Is the mode of the elevator
   */
  public enum ElevatorMode {
    HATCH,
    CARGO
  }

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
    HIGH_CARGO,
    TOP
  }

  // TODO: We need to figure out what the actual heights of these things will be.
  /**
   * For all of the following double[]'s the first double value is the required height for the elevator to move
   * and the second value is the height that the lift is required to move.
   */
  private static final double[] FLOOR = {0, 0};
  private static final double[] LOW_HATCH = {2000, 1000};
  private static final double[] LOW_CARGO = {4000 , 2000};
  private static final double[] MIDDLE_HATCH = {6000, 3000};
  private static final double[] MIDDLE_CARGO = {8000, 4000};
  private static final double[] HIGH_HATCH = {10000, 5000};
  private static final double[] HIGH_CARGO = {12000, 6000};
  private static final double[] TOP = {14000, 7000};

  private static final double LIFT_ALLOWED_ERROR = 50;
  private static final double ELEVATOR_ALLOWED_ERROR = 50;

 /**
  * Does the normal stuff but also adds the PID values to Smart Dashboard.
  */
  public Elevator() {
    super();
    // Puts Elevator PID values into Smart Dashboard
    SmartDashboard.putNumber("Elevator kP", 2);
    SmartDashboard.putNumber("Elevator kI", 0);
    SmartDashboard.putNumber("Elevator kD", 20);
    SmartDashboard.putNumber("Elevator kF", 0.076);

    // Puts Lift PID values into Smart Dashboard
    SmartDashboard.putNumber("Lift kP", 2);
    SmartDashboard.putNumber("Lift kI", 0);
    SmartDashboard.putNumber("Lift kD", 20);
    SmartDashboard.putNumber("Lift kF", 0.076);

    init();
  }

  /**
   * Adds the encoder to the motor/ Talon
   * Also sets the PID values
   */
  private void init(){

    elevatorMasterMotor.setNeutralMode(NeutralMode.Coast);
    liftMasterMotor.setNeutralMode(NeutralMode.Coast);

    // Configures an encoder fo the lift motor and elevator motor
    elevatorMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    liftMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    // Configures the elevators PID values
    elevatorMasterMotor.config_kP(0, getElevator_kP(), 10);
    elevatorMasterMotor.config_kI(0, getElevator_kI(), 10);
    elevatorMasterMotor.config_kD(0, getElevator_kD(), 10);
    elevatorMasterMotor.config_kF(0, getElevator_kF(), 10);

    // TODO: Find out what the max amperage should be
    elevatorMasterMotor.configPeakCurrentLimit(24);
    elevatorMasterMotor.configPeakCurrentDuration(2000);

    // Configures the lifts PID values
    liftMasterMotor.config_kP(0, getLift_kP(), 10);
    liftMasterMotor.config_kI(0, getLift_kI(), 10);
    liftMasterMotor.config_kD(0, getLift_kD(), 10);
    liftMasterMotor.config_kF(0, getLift_kF(), 10);

    liftMasterMotor.configPeakCurrentLimit(24);
    liftMasterMotor.configPeakCurrentDuration(2000);
  }

  @Override
  protected void initDefaultCommand() {
  }

  /**
   * Moves the elevator up to the next possible level.
   */
  public void elevatorUp() {
    switch(elevatorState) {
      case FLOOR:
        if (isHatch()) {
          System.out.println("Going to low hatch (up)");
          setElevator(ElevatorState.LOW_HATCH);
        } else {
          System.out.println("Going to low cargo (up)");
          setElevator(ElevatorState.LOW_CARGO);
        }
        break;
      case LOW_HATCH:
        System.out.println("Going to middle hatch (up)");
        setElevator(ElevatorState.MIDDLE_HATCH);
        break;
      case LOW_CARGO:
        System.out.println("Going to middle cargo (up)");
        setElevator(ElevatorState.MIDDLE_CARGO);
        break;
      case MIDDLE_HATCH:
        System.out.println("Going to high hatch (up)");
        setElevator(ElevatorState.HIGH_HATCH);
        break;
      case MIDDLE_CARGO:
        System.out.println("Going to high cargo (up)");
        setElevator(ElevatorState.HIGH_CARGO);
        break;
    }
  }

  /**
   * Moves the elevator down to the next possible state.
   */
  @SuppressWarnings("Duplicates")
  public void elevatorDown() {
    switch (elevatorState) {
      case LOW_HATCH:
        System.out.println("Going to floor (down)");
        setElevator(ElevatorState.FLOOR);
        break;
      case LOW_CARGO:
        System.out.println("Going to floor (down)");
        setElevator(ElevatorState.FLOOR);
        break;
      case MIDDLE_HATCH:
        System.out.println("Going to low hatch (down)");
        setElevator(ElevatorState.LOW_HATCH);
        break;
      case MIDDLE_CARGO:
        System.out.println("Going to low cargo (down)");
        setElevator(ElevatorState.LOW_CARGO);
        break;
      case HIGH_HATCH:
        System.out.println("Going to middle hatch (down)");
        setElevator(ElevatorState.MIDDLE_HATCH);
        break;
      case HIGH_CARGO:
        System.out.println("Going to middle cargo (down)");
        setElevator(ElevatorState.MIDDLE_CARGO);
        break;
    }
  }

  /**
   * If elevatorFlip is equal to one thing than set elevator to that height
   */
  @SuppressWarnings("Duplicates")
  public void elevatorFlip(){
    if (isHatch()) {
      elevatorMode = ElevatorMode.CARGO;
    } else {
      elevatorMode = ElevatorMode.HATCH;
    }

    switch (elevatorState) {
      case LOW_HATCH:
        setElevator(LOW_CARGO);
        break;
      case LOW_CARGO:
        setElevator(LOW_HATCH);
        break;
      case MIDDLE_HATCH:
        setElevator(MIDDLE_CARGO);
        break;
      case MIDDLE_CARGO:
        setElevator(MIDDLE_HATCH);
        break;
      case HIGH_HATCH:
        setElevator(HIGH_CARGO);
        break;
      case HIGH_CARGO:
        setElevator(HIGH_HATCH);
        break;
    }
  }

  /**
   * Sets the elevator and lift height to the lowest setting as to prevent a high center of mass
   */
  public void saveElevator() {
    setElevator(FLOOR);
    System.out.println("Saved Elevator");
  }

  /**
   * Checks to see if the elevator and lift are at the desired location
   * @return Returns true if it has reached destination, returns false if it has not reached destination
   */
  public boolean isSetpointReached() {
    return isElevatorAllowableError() && isLiftAllowableError();
  }

  /**
   * Checks to see if the amount of error in the elevator motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  private boolean isElevatorAllowableError() {
    return Math.abs(getElevatorError()) <= ELEVATOR_ALLOWED_ERROR;
  }

  /**
   * Checks to see if te amount of error in the lift motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  private boolean isLiftAllowableError(){
    return Math.abs(getLiftError()) <= LIFT_ALLOWED_ERROR;
  }

  /**
   * Tells the elevator to go to the specified height, also sets elevatorState
   * @param state Is the state of which the elevator is wanted to go to
   */
  public void setElevator(ElevatorState state) {
    System.out.println("---------------");
    System.out.println(isHatch());
    switch (state) {
      case FLOOR:
        System.out.println("FLOOR");
        setElevator(FLOOR);
        elevatorState = ElevatorState.FLOOR;
        break;
      case LOW_HATCH:
        System.out.println("LOW_HATCH");
        setElevator(LOW_HATCH);
        elevatorState = ElevatorState.LOW_HATCH;
        break;
      case LOW_CARGO:
        System.out.println("LOW_CARGO");
        setElevator(LOW_CARGO);
        elevatorState = ElevatorState.LOW_CARGO;
        break;
      case MIDDLE_HATCH:
        System.out.println("MIDDLE_HATCH");
        setElevator(MIDDLE_HATCH);
        elevatorState = ElevatorState.MIDDLE_HATCH;
        break;
      case MIDDLE_CARGO:
        System.out.println("MIDDLE_CARGO");
        setElevator(MIDDLE_CARGO);
        elevatorState = ElevatorState.MIDDLE_CARGO;
        break;
      case HIGH_HATCH:
        System.out.println("HIGH_HATCH");
        setElevator(HIGH_HATCH);
        elevatorState = ElevatorState.HIGH_HATCH;
        break;
      case HIGH_CARGO:
        System.out.println("HIGH_CARGO");
        setElevator(HIGH_CARGO);
        elevatorState = ElevatorState.HIGH_CARGO;
        break;
    }
  }

  /**
   * "Runs" setSetpointElevator and setSetpointLift with the appropriate parameters.
   * @param setpoints double[2] both
   */
  public void setElevator(double[] setpoints) {
    System.out.println(Arrays.toString(setpoints));
    setSetpointElevator(setpoints[0]);
    setSetpointLift(setpoints[1]);
  }

  /**
   * Sets the height of the elevator
   * Will not move the elevator if you want to move it below 0 ticks or above the TOP ticks number
   * @param setpoint is the height (in ticks) of which you want the elevator to go to
   */
  private void setSetpointElevator(double setpoint) {
    elevatorMasterMotor.set(ControlMode.Position, setpoint);
  }

  /**
   * Sets the height of the lift
   * @param setpoint Is the height to which is need to get to
   */
  private void setSetpointLift(double setpoint){
    System.out.println(setpoint);
    liftMasterMotor.set(ControlMode.Position, setpoint);
  }

  /**
   * Gets the error of the elevator motor
   * @return Returns the error of the elevator motor
   */
  public double getElevatorError() {
    return elevatorMasterMotor.getClosedLoopError(0);
  }

  /**
   * Gets the error of the lift motor
   * @return Returns the error of the lift motor
   */
  public double getLiftError() {
    return liftMasterMotor.getClosedLoopError(0);
  }

  /**
   * Gets the value of Setpoint
   * @return returns the value of Setpoint
   */
  public double getElevatorSetpoint(){
    return elevatorMasterMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the value of Setpoint
   * @return returns the value of Setpoint
   */
  public double getLiftSetpoint(){
    return liftMasterMotor.getClosedLoopTarget(0);
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

  public double getElevatorEncoderPosition(){
    return elevatorMasterMotor.getSelectedSensorPosition();
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

  public double getLiftEncoderPosition(){
    return liftMasterMotor.getSelectedSensorPosition();
  }

  public boolean isHatch() {
    return elevatorMode == ElevatorMode.HATCH;
  }

  public boolean isCargo() {
    return elevatorMode == ElevatorMode.CARGO;
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
