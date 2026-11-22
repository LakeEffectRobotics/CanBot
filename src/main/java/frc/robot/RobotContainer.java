// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.DriveCommand;

/**
 * Creates and connects the robot's subsystems, commands, and operator controls.
 *
 * <p>A subsystem represents a physical part of the robot, such as the drivetrain.
 * A command describes behavior that uses one or more subsystems. This class
 * connects those pieces when the robot starts.
 */
public class RobotContainer {

  // Create the drivetrain subsystem once. RobotMap supplies the motor controllers.
  private final Drivetrain drivetrain = new Drivetrain(RobotMap.leftDrive1, RobotMap.rightDrive1);

  /** Runs once when the robot program starts. */
  public RobotContainer() {
    // Reserved for adding button and joystick bindings later.
    configureBindings();

    // Run DriveCommand whenever no other command is controlling the drivetrain.
    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, OI.leftDriveSupplier, OI.rightDriveSupplier));
  }

  /**
   * Add controller button actions here.
   *
   * <p>A binding maps a controller event, such as pressing a button, to a command.
   */
  private void configureBindings() {
    // Example: OI.controller.a().onTrue(new MyCommand(drivetrain));
  }

  /**
   * Returns the command that should run during autonomous mode.
   *
   * @return a command to run, or {@code null} when the robot should remain stopped
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
