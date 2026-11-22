// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

/**
 * Runs the drivetrain for a short timed maneuver during autonomous mode.
 *
 * <p>The command uses {@link Drivetrain#setOutput(double, double)} so it does
 * not communicate directly with motor controllers.
 */
public class DriveAuto extends Command {
  private final Drivetrain drivetrain;
  private final Timer timer = new Timer();

  /** Creates an autonomous command that uses the drivetrain. */
  public DriveAuto(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;

    // Keep other drivetrain commands from using the subsystem at the same time.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
    drivetrain.setOutput(-.2, 0.2);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.hasElapsed(1.0)) {
      drivetrain.setOutput(0.0, 0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    drivetrain.setOutput(0.0, 0.0);
  }
}