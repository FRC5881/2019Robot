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
        int ticksToGo = 0;
        if(elevatorLevel == 3){
            elevatorMasterMotor.set(ControlMode.Position, 0);
        } else if (elevatorLevel < 3){
            //TODO: Figure out if we want to do the elevator movement by incrementing or by just using the joysticks
            //Supposed to make the ticksToGo become the ticks of the next level, don't know if it will do that, but who knows.
            ticksToGo += 333;
            //TODO: Figure out what mode we want to move the elevator in
            elevatorMasterMotor.set(ControlMode.Position, ticksToGo);
            System.out.println(ticksToGo);
        }
        //elevatorMasterMotor.set(ControlMode.Position, );
                /*
        Put something here that would signify the moving of the 'elevator' in the upward fashion, but not too far up.
         */

    }
    public void elevatorDown(){
        int ticksToGo = 0;
        if (elevatorLevel <= 3 && ticksToGo > 0){
            ticksToGo -= elevatorLevel * 333;
            elevatorMasterMotor.set(ControlMode.Position, ticksToGo);
        }
                /*
        Put something here that would signify the moving of the 'elevator' in the downward fashion, but not too far up.
         */
    }
}
