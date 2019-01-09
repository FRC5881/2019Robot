package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    //TODO: Find out if there is any better way to do this
    private ElevatorState elevatorState = ElevatorState.HIGH_HATCH;
    // TODO: Change the "deviceNumber" to whatever the actual number(s) on the talon(s) is(are).
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX elevatorSlaveMotor = new WPI_TalonSRX(3);
//    private Encoder encoder1 = new Encoder(0, 1);
//    private Encoder encoder2 = new Encoder(2, 3);
    // TODO: find out how many "ticks" it is till the top of the elevator
    // if we actually reach these points we need to stop
    public static final int topTicks = 1000;
    public static final int bottomTicks = 0;

    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int lowHatchTicks = 10;
    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int lowCargoTicks = 15;
    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int midHatchTicks = 50;
    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int midCargoTicks = 55;
    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int highHatchTicks = 100;
    //TODO: Find out what the actual amount of ticks to each thing is
    public static final int highCargoTicks = 105;
    // TODO: We should probably find out what the heights for the low, middle and high hatchet and ball locations are

    //TODO: Find the value
    private int kP = 0;
    //TODO: Find the value
    private int kI = 0;
    //TODO: Find the value
    private int kD = 0;
    //TODO: Find the value
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

    public Elevator() {
        super();
    }

    public Elevator(String name){
        super(name);
        init();
    }

    private void init(){
       elevatorSlaveMotor.follow(elevatorMasterMotor);
       // elevatorMasterMotor.configSelectedFeedbackSensor();

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void elevatorUp(){
        // TODO: Probably should organize this in the future so it might have a chance of working
        // In theory whenever we tell the elevator to go up it should
          if(ElevatorState.NONE.equals(elevatorState)){
            setSetpoint(lowHatchTicks);
            elevatorState = ElevatorState.LOW_HATCH;
          } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {
            setSetpoint(lowCargoTicks);
            elevatorState = ElevatorState.LOW_CARGO;
          } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){
            setSetpoint(midHatchTicks);
            elevatorState = ElevatorState.MIDDLE_HATCH;
          } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){
            setSetpoint(midCargoTicks);
            elevatorState = ElevatorState.MIDDLE_CARGO;
          } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
            setSetpoint(highHatchTicks);
            elevatorState = ElevatorState.HIGH_HATCH;
          } else if(ElevatorState.HIGH_HATCH.equals(elevatorState)){
            setSetpoint(highCargoTicks);
            elevatorState = ElevatorState.HIGH_CARGO;
          }
    }

    public void elevatorDown(){
        if(ElevatorState.HIGH_CARGO.equals(elevatorState)){
          setSetpoint(highHatchTicks);
          elevatorState = ElevatorState.HIGH_HATCH;
        } else if(ElevatorState.HIGH_HATCH.equals(elevatorState)){
          setSetpoint(midCargoTicks);
          elevatorState = ElevatorState.MIDDLE_CARGO;
        } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
          setSetpoint(midHatchTicks);
          elevatorState = ElevatorState.MIDDLE_HATCH;
        } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){
          setSetpoint(lowCargoTicks);
          elevatorState = ElevatorState.LOW_CARGO;
        } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){
          setSetpoint(lowHatchTicks);
          elevatorState = ElevatorState.LOW_HATCH;
        } else if(ElevatorState.LOW_HATCH.equals(elevatorState)){
          setSetpoint(bottomTicks);
          elevatorState = ElevatorState.NONE;
        }
    }

    public void setSetpoint(double setpoint) {
      elevatorMasterMotor.set(ControlMode.Position, setpoint);
    }
}
