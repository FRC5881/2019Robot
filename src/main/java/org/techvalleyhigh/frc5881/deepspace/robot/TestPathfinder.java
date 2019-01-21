package org.techvalleyhigh.frc5881.deepspace.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.Date;

public class TestPathfinder {
  private static double TIMESTEP = 0.05;   //seconds
  private static double MAX_VELOCITY = 10; // m/s
  private static double MAX_ACCEL = 6;     // m/s/s
  private static double MAX_JERK = 60;     // m/s/s/s

  private static double WHEEL_BASE = 0.6;  // m

  private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
          Trajectory.Config.SAMPLES_FAST, TIMESTEP, MAX_VELOCITY, MAX_ACCEL, MAX_JERK);

  public void testNear() {
    Date start = new Date();
    Waypoint[] wps = new Waypoint[] {
            new Waypoint(1.7184030660096394, 7.187396537941996, Math.atan2(0, 3.048)),
            new Waypoint(5.245520487492751, 7.651055166118179, Math.atan2(1.2419427540433503, 1.9539899330282036))
    };

    System.out.println(runTest(start, wps));
    start = new Date();
    System.out.println(runTestNoTank(start, wps));
  }

  public void testMid() {
    Date start = new Date();
    Waypoint[] wps = new Waypoint[] {
            new Waypoint(3.341208264626282, 5.398998972119573, Math.atan2(0.16559236720577974, 0.57957328522023)),
            new Waypoint(5.791975299271824, 7.352988905147776, Math.atan2(1.2253835173227703, -0.016559236720577353))
    };

    System.out.println(runTest(start, wps));
    start = new Date();
    System.out.println(runTestNoTank(start, wps));
  }

  public void testFar() {
    Date start = new Date();
    Waypoint[] wps = new Waypoint[] {
            new Waypoint(6.73585179234477, 6.49190859567772, Math.atan2(1.3909758845285510, 0.23182931408809182)),
            new Waypoint(6.354989347771477,7.667614402838758, Math.atan2(0.8279618360288996, -1.5068905415725977))
    };

    System.out.println(runTest(start, wps));
    start = new Date();
    System.out.println(runTestNoTank(start, wps));
  }

  private String runTest(Date start, Waypoint[] wps) {
    Trajectory trajectory = Pathfinder.generate(wps, config);

    TankModifier modifier = new TankModifier(trajectory).modify(WHEEL_BASE);

    Trajectory left = modifier.getLeftTrajectory();
    Trajectory right = modifier.getRightTrajectory();

    long elapsed = new Date().getTime() - start.getTime();
    return "Dur: " + elapsed + "ms - L/R Segments: " + left.length() + "/" + right.length();
  }

  private String runTestNoTank(Date start, Waypoint[] wps) {
    Trajectory trajectory = Pathfinder.generate(wps, config);

    long elapsed = new Date().getTime() - start.getTime();
    return "Dur: " + elapsed + "ms - Segments: " + trajectory.length();
  }
}
