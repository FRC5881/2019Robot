package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Arm extends Subsystem {

  private static WPI_TalonSRX armMotor = new WPI_TalonSRX(5);

  private static final int maxTicks = 0;
  private static final int minTicks = 200;

  //TODO: Find cargo and hatch ticks
  public static final int cargoTicks = 10;
  public static final int hatchTicks = 15;


  @Override
  protected void initDefaultCommand() {
  }

  public Arm (){
    super ();
    init ();
  }

  public void init() {
    armMotor.setName("Arm", "Motor");
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    armMotor.setNeutralMode(NeutralMode.Brake);
    LiveWindow.add(armMotor);
  }

  public void setArmMotor (double setPoint){
    if (setPoint < minTicks) setPoint = minTicks;
    if (setPoint > maxTicks) setPoint = maxTicks;

    armMotor.set(ControlMode.Position, setPoint);
  }

  public void setToCargoTicks(){
    setArmMotor(cargoTicks);
  }

  public void setToHatchTicks(){
    setArmMotor(hatchTicks);
  }

  public void flip() {
    if(getSetPoint() == hatchTicks) {
      setToCargoTicks();
    } else if(getSetPoint() == cargoTicks){
      setToHatchTicks();
    }
  }

  public boolean isCargo() {
    return getSetPoint() == cargoTicks;
  }

  public boolean isHatch() {
    return getSetPoint() == hatchTicks;
  }

  public double getSetPoint() {
    return armMotor.getClosedLoopTarget();
  }


}
