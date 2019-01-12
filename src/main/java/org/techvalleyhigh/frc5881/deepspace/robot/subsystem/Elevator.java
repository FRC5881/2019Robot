package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
    //TODO: Find out if there is any better way to do this
    private ElevatorState elevatorState = ElevatorState.HIGH_HATCH;
    // TODO: Change the "deviceNumber" to whatever the actual number(s) on the talon(s) is(are).
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX elevatorSlaveMotor = new WPI_TalonSRX(3);
    // TODO: find out how many "ticks" it is till the top of the elevator
    // if we actually reach these points we need to stop
    private int topTicks = 1000;
    private int bottomTicks = 0;

    //TODO: Find out what the actual amount of ticks to each thing is
    private int lowHatchTicks = 10;
    //TODO: Find out what the actual amount of ticks to each thing is
    private int lowCargoTicks = 15;
    //TODO: Find out what the actual amount of ticks to each thing is
    private int midHatchTicks = 50;
    //TODO: Find out what the actual amount of ticks to each thing is
    private int midCargoTicks = 55;
    //TODO: Find out what the actual amount of ticks to each thing is
    private int highHatchTicks = 100;
    //TODO: Find out what the actual amount of ticks to each thing is
    private int highCargoTicks = 105;
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
      SmartDashboard.putNumber("elevator kP", kP);
      SmartDashboard.putNumber("elevator kI", kI);
      SmartDashboard.putNumber("elevator kD", kD);
      SmartDashboard.putNumber("elevator kF", kF);
      elevatorMasterMotor.config_kP(0, 2);
      elevatorMasterMotor.config_kI(0, 0);
      elevatorMasterMotor.config_kD(0, 20);
      elevatorMasterMotor.config_kF(0, .076);

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void elevatorUp(){
        // TODO: Probably should organize this in the future so it might have a chance of working
        // In theory whenever we tell the elevator to go up it should
          if(ElevatorState.NONE.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, lowHatchTicks);
            elevatorState = ElevatorState.LOW_HATCH;
          } else if (ElevatorState.LOW_HATCH.equals(elevatorState)) {
            elevatorMasterMotor.set(ControlMode.Position, lowCargoTicks);
            elevatorState = ElevatorState.LOW_CARGO;
          } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, midHatchTicks);
            elevatorState = ElevatorState.MIDDLE_HATCH;
          } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, midCargoTicks);
            elevatorState = ElevatorState.MIDDLE_CARGO;
          } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, highHatchTicks);
            elevatorState = ElevatorState.HIGH_HATCH;
          } else if(ElevatorState.HIGH_HATCH.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, highCargoTicks);
            elevatorState = ElevatorState.HIGH_CARGO;
          }
    }

    public void elevatorDown(){
        if(ElevatorState.HIGH_CARGO.equals(elevatorState)){
            elevatorMasterMotor.set(ControlMode.Position, highHatchTicks);
            elevatorState = ElevatorState.HIGH_HATCH;
        } else if(ElevatorState.HIGH_HATCH.equals(elevatorState)){
          elevatorMasterMotor.set(ControlMode.Position, midCargoTicks);
          elevatorState = ElevatorState.MIDDLE_CARGO;
        } else if(ElevatorState.MIDDLE_CARGO.equals(elevatorState)){
          elevatorMasterMotor.set(ControlMode.Position, midHatchTicks);
          elevatorState = ElevatorState.MIDDLE_HATCH;
        } else if(ElevatorState.MIDDLE_HATCH.equals(elevatorState)){
          elevatorMasterMotor.set(ControlMode.Position, lowCargoTicks);
          elevatorState = ElevatorState.LOW_CARGO;
        } else if(ElevatorState.LOW_CARGO.equals(elevatorState)){
          elevatorMasterMotor.set(ControlMode.Position, lowHatchTicks);
          elevatorState = ElevatorState.LOW_HATCH;
        } else if(ElevatorState.LOW_HATCH.equals(elevatorState)){
          elevatorMasterMotor.set(ControlMode.Position, bottomTicks);
          elevatorState = ElevatorState.NONE;
        }
    }
}
