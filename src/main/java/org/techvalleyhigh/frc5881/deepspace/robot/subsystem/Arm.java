package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Arm extends Subsystem {

  //Define motor
  private static WPI_TalonSRX armMotor = new WPI_TalonSRX(5);

  //The maximum and minimum numbers of ticks for how far the arm can move
  private static final int maxTicks = 0;
  private static final int minTicks = 200;

  //TODO: Find cargo and hatch ticks
  public static final int cargoTicks = 10;
  public static final int hatchTicks = 15;


  @Override
  protected void initDefaultCommand() {
  }

  /**Create the subsystem with a default name*/
  public Arm (){
    super ();
    init ();
  }

/**
 * Initialize SmartDashboard and other local variables
 */
  public void init() {
    armMotor.setName("Arm", "Motor");
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    armMotor.setNeutralMode(NeutralMode.Brake);
    LiveWindow.add(armMotor);
  }

  /**
   * Set the setPoint to stay in between the minimum and maximum amount of ticks
   * If setPoint is less than minTicks, it will be equal to minTicks
   * If setPoint is more than maxTicks, it will be equal to maxTicks
  */
  public void setArmMotor (double setPoint){
    if (setPoint < minTicks) setPoint = minTicks;
    if (setPoint > maxTicks) setPoint = maxTicks;

    armMotor.set(ControlMode.Position, setPoint);
  }

  /**
   * set arm to target cargo or hatch
   */
  public void setToCargoTicks(){
    setArmMotor(cargoTicks);
  }

  public void setToHatchTicks(){
    setArmMotor(hatchTicks);
  }

  /**
   * flip arm setpoint from cargo to hatch and vice versa
   */
  public void flip() {
    if(getSetPoint() == hatchTicks) {
      setToCargoTicks();
    } else if(getSetPoint() == cargoTicks){
      setToHatchTicks();
    }
  }

  /**
   * identifies which mode it is in, cargo or hatch
   * returns if it's true or false
   */
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
