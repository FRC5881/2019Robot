package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;

public class DriveControl extends Subsystem {
  @Override
  protected void initDefaultCommand() {}

  private static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(1);
  private static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(2);
  private static WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(3);
  private static WPI_TalonSRX backRightMotor = new WPI_TalonSRX(4);

  private DifferentialDrive robotDrive;

  public DriveControl() {
    super();

    init();
  }

  public void init(){
    SpeedControllerGroup m_left = new SpeedControllerGroup (frontLeftMotor);
    SpeedControllerGroup m_right = new SpeedControllerGroup(frontRightMotor);
    robotDrive = new DifferentialDrive(m_right, m_left);
  }

  public void rawArcadeDrive(double turn, double speed){
    robotDrive.arcadeDrive(speed, turn, true);
  }

  public void arcadeJoystickInputs (){
    double turn = Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_X_AXIS);
    double speed = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_Y_AXIS);

    if(Math.abs(turn) < 0.1 || Math.abs(speed)< 0.1) {
      return;
    }

    rawArcadeDrive(turn, speed);
  }

}
