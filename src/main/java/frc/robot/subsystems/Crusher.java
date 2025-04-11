// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.epilogue.Logged.Strategy;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

@Logged(strategy = Strategy.OPT_IN)
public class Crusher extends SubsystemBase {
  
  // Use WPI_TalonSRX for better simulation support
  WPI_TalonSRX talon;

  // 12.75:1 Gearbox, 4:1 belt reduction to flywheel
  private static final double GEAR_REDUCTION = 12.75;
  // 11cm ID to 15cm OD radius ring, 20lbs
  private static final double MOMENT_OF_INERTIA = 0.5 * Units.lbsToKilograms(20) * (0.11 * 0.11 + 0.15 * 0.15);
  
  LinearSystem<N1, N1, N1> sys = LinearSystemId.createFlywheelSystem(DCMotor.getCIM(1), MOMENT_OF_INERTIA, GEAR_REDUCTION);
  FlywheelSim flywheel = new FlywheelSim(sys, DCMotor.getCIM(1));
  TalonSRXSimCollection simTalon;

  Mechanism2d display = new Mechanism2d(3, 3);
  MechanismFlywheel2d flywheelDisplay;

  /** Creates a new Crusher. */
  public Crusher(WPI_TalonSRX talon) {
    this.talon = talon;
    if (Robot.isSimulation()){
      simTalon = talon.getSimCollection();
    }

    MechanismRoot2d root = display.getRoot("Flywheel Root", 1.5, 1.5);
    flywheelDisplay = new MechanismFlywheel2d("Flywheel", 0.15, 6);
    root.append(flywheelDisplay);

    SmartDashboard.putData("Flywheel", display);
  }

  public void setOutput(double output){
    talon.set(ControlMode.PercentOutput, output);
  }

  @Logged(name="Curent (A)")
  public double getCurrent(){
    return talon.getStatorCurrent();
  }

  @Logged(name="Motor Volts (V)")
  public double getOutput(){
    return talon.getMotorOutputVoltage();
  }

  @Logged(name="RPM [SIMULATION]")
  public double simRPM(){
    return flywheel.getAngularVelocityRPM();
  }

  @Override
  public void simulationPeriodic() {
    simTalon.setBusVoltage(12);
    flywheel.setInputVoltage(simTalon.getMotorOutputLeadVoltage());
    flywheel.update(0.02);
    simTalon.setStatorCurrent(flywheel.getCurrentDrawAmps());

    flywheelDisplay.update();
    flywheelDisplay.setRPM(flywheel.getAngularVelocityRPM());
  }
}
