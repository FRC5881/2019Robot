package org.techvalleyhigh.frc5881.deepspace.robot.utils;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderJNI;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

import java.util.Date;

/**
 * Methods for turning SmartDashboard values into motion profiles. Use feet for lengths and radians for angles
 */
public class PathUtil {
    /**
     * Default config parameter to use for trajectories
     */
    private static Trajectory.Config defaultConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_FAST, 0.05, 5, 5, 40);

    /**
     * The width of the robot (from left wheels to right wheels)
     */
    // TODO: Find actual base width
    private static final double DRIVE_BASE = 2;

    /**
     * Generate a trajectory crafted by 2 Waypoints
     * @param distance distance from robot to the target (feet)
     * @param angle angle separation from a ray extending forward out of robot to target (Radians)
     * @param finalAngle angle to finish facing once profile is complete (Radians)
     */
    public Trajectory generatePath(double distance, double angle, double finalAngle) {
        // Calculate displacement vector in rectangular form
        double x = Math.cos(angle) * distance;
        double y = Math.sin(angle) * distance;

        // Make waypoints
        Waypoint[] wps = new Waypoint[] {
            // Assume bot is stationary at origin, its direction is 0 degrees
             new Waypoint(0, 0, 0),
            // The bot is going to target (x, y) and once it is there end at finalAngle
             new Waypoint(x, y, finalAngle)
        };

        // Return the calculate trajectory
        return Pathfinder.generate(wps, defaultConfig);
    }
}
