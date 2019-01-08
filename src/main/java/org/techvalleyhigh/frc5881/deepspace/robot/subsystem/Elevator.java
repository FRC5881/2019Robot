package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// TODO: Probably should test this code and make sure it works.

public class Elevator extends Subsystem {

    private ElevatorState elevatorState = ElevatorState.HIGH_HATCH;

    // TODO: Change the "deviceNumber" to whatever the actual number(s) on the talon(s) is(are).
    // There are four motors because Ian is thinking of having four on the bot.
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX elevatorSlaveMotor1 = new WPI_TalonSRX(3);
    private WPI_TalonSRX elevatorSlaveMotor2 = new WPI_TalonSRX(4);
    private WPI_TalonSRX elevatorSlaveMotor3 = new WPI_TalonSRX(5);

    // TODO: find out how many "ticks" it is till the top of the elevator
    // If we actually reach these points we need to stop
    private int topTicks = 1000;

    private int bottomTicks = 0;

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

    public Elevator() {

        super();
    }

    public Elevator(String name){

        super(name);
        init();
    }

    private void init(){

      // TODO: Find out what direction the four motors are going in and maybe switch this around
      elevatorSlaveMotor1.follow(elevatorMasterMotor);
      elevatorSlaveMotor2.follow(elevatorMasterMotor);
      elevatorSlaveMotor3.follow(elevatorMasterMotor);

      // Puts the PID values into Smart Dashboard
      SmartDashboard.putNumber("Elevator kP", kP);
      SmartDashboard.putNumber("Elevator kI", kI);
      SmartDashboard.putNumber("Elevator kD", kD);
      SmartDashboard.putNumber("Elevator kF", kF);

      // Sets the PID values of the elevator motors
      elevatorMasterMotor.config_kP(0, kD);
      elevatorMasterMotor.config_kI(0, kI);
      elevatorMasterMotor.config_kD(0, kD);
      elevatorMasterMotor.config_kF(0, kF);

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void elevatorUp(){

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
