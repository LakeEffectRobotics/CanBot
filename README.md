# CanBot

CanBot is a small WPILib Java robot project. It is intentionally simple and uses
the WPILib command-based framework.

## What the robot does

- The Xbox controller left joystick Y axis controls the left side of the drivetrain.
- The right joystick Y axis controls the right side.
- Both joystick outputs are limited to 50 percent speed.
- The robot has no camera or vision code.
- If no autonomous command is selected, the robot remains stopped in autonomous.

## Important folders

- `src/main/java/frc/robot/Robot.java` — handles the lifecycle of robot modes.
- `src/main/java/frc/robot/RobotContainer.java` — creates subsystems and connects commands.
- `src/main/java/frc/robot/subsystems/` — code that controls physical robot hardware.
- `src/main/java/frc/robot/commands/` — code that describes robot behavior.
- `src/main/java/frc/robot/RobotMap.java` — motor CAN IDs and motor configuration.
- `src/main/java/frc/robot/OI.java` — controller setup and joystick values.

## Java terms used in this project

- **Class**: A blueprint for a type of object.
- **Object**: An instance created from a class.
- **Method**: An action or function belonging to a class.
- **Subsystem**: Code that owns a physical mechanism and controls its hardware safely.
- **Command**: Code that tells subsystems what to do over time.
- **Supplier**: A method object that provides a value when asked.
- **CAN ID**: The number that identifies a motor controller on the robot's CAN bus.

## Starting point for common changes

### Change controller speed

Edit the `0.50` values in `OI.java`. A value of `1.0` is full speed and `0.0` is stopped.

### Change motor CAN IDs

Edit the constants in `RobotMap.java`. Do not change CAN IDs until the physical
motor wiring and robot configuration have also been checked.

### Add a subsystem

1. Create a class in `src/main/java/frc/robot/subsystems/`.
2. Extend `SubsystemBase`.
3. Add its motor or sensor setup.
4. Create it in `RobotContainer.java`.

### Add an autonomous command

1. Create a command in `src/main/java/frc/robot/commands/`.
2. Declare every subsystem the command uses with `addRequirements(...)`.
3. Return the command from `RobotContainer.getAutonomousCommand()`.

## Build the project

On macOS or Linux:

```sh
./gradlew build
```

If `gradlew` is not executable, run:

```sh
bash ./gradlew build
```
