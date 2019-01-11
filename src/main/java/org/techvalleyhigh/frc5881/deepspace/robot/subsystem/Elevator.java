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

    // TODO: Change the "deviceNumber" to whatever the actual number(s) on the talon(s) is(are).
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX elevatorSlaveMotor = new WPI_TalonSRX(3);

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
    private int lowHatchTicks = 10;
    private int lowCargoTicks = 15;
    private int midHatchTicks = 50;
    private int midCargoTicks = 55;
    private int highHatchTicks = 100;
    private int highCargoTicks = 105;

    // TODO: Find the actual kP of the elevator motors
    private int kP = 0;

    // TODO: Find the actual kI of the elevator motors
    private int kI = 0;

    // TODO: Find the actual kD of the elevator motors
    private int kD = 0;

    // TODO: Find the actual kF of the elevator motors
    private int kF = 0;

  /*
    The order of heights is: (greatest to least)

    1. High cargo

    2. High hatch

    3. Middle cargo

    4. Middle hatch

    5. Low cargo

    6. Low hatch
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
        SmartDashboard.putNumber("Elevator kP", 2);
        SmartDashboard.putNumber("Elevator kI", 0);
        SmartDashboard.putNumber("Elevator kD", 20);
        SmartDashboard.putNumber("Elevator kF", 0.076);
        init();
    }

  /**
   * Adds the encoder to the motor/ Talon
   * Also "sets the PID values"
   */
  private void init(){

      elevatorSlaveMotor.set(ControlMode.Follower, 2);
      elevatorMasterMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);


      elevatorMasterMotor.config_kP(0, getElevator_kP(), 10);

      elevatorMasterMotor.config_kI(0, getElevator_kI(), 10);

      elevatorMasterMotor.config_kD(0, getElevator_kD(), 10);

      elevatorMasterMotor.config_kF(0, getElevator_kF(), 10);

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

              setSetpoint(lowCargoTicks);
              elevatorState = ElevatorState.MIDDLE_HATCH;

            } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){

              setSetpoint(midCargoTicks);
              elevatorState = ElevatorState.HIGH_HATCH;

            }
          } else if(manipulator.getMode().equals(Manipulator.ManipulatorMode.CARGO)){

            if(ElevatorState.LOW_CARGO.equals(elevatorState)){

              setSetpoint(midHatchTicks);
              elevatorState = ElevatorState.MIDDLE_CARGO;

            }  else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){

              setSetpoint(highHatchTicks);
              elevatorState = ElevatorState.HIGH_CARGO;

            }
          }
    }

  /**
   * Moves the elevator down to the next possible state.
   */
  public void elevatorDown(){

    if (manipulator.getMode().equals(Manipulator.ManipulatorMode.HATCH)) {

      if (ElevatorState.HIGH_HATCH.equals(elevatorState)) {

        setSetpoint(midCargoTicks);
        elevatorState = ElevatorState.MIDDLE_HATCH;

      } else if (ElevatorState.MIDDLE_HATCH.equals(elevatorState)) {

        setSetpoint(lowCargoTicks);
        elevatorState = ElevatorState.LOW_HATCH;

      } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {

        setSetpoint(bottomTicks);
        elevatorState = ElevatorState.NONE;

      }
    } else if (manipulator.getMode().equals(Manipulator.ManipulatorMode.CARGO)){

      if(ElevatorState.HIGH_CARGO.equals(elevatorState)) {

        setSetpoint(highHatchTicks);
        elevatorState = ElevatorState.HIGH_CARGO;

      } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){

        setSetpoint(midHatchTicks);
        elevatorState = ElevatorState.MIDDLE_CARGO;

      } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){

        setSetpoint(lowHatchTicks);
        elevatorState = ElevatorState.LOW_CARGO;

      }
    }
  }

  public void setSetpoint(double setpoint) {
      if(getSetpoint() >= bottomTicks && getSetpoint() <= topTicks) {
        elevatorMasterMotor.set(ControlMode.Position, setpoint);
      }
    }

    public double getSetpoint(){
      return elevatorMasterMotor.getClosedLoopTarget(0);
    }

    public double getElevator_kP() {
      return SmartDashboard.getNumber("Elevator kP", 2.0);
    }

    public double getElevator_kI() {
      return SmartDashboard.getNumber("Elevator kI", 0);
    }

    public double getElevator_kD(){
      return SmartDashboard.getNumber("Elevator kD", 20);
    }

    public double getElevator_kF() {
      return SmartDashboard.getNumber("Elevator kF", 0.076);
    }

}
