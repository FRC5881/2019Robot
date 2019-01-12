package org.techvalleyhigh.frc5881.deepspace.robot.utils;

public class VisionUtil {
  double getTapeAngle(double ax, double ay, double bx, double by) {
    return Math.atan((bx - ax)/(by - ay));
  }

  double getNeededAngle(double dist, double tapeAngle) {
    return Math.asin((18 * Math.sin(Math.PI - tapeAngle))/dist);
  }

  double getDX(double a, double dist) {
    return Math.sin(a) * dist;
  }

  double getDY(double a, double dist) {
    return Math.cos(a) * dist;
  }
}
