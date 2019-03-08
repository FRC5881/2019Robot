package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

import javax.naming.ldap.Control;
import java.util.Arrays;

/**
 * Contains methods to get the error of the motors, set the PIDs and even move the elevator and bar!
 */
public class Elevator extends Subsystem {
  private ElevatorState elevatorState = ElevatorState.FLOOR;
  private LiftMode liftMode = LiftMode.HATCH;

  // TODO: Change the "deviceNumber" to whatever the actual number on the talon is.
  private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(5);
  //  \/ is the talon for the four bar lift motor \/
  private WPI_TalonSRX barMasterMotor = new WPI_TalonSRX(6);

  /**
   * Is the mode of the lift
   */
  public enum LiftMode {
    HATCH,
    CARGO
  }

  /**
   * Is the state of the elevator and bar
   */
  public enum ElevatorState {
    FLOOR(0, 0),
    LOW_HATCH(200, 50),
    LOW_CARGO( 300, 150),
    MIDDLE_HATCH(500, 1000),
    MIDDLE_CARGO(600, 1000),
    HIGH_HATCH(800, 8000),
    HIGH_CARGO(1000, 10000),
    TOP(1200, 11000);

    private double barPos, elevatorPos;

    ElevatorState(double bar, double elevator) {
      barPos = bar;
      elevatorPos = elevator;
    }

    public double getElevatorPosition() {
      return elevatorPos;
    }

    public double getBarPosition() {
      return barPos;
    }
  }

  private static final double BAR_ALLOWED_ERROR = 50;
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

    // Puts Bar PID values into Smart Dashboard
    SmartDashboard.putNumber("Bar kP", 2);
    SmartDashboard.putNumber("Bar kI", 0);
    SmartDashboard.putNumber("Bar kD", 20);
    SmartDashboard.putNumber("Bar kF", 0.076);

    init();
  }

  /**
   * Adds the encoder to the motor/ Talon
   * Also sets the PID values
   */
  public void init(){
    barMasterMotor.set(ControlMode.Position, 0);
    elevatorMasterMotor.set(ControlMode.Position, 0);
    barMasterMotor.setSelectedSensorPosition(0);
    elevatorMasterMotor.setSelectedSensorPosition(0);

    barMasterMotor.setInverted(true);

    elevatorMasterMotor.setNeutralMode(NeutralMode.Brake);
    barMasterMotor.setNeutralMode(NeutralMode.Brake);

    // Configures an encoder for the bar motor and elevator motor
    elevatorMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    barMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    // Configures the elevators PID values
    elevatorMasterMotor.config_kP(0, getElevator_kP(), 10);
    elevatorMasterMotor.config_kI(0, getElevator_kI(), 10);
    elevatorMasterMotor.config_kD(0, getElevator_kD(), 10);
    elevatorMasterMotor.config_kF(0, getElevator_kF(), 10);

    // TODO: Find out what the max amperage should be
    //elevatorMasterMotor.configPeakCurrentLimit(24);
    //elevatorMasterMotor.configPeakCurrentDuration(2000);

    // Configures the bar's PID values
    barMasterMotor.config_kP(0, getBar_kP(), 10);
    barMasterMotor.config_kI(0, getBar_kI(), 10);
    barMasterMotor.config_kD(0, getBar_kD(), 10);
    barMasterMotor.config_kF(0, getBar_kF(), 10);

    setLiftSetpoint(ElevatorState.FLOOR);

    //barMasterMotor.configPeakCurrentLimit(24);
    //barMasterMotor.configPeakCurrentDuration(2000);
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
          setLiftSetpoint(ElevatorState.LOW_HATCH);
        } else {
          System.out.println("Going to low cargo (up)");
          setLiftSetpoint(ElevatorState.LOW_CARGO);
        }
        break;
      case LOW_HATCH:
        System.out.println("Going to middle hatch (up)");
        setLiftSetpoint(ElevatorState.MIDDLE_HATCH);
        break;
      case LOW_CARGO:
        System.out.println("Going to middle cargo (up)");
        setLiftSetpoint(ElevatorState.MIDDLE_CARGO);
        break;
      case MIDDLE_HATCH:
        System.out.println("Going to high hatch (up)");
        setLiftSetpoint(ElevatorState.HIGH_HATCH);
        break;
      case MIDDLE_CARGO:
        System.out.println("Going to high cargo (up)");
        setLiftSetpoint(ElevatorState.HIGH_CARGO);
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
        setLiftSetpoint(ElevatorState.FLOOR);
        break;
      case LOW_CARGO:
        System.out.println("Going to floor (down)");
        setLiftSetpoint(ElevatorState.FLOOR);
        break;
      case MIDDLE_HATCH:
        System.out.println("Going to low hatch (down)");
        setLiftSetpoint(ElevatorState.LOW_HATCH);
        break;
      case MIDDLE_CARGO:
        System.out.println("Going to low cargo (down)");
        setLiftSetpoint(ElevatorState.LOW_CARGO);
        break;
      case HIGH_HATCH:
        System.out.println("Going to middle hatch (down)");
        setLiftSetpoint(ElevatorState.MIDDLE_HATCH);
        break;
      case HIGH_CARGO:
        System.out.println("Going to middle cargo (down)");
        setLiftSetpoint(ElevatorState.MIDDLE_CARGO);
        break;
    }
  }

  /**
   * If elevatorFlip is equal to one thing than set elevator to that height
   */
  @SuppressWarnings("Duplicates")
  public void elevatorFlip(){
    if (isHatch()) {
      liftMode = LiftMode.CARGO;
    } else {
      liftMode = LiftMode.HATCH;
    }

    switch (elevatorState) {
      case LOW_HATCH:
        setLiftSetpoint(ElevatorState.LOW_HATCH);
        break;
      case LOW_CARGO:
        setLiftSetpoint(ElevatorState.LOW_HATCH);
        break;
      case MIDDLE_HATCH:
        setLiftSetpoint(ElevatorState.MIDDLE_CARGO);
        break;
      case MIDDLE_CARGO:
        setLiftSetpoint(ElevatorState.MIDDLE_HATCH);
        break;
      case HIGH_HATCH:
        setLiftSetpoint(ElevatorState.HIGH_CARGO);
        break;
      case HIGH_CARGO:
        setLiftSetpoint(ElevatorState.HIGH_HATCH);
        break;
    }
  }

  /**
   * Sets the elevator and bar height to the lowest setting as to prevent a high center of mass
   */
  public void saveElevator() {
    setLiftSetpoint(ElevatorState.FLOOR);
    System.out.println("Saved Elevator");
  }

  /**
   * Checks to see if the elevator is within the margin of error of it's target
   * @return true if it's close enough to the desired target
   */
  public boolean isElevatorSetpointReached(){
    return isElevatorAllowableError();
  }

  /**
   * Checks to see if the amount of error in the elevator motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  private boolean isElevatorAllowableError() {
    return Math.abs(getElevatorError()) <= ELEVATOR_ALLOWED_ERROR;
  }

  /**
   * Checks to see if the bar is within the margin of error of it's target
   * @return true if it's close enough to the desired target
   */
  public boolean isBarSetpointReached(){
    return isBarAllowableError();
  }

  /**
   * Checks to see if te amount of error in the bar motors is acceptable
   * @return Returns true if it is within the allowed range, returns false if it is not close enough
   */
  private boolean isBarAllowableError(){
    return Math.abs(getBarError()) <= BAR_ALLOWED_ERROR;
  }

  /**
   * Tells the bar and elevator to go to the specified height, also sets elevatorState
   * @param state Is the state of which the elevator is wanted to go to
   */
  public void setLiftSetpoint(ElevatorState state) {
    System.out.println("---------------");
    System.out.println(isHatch());
    switch (state) {
      case FLOOR:
        System.out.println("FLOOR");
        if (isHatch()) {
            Robot.led.sendLED(LED.Color.VERY_LOW_YELLOW);
        } else {
            Robot.led.sendLED(LED.Color.VERY_LOW_ORANGE);
        }
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.FLOOR;
        break;
      case LOW_HATCH:
        System.out.println("LOW_HATCH");
        Robot.led.sendLED(LED.Color.LOW_YELLOW);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.LOW_HATCH;
        break;
      case LOW_CARGO:
        System.out.println("LOW_CARGO");
        Robot.led.sendLED(LED.Color.LOW_ORANGE);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.LOW_CARGO;
        break;
      case MIDDLE_HATCH:
        System.out.println("MIDDLE_HATCH");
        Robot.led.sendLED(LED.Color.MID_YELLOW);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.MIDDLE_HATCH;
        break;
      case MIDDLE_CARGO:
        System.out.println("MIDDLE_CARGO");
        Robot.led.sendLED(LED.Color.MID_ORANGE);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.MIDDLE_CARGO;
        break;
      case HIGH_HATCH:
        System.out.println("HIGH_HATCH");
        Robot.led.sendLED(LED.Color.HIGH_YELLOW);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.HIGH_HATCH;
        break;
      case HIGH_CARGO:
        System.out.println("HIGH_CARGO");
        Robot.led.sendLED(LED.Color.HIGH_ORANGE);
        setLiftSetpoint(state.getBarPosition(), state.getElevatorPosition());
        elevatorState = ElevatorState.HIGH_CARGO;
        break;
    }
  }

  /**
   * "Runs" setLiftSetpoint and setBarSetpoint with the appropriate parameters.
   * @param barSetpoint is the desired setpoint for the bar to go to
   * @param elevatorSetpoint is the desired setpoint for the elevator to go to
   */

  public void setLiftSetpoint(double barSetpoint, double elevatorSetpoint) {
    System.out.println(barSetpoint + " <-- is bar setpoint");
    System.out.println(elevatorSetpoint + " <-- is elevator setpoint");

    if (barSetpoint == -42) {
        barMasterMotor.set(ControlMode.PercentOutput, 0);
    } else {
        setBarSetpoint(barSetpoint);
    }

    setElevatorSetpoint(elevatorSetpoint);
  }

  /**
   * Sets the height of the elevator
   * @param setpoint is the height (in ticks) of which you want the elevator to go to
   */
  private void setElevatorSetpoint(double setpoint) {
    elevatorMasterMotor.set(ControlMode.Position, setpoint);
  }

  /**
   * Sets the setpoint of the bar
   * @param setpoint Is the setpoint to which the bar needs to get to
   */
  private void setBarSetpoint(double setpoint){
    System.out.println(setpoint);
    barMasterMotor.set(ControlMode.Position, setpoint);
  }

  /**
   * Gets the error of the elevator motor
   * @return Returns the error of the elevator motor
   */
  public double getElevatorError() {
    return elevatorMasterMotor.getClosedLoopError(0);
  }

  /**
   * Gets the error of the bar motor
   * @return Returns the error of the bar motor
   */
  public double getBarError() {
    return barMasterMotor.getClosedLoopError(0);
  }

  /**
   * Gets the value of elevator setpoint
   * @return returns the value of elevator setpoint
   */
  public double getElevatorSetpoint(){
    return elevatorMasterMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the value of bar setpoint
   * @return returns the value of bar setpoint
   */
  public double getBarSetpoint(){
    return barMasterMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the value of Elevator_kP from Smart Dashboard
   * @return Returns the value of Elevator_kP
   */
  private double getElevator_kP() {
    return SmartDashboard.getNumber("Elevator kP", 2.0);
  }

  /**
   * Gets the value of Elevator_kI from Smart Dashboard
   * @return Returns the value of Elevator_kI
   */
  private double getElevator_kI() {
    return SmartDashboard.getNumber("Elevator kI", 0);
  }

  /**
   * Gets the value of Elevator_kD from Smart Dashboard
   * @return Returns the value of Elevator_kD
   */
  private double getElevator_kD(){
    return SmartDashboard.getNumber("Elevator kD", 20);
  }

  /**
   * Gets the value of Elevator_kF from Smart Dashboard
   * @return Returns the value of Elevator_kF
   */
  private double getElevator_kF() {
    return SmartDashboard.getNumber("Elevator kF", 0.076);
  }

  public double getElevatorEncoderPosition() {
      return elevatorMasterMotor.getSelectedSensorPosition(0);
  }

  /**
   * Gets the value of Bar_kP from Smart Dashboard
   * @return Returns the value of Bar_kP
   */
  private double getBar_kP() {
    return SmartDashboard.getNumber("Bar kP", 2.0);
  }

  /**
   * Gets the value of Bar_kI from Smart Dashboard
   * @return Returns the value of Bar_kI
   */
  private double getBar_kI() {
    return SmartDashboard.getNumber("Bar kI", 0);
  }

  /**
   * Gets the value of Bar_kD from Smart Dashboard
   * @return Returns the value of Bar_kD
   */
  private double getBar_kD(){
    return SmartDashboard.getNumber("Bar kD", 20);
  }

  /**
   * Gets the value of Bar_kF from Smart Dashboard
   * @return Returns the value of Bar_kF
   */
  private double getBar_kF() {
    return SmartDashboard.getNumber("Bar kF", 0.076);
  }

  public double getBarEncoderPosition(){
    return barMasterMotor.getSelectedSensorPosition(0);
  }

  public double getBarCurrent() {
      return barMasterMotor.getOutputCurrent();
  }

  public boolean isHatch() {
    return liftMode == LiftMode.HATCH;
  }

  public boolean isCargo() {
    return liftMode == LiftMode.CARGO;
  }

    public ElevatorState getLiftState() {
        return elevatorState;
    }
}
