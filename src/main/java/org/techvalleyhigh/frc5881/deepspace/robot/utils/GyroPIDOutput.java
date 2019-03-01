package org.techvalleyhigh.frc5881.deepspace.robot.utils;

import edu.wpi.first.wpilibj.PIDOutput;

public class GyroPIDOutput implements PIDOutput {
    private double output = 0;
    /**
     * Set the output to the value calculated by PIDController.
     *
     * @param output the value calculated by PIDController
     */
    @Override
    public void pidWrite(double output) {
        this.output = output;
    }

    public double getOutput() {
        return output;
    }
}
