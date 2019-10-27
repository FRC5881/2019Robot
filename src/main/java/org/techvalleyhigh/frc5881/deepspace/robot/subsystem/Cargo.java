package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.techvalleyhigh.frc5881.deepspace.robot.OI;
import org.techvalleyhigh.frc5881.deepspace.robot.Robot;
import org.techvalleyhigh.frc5881.deepspace.robot.command.IntakeDrive;

public class Cargo extends Subsystem {
    private WPI_TalonSRX intakeMotor = new WPI_TalonSRX(5);

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeDrive());
    }

    public Cargo() {
        init();
    }

    private void init() {
        intakeMotor.configFactoryDefault();
        intakeMotor.setName("cargo", "Intake Motor");
        intakeMotor.setInverted(true);
    }

    public void readAxisInputs() {
        double fwdSpeed = Robot.oi.driverController.getRawAxis(OI.XBOX_RIGHT_TRIGGER_AXIS);
        double revSpeed = Robot.oi.driverController.getRawAxis(OI.XBOX_LEFT_TRIGGER_AXIS);

        fwdSpeed = OI.applyDeadZone(fwdSpeed, 0.1);
        revSpeed = OI.applyDeadZone(revSpeed, 0.1) * (-1);

        if (fwdSpeed > 0) {
            intakeMotor.set(fwdSpeed);
        } else if (revSpeed < 0) {
            intakeMotor.set(revSpeed);
        } else {
            stopMotor();
        }
    }

    public void startIntake() {
        double intakeSpeed = 0.5d;
        if (intakeMotor.get() != intakeSpeed) {
            intakeMotor.set(intakeSpeed);
        }
    }

    public void startEject() {
        double ejectSpeed = 1;
        if (intakeMotor.get() != ejectSpeed) {
            intakeMotor.set(1);
        }
    }

    public void startReverse() {
        double reverseSpeed = -0.25d;
        if (intakeMotor.get() != reverseSpeed) {
            intakeMotor.set(-0.25);
        }
    }

    public void stopMotor() {
        intakeMotor.set(0);
    }
}
