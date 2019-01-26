package org.techvalleyhigh.frc5881.deepspace.robot.utils;

import jaci.pathfinder.PathfinderJNI;
import jaci.pathfinder.Waypoint;

import java.util.Date;

public class PathUtil {
  public void generatePath(double distance, double angle, double finalAngle) {
         Date start = new Date();
         double x = Math.cos(angle) * distance;
         double y = Math.sin(angle) * distance;

         Waypoint[] wps = new Waypoint[]{
                 new Waypoint(0, 0, 0),
                 new Waypoint(x, y, finalAngle)
         };
  }
}
