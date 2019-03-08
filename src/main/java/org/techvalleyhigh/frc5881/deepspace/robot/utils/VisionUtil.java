package org.techvalleyhigh.frc5881.deepspace.robot.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionUtil {
    /**
     * Angle to turn to face the tape and take measurement
     * @return double relative angle
     */
    public static double getSeparationAngle() {
        return SmartDashboard.getNumber("Separation Angle", 0);
    }

    /**
     * Angle robot must finish trajectory facing to be co-linear with tape
     * @return double relative angle
     */
    public static double getFinalAngle() {
        return SmartDashboard.getNumber("Final Angle", 0);
    }
}
