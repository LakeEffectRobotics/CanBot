// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Controls the robot's left and right drive motors.
 *
 * <p>Commands call {@link #setOutput(double, double)} to move the robot. Keeping
 * motor code in this subsystem prevents commands from communicating directly
 * with the motor controllers.
 */
public class Drivetrain extends SubsystemBase {
  
  private BaseMotorController leftLead, rightLead;
  
  /**
   * Creates a drivetrain that controls the lead motors for both sides.
   *
   * <p>The follower motors are configured in {@link RobotMap}.
   */
  public Drivetrain(BaseMotorController leftLead, BaseMotorController rightLead) {
    this.leftLead = leftLead;
    this.rightLead = rightLead;
  }

  @Override
  public void periodic() {
    // The command scheduler calls this method regularly.
  }

  /**
   * Sets the percent output for the left and right sides of the drivetrain.
   *
   * @param leftSpeed a value from -1.0 (full reverse) to 1.0 (full forward)
   * @param rightSpeed a value from -1.0 (full reverse) to 1.0 (full forward)
   */
  public void setOutput(double leftSpeed, double rightSpeed){
    leftLead.set(ControlMode.PercentOutput, leftSpeed);
    rightLead.set(ControlMode.PercentOutput, rightSpeed);
  }

}
