package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
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

    private int elevatorLevel = 0;

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
        if(elevatorLevel == 0){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorLevel = 1;
        } else if(elevatorLevel == 1){
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorLevel = 2;
        } else if(elevatorLevel == 2){
            elevatorMasterMotor.set(ControlMode.Position, thirdLevelTicks);
            elevatorLevel = 3;
        }
                /*
        Put something here that would signify the moving of the 'elevator' in the upward fashion, but not too far up.
         */

    }
    public void elevatorDown(){
        if (elevatorLevel == 3){
            elevatorMasterMotor.set(ControlMode.Position, secondLevelTicks);
            elevatorLevel = 2;
        } else if(elevatorLevel == 2){
            elevatorMasterMotor.set(ControlMode.Position, firstLevelTicks);
            elevatorLevel = 1;
        }
                /*
        Put something here that would signify the moving of the 'elevator' in the downward fashion, but not too far down.
         */
    }
}
