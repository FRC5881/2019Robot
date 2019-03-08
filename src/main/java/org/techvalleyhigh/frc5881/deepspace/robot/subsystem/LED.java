package org.techvalleyhigh.frc5881.deepspace.robot.subsystem;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Does stuff the the LED's
 */
public class LED extends Subsystem {
  private static DigitalOutput interruptPin = new DigitalOutput(0);
  private static DigitalOutput dataPin1 = new DigitalOutput(1);
  private static DigitalOutput dataPin2 = new DigitalOutput(2);
  private static DigitalOutput dataPin3 = new DigitalOutput(3);
  private static DigitalOutput dataPin4 = new DigitalOutput(4);

  private static final int PULSE_LENGTH = 1000;

  public enum Color {
    NONE(false, false, false, false), // No colors : 0

    LOW_ORANGE(false, false, false, true), // Low elevator + cargo : 1
    MID_ORANGE(false, false, true, false), // Mid elevator + cargo : 2
    HIGH_ORANGE(false, false, true, true), // High elevator + cargo : 3

    LOW_YELLOW(false, true, false, false), // Low elevator + hatch panel : 4
    MID_YELLOW(false, true, false, true), // Mid elevator + hatch panel : 5
    HIGH_YELLOW(false, true, true, false), // High elevator + hatch panel : 6

    FLASH_BLUE(false, true, true, true), // Score Blue : 7
    FLASH_RED(true, false, false, false), // Score Red : 8

    ROTATE_BLUE(true, false, false, true), // Disabled Blue : 9
    ROTATE_RED(true, false, true, false), // Disabled Red : 10

    CHARGE_BLUE(true, false, true, true), // POWERUP Blue : 11
    CHARGE_RED(true, true, false, false), // POPWERUP Red : 12

    VERY_LOW_ORANGE(true, true, false, true), // Floor elevator + cargo : 13
    VERY_LOW_YELLOW(true, true, true, false), // Floor elevator + hatch panel : 14

    RAINBOW(true, true, true, true); // Test Mode (: 15



    private boolean data1, data2, data3, data4;

    Color(boolean data1, boolean data2, boolean data3, boolean data4) {
      this.data1 = data1;
      this.data2 = data2;
      this.data3 = data3;
      this.data4 = data4;
    }

    public boolean isData1() {
      return data1;
    }

    public boolean isData2() {
      return data2;
    }

    public boolean isData3() {
      return data3;
    }

    public boolean isData4() {
      return data4;
    }
  }

  public LED() {
    // Run default subsystem constructor
    super();

    // Run initialization protocol
    init();
  }

  /**
   * Initialize the default command for a subsystem By default subsystems have no default command,
   * but if they do, the default command is set with this method. It is called on all Subsystems by
   * CommandBase in the users program after all the Subsystems are created.
   */
  @Override
  protected void initDefaultCommand() {
  }

  /**
   * Init subsystem variables
   */
  private void init() {
  }

  public void sendLED(Color code) {
    System.out.println("Color! " + code.data1 + " " + code.data2 + " " + code.data3 + " " + code.data4);

    dataPin1.set(code.isData1());
    dataPin2.set(code.isData2());
    dataPin3.set(code.isData3());
    dataPin4.set(code.isData4());

    update();
  }

  public void flashTeam() {
    DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
    if (alliance.equals(DriverStation.Alliance.Red)) {
      flashRed();
    } else if (alliance.equals(DriverStation.Alliance.Blue)) {
      flashBlue();
    }
  }

  public void flashBlue() {
    sendLED(Color.FLASH_BLUE);
    sendLED(Color.NONE);
  }

  public void flashRed()  {
    sendLED(Color.FLASH_BLUE);
    sendLED(Color.NONE);
  }

  public void rotateTeam() {
    DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
    if (alliance.equals(DriverStation.Alliance.Red)) {
      rotateRed();
    } else if (alliance.equals(DriverStation.Alliance.Blue)) {
      rotateBlue();
    }
  }

  public void rotateRed() {
    sendLED(Color.ROTATE_RED);
  }

  public void rotateBlue() {
    sendLED(Color.ROTATE_BLUE);
  }

  public void chargeTeam() {
    DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
    if (alliance.equals(DriverStation.Alliance.Red)) {
      chargeRed();
    } else if (alliance.equals(DriverStation.Alliance.Blue)) {
      chargeBlue();
    }
  }

  public void chargeRed() {
    sendLED(Color.CHARGE_RED);
  }

  public void chargeBlue() {
    sendLED(Color.CHARGE_BLUE);
  }

  public void update() {
    try {
      interruptPin.set(false);
      Thread.sleep(10);
      interruptPin.set(true);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
  }
}
