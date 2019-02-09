package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


/**
 * Subsystem that has everything to do with the arm
 */
public class Arm extends Subsystem {

  //Define motor
  //private static WPI_TalonSRX armMotor = new WPI_TalonSRX(6);

  //The maximum and minimum numbers of ticks for how far the arm can move
  private static final int MAX_TICKS = 0;
  private static final int MIN_TICKS = 200;

  //TODO: Find cargo and hatch ticks
  public static final int CARGO_TICKS = 10;
  public static final int HATCH_TICKS = 15;

  public static final int MAX_ERROR = 50;


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
    //armMotor.setName("Arm", "Motor");
    //armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    //armMotor.setNeutralMode(NeutralMode.Brake);
    //LiveWindow.add(armMotor);
  }

  /**
   * Set the setPoint to stay in between the minimum and maximum amount of ticks
   * If setPoint is less than minTicks, it will be equal to minTicks
   * If setPoint is more than maxTicks, it will be equal to maxTicks
  */
  public void setArmMotor(double setPoint){
    if (setPoint < MIN_TICKS) {
      setPoint = MIN_TICKS;
    }
    if (setPoint > MAX_TICKS) {
      setPoint = MAX_TICKS;
    }

    //armMotor.set(ControlMode.Position, setPoint);
  }

  /**
   * set arm to target cargo
   */
  public void setToCargoTicks(){
    setArmMotor(CARGO_TICKS);
  }

  /**
   * set arm to target hatch
   */
  public void setToHatchTicks(){
    setArmMotor(HATCH_TICKS);
  }

  /**
   * flip arm setpoint from cargo to hatch and vice versa
   */
  public void flip() {
    if(getSetPoint() == HATCH_TICKS) {
      setToCargoTicks();
    } else if(getSetPoint() == CARGO_TICKS){
      setToHatchTicks();
    } else {
      // Default to cargo
      setToCargoTicks();
    }
  }

  /**
   * identifies if arm is in cargo mode
   * @return true if cargo, false otherwise
   */
  public boolean isCargo() {
    return getSetPoint() == CARGO_TICKS;
  }

  /**
   * identifies if arm is in hatch mode
   * @return true if cargo, false otherwise
   */
  public boolean isHatch() {
    return getSetPoint() == HATCH_TICKS;
  }

  /**
   * Gets current setpoint of the arm motor
   * @return double setpoint
   */
  public double getSetPoint() {
    return 0;
    //return armMotor.getClosedLoopTarget();
  }

  /**
   * Gets how far the arm is from the setpoint
   * @return double error
   */
  public double getError() {
    return 0;
    //return armMotor.getClosedLoopError();
  }

  /**
   * Gets the position of the arm
   * @return double position
   */
  public double getPosition() {
    return 0;
    //return armMotor.getSelectedSensorPosition();
  }

}
