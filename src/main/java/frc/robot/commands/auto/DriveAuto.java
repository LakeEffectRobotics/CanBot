package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveAuto extends Command {
  private final Drivetrain drivetrain;
  private final Timer timer = new Timer();

  public DriveAuto(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    timer.restart();
    drivetrain.setOutput(-.2, 0.2);
  }

  @Override
  public void execute() {
    if (timer.hasElapsed(1.0)) {
      drivetrain.setOutput(0.0, 0.0);
    }
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    drivetrain.setOutput(0.0, 0.0);
  }
}