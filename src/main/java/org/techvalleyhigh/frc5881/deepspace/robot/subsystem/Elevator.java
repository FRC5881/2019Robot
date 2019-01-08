package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

    public Elevator() {
        super();
    }
    public Elevator(String name){
        super(name);
        init();
    }

    private void init(){

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void elevatorUp(){
                /*
        Put something here that would signify the moving of the 'elevator' in the upward fashion, but not to far up.
         */
    }
    public void elevatorDown(){
                /*
        Put something here that would signify the moving of the 'elevator' in the upward fashion, but not to far up.
         */
    }
}
