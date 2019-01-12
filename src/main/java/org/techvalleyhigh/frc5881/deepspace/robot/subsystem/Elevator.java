package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.techvalleyhigh.frc5881.deepspace.robot.Robot.manipulator;

/**
 * This class includes 2 methods in which are elevatorUp(), and elevatorDown().
 */
public class Elevator extends Subsystem {

    private ElevatorState elevatorState = ElevatorState.HIGH_HATCH;

    // TODO: Change the "deviceNumber" to whatever the actual number on the talon is.
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    //  ||                                          ||
    //  \/ is the talon for the four bar lift motor \/
    private WPI_TalonSRX liftMotor = new WPI_TalonSRX(3);

    // TODO: find out how many "ticks" it is till the top of the elevator
    /**
    * topTicks is (hopefully going to be) the most amount of ticks the elevator motor(s) can go before it over-extends.
    */
    public static final int topTicks = 1000;
    /**
    * bottomTicks is the least amount of ticks the elevator motor(s) be at before it tries to break something.
    */
    public static final int bottomTicks = 0;


    // TODO: Find out what the actual amount of ticks to each is
    public static final double lowHatchTicks = 10;
    public static final double lowCargoTicks = 15;
    public static final double midHatchTicks = 50;
    public static final double midCargoTicks = 55;
    public static final double highHatchTicks = 100;
    public static final double highCargoTicks = 105;

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
        NONE,

        LOW_HATCH,

        LOW_CARGO,

        MIDDLE_HATCH,

        MIDDLE_CARGO,

        HIGH_HATCH,

        HIGH_CARGO
    }

  /**
   * Does the normal stuff but also adds the PID values to Smart Dashboard.
   */
  public Elevator() {

        super();
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
   * Also "sets the PID values"
   */
  private void init(){

      liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

      elevatorMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);


      elevatorMasterMotor.config_kP(0, getElevator_kP(), 10);

      elevatorMasterMotor.config_kI(0, getElevator_kI(), 10);

      elevatorMasterMotor.config_kD(0, getElevator_kD(), 10);

      elevatorMasterMotor.config_kF(0, getElevator_kF(), 10);

      liftMotor.config_kP(0, getLift_kP(), 10);

      liftMotor.config_kI(0, getLift_kI(), 10);

      liftMotor.config_kD(0, getLift_kD(), 10);

      liftMotor.config_kF(0, getLift_kF(), 10);

    }

    @Override
    protected void initDefaultCommand() {

    }

  /**
   * Moves the elevator up to the next possible level.
   */
  public void elevatorUp(){

      if(manipulator.getMode().equals(Manipulator.ManipulatorMode.HATCH)){

        if(ElevatorState.NONE.equals(elevatorState)){

              setSetpoint(lowHatchTicks);
              elevatorState = ElevatorState.LOW_HATCH;

            } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {

              setSetpoint(lowHatchTicks);
              elevatorState = ElevatorState.MIDDLE_HATCH;

            } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){

              setSetpoint(highHatchTicks);
              elevatorState = ElevatorState.HIGH_HATCH;

            }
          } else if(manipulator.getMode().equals(Manipulator.ManipulatorMode.CARGO)){

            if(ElevatorState.LOW_CARGO.equals(elevatorState)){

              setSetpoint(midCargoTicks);
              elevatorState = ElevatorState.MIDDLE_CARGO;

            }  else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){

              setSetpoint(highCargoTicks);
              elevatorState = ElevatorState.HIGH_CARGO;

            }
          }
    }

  /**
   * Moves the elevator down to the next possible state.
   */
  public void elevatorDown(){
        // Check if the manipulator mode is hatch
    if (manipulator.getMode().equals(Manipulator.ManipulatorMode.HATCH)) {
        // If the elevator's height is "high hatch" then proceed
      if (ElevatorState.HIGH_HATCH.equals(elevatorState)) {

        setSetpoint(midHatchTicks);
        elevatorState = ElevatorState.MIDDLE_HATCH;

      } else if (ElevatorState.MIDDLE_HATCH.equals(elevatorState)) {

        setSetpoint(lowHatchTicks);
        elevatorState = ElevatorState.LOW_HATCH;

      } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {

        setSetpoint(bottomTicks);
        elevatorState = ElevatorState.NONE;

      }
    } else if (manipulator.getMode().equals(Manipulator.ManipulatorMode.CARGO)){

      if(ElevatorState.HIGH_CARGO.equals(elevatorState)) {

        setSetpoint(midCargoTicks);
        elevatorState = ElevatorState.MIDDLE_CARGO;

      } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){

        setSetpoint(lowCargoTicks);
        elevatorState = ElevatorState.LOW_CARGO;

      } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){

        setSetpoint(bottomTicks);
        elevatorState = ElevatorState.NONE;

      }
    }
  }

  /**
   * Sets the height of the elevator
   * Will not move the elevator if you want to move it below 0 ticks or above the top ticks number
   * @param setpoint is the height (in ticks) of which you want the elevator to go to
   */
  public void setSetpoint(double setpoint) {
      if(getSetpoint() >= bottomTicks && getSetpoint() <= topTicks) {
        elevatorMasterMotor.set(ControlMode.Position, setpoint);
      }
    }

  /**
   * Gets the value of Setpoint
   * @return returns the value of Setpoint
   */
  private double getSetpoint(){
      return elevatorMasterMotor.getClosedLoopTarget(0);
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
      double inches1;
      // TODO: need to find out how many ticks per rotation the encoder has.
      double ticksPerRotation = 1000;
      // How many inches the lift moves per every full rotation
      // TODO: need to find out how many inches the lift moves per rotation
      double inchesMovedPerRotation = 0.5;
      inches1 = inches * inchesMovedPerRotation;

      ticks = inches1 / ticksPerRotation;

      return ticks;
  }
}
