package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    private ElevatorState elevatorState = ElevatorState.HIGH_HATCH;

    //TODO: Change the "deviceNumber" to whatever the actual number(s) on the talon(s) is(are).
    private WPI_TalonSRX elevatorMasterMotor = new WPI_TalonSRX(2);
    private WPI_TalonSRX elevatorSlaveMotor = new WPI_TalonSRX(3);
    //TODO: find out how many "ticks" it is till the top of the elevator
    //if we actually reach these points we need to stop
    private int topTicks = 1000;
    private int bottomTicks = 0;
    //TODO: Find out how many "ticks" it is till the first level that the elevator needs to go to
    private int firstLevelTicks = 333;
    //TODO: Find out how many "ticks" it is till the second level that the elevator needs to go to
    private int secondLevelTicks = 667;
    //TODO: Find out how many "ticks" it is till the third level that the elevator needs to go to(if we actually decide to do that)
    private int thirdLevelTicks = 1000;
    //TODO: We should probably find out what the heights for the low, middle and high hatchet and ball locations are
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
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void elevatorUp(){
        //TODO: Probably should organize this in the future so it might have a chance of working

        if(elevatorState == ElevatorState.LOW_HATCH){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorState = ElevatorState.MIDDLE_HATCH;
        } else if(elevatorState == ElevatorState.MIDDLE_HATCH) {
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorState = ElevatorState.HIGH_HATCH;
        } else if(elevatorState == ElevatorState.LOW_CARGO){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorState = ElevatorState.MIDDLE_CARGO;
        } else if(elevatorState == ElevatorState.MIDDLE_CARGO){
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorState = ElevatorState.HIGH_CARGO;
        }                /*
        Put something here that would signify the moving of the 'elevator' in the upward fashion, but not too far up.
         */

    }
    public void elevatorDown(){
        //TODO: Probably should organize this in the future so it might have a chance of working
        if (elevatorState == ElevatorState.HIGH_HATCH){
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorState = ElevatorState.MIDDLE_HATCH;
        } else if(elevatorState == ElevatorState.MIDDLE_HATCH){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorState = ElevatorState.LOW_HATCH;
        } else if(elevatorState == ElevatorState.HIGH_CARGO){
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorState = ElevatorState.MIDDLE_CARGO;
        } else if(elevatorState == ElevatorState.MIDDLE_CARGO){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorState = ElevatorState.LOW_CARGO;
        }
                /*
        Put something here that would signify the moving of the 'elevator' in the downward fashion, but not too far down.
         */
    }
}
