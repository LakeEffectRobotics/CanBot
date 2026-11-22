# From Blocks to Java Autonomous

## Lesson at a glance

**Audience:** Students familiar with basic robot controls; no prior command-based programming required.

**Time:** 60-75 minutes, including a supervised robot test.

**Goal:** Trace a timed drivetrain action from blocks, through OnBot Java, to this project's WPILib command-based robot code.

**Students will be able to:**

- Describe the order, power values, and duration of the block program.
- Match blocks to Java method calls and explain the role of `waitForStart()`.
- Trace how WPILib schedules an autonomous command and stops the drivetrain.
- Change a timed action and predict its effects before testing.

**Materials:** The [block screenshot](<Screenshot%202026-11-21%20at%207.03.47%E2%80%AFPM.png>), [OnBot Java example](MyFIRSTJavaOpMode.java), this project, and a supervised robot or simulator. For hardware testing, use a clear area, secure the robot with its wheels off the ground, and have an adult ready to disable it.

## 1. Read the blocks (10 minutes)

Ask students to read the blocks from top to bottom and write down what each action does:

1. Set `motorLeft` power to `-1` and `motorRight` power to `1`.
2. Wait 1,000 milliseconds, or one second.
3. Set both motor powers to `0`.
4. Wait for start, then repeat the empty loop while the op mode is active.

**Discuss:** What do `-1`, `1`, and `0` mean? They are commanded reverse, forward, and stopped output, respectively; whether the robot moves straight, turns, or travels in a particular direction depends on motor mounting and configuration. Why does the program set both motors to zero?

**Important:** In the screenshot, the movement is placed *before* `waitForStart()`. It is not a reliable example of movement after the start signal. Real robot safety and enable behavior depend on the platform; do not rely on pre-start movement. For a real autonomous routine, put movement after the start signal.

## 2. Connect blocks to OnBot Java (15 minutes)

Open [MyFIRSTJavaOpMode.java](MyFIRSTJavaOpMode.java). Have students locate these corresponding statements:

| Blocks | OnBot Java | Meaning |
| --- | --- | --- |
| Set left power to `-1` | `motorLeft.setPower(-1);` | Command the left motor. |
| Set right power to `1` | `motorRight.setPower(1);` | Command the right motor. |
| Sleep 1,000 milliseconds | `sleep(1000);` | Pause this op mode for one second. |
| Set both powers to `0` | `motorLeft.setPower(0);` and `motorRight.setPower(0);` | Stop both motors. |
| Wait for start | `waitForStart();` | Wait for the start signal. |
| Repeat while active | `while (opModeIsActive())` | Run the loop while the op mode remains active. |

The source writes `(-1)` instead of `-1`; they mean the same thing. Before the motor calls, `hardwareMap.get(...)` looks up configured devices by name. `frontLeft`, `frontRight`, and the sensors are initialized but are not used in this action. This example represents a different robot platform from the WPILib project, so its hardware names and APIs are not interchangeable.

**Student task:** Mark the motor-action lines and `waitForStart()` in the source. Sketch where the motor-action block would go to run after the start signal. Discuss checking that the op mode is still active after waiting and ensuring the motors stop if the operation is interrupted.

## 3. Follow the WPILib path (20 minutes)

In this project, the same general action is split across several classes. Trace it in this order:

1. [Robot.java](../src/main/java/frc/robot/Robot.java): `robotPeriodic()` runs the command scheduler. When autonomous begins, `autonomousInit()` obtains and schedules the autonomous command. `teleopInit()` cancels it when teleop starts.
2. [RobotContainer.java](../src/main/java/frc/robot/RobotContainer.java): `getAutonomousCommand()` creates `DriveAuto` with the drivetrain. The container also sets the normal joystick drive command as the drivetrain's default.
3. [DriveAuto.java](../src/main/java/frc/robot/commands/auto/DriveAuto.java): `addRequirements(drivetrain)` claims the subsystem. `initialize()` restarts the timer and sets left output to `-0.2` and right output to `0.2`. The scheduler repeatedly calls `execute()`, which commands zero after one second. `end()` stops the timer and motors when the command is canceled or interrupted.
4. [Drivetrain.java](../src/main/java/frc/robot/subsystems/Drivetrain.java): `setOutput(leftSpeed, rightSpeed)` sends percent outputs to the two lead motor controllers.
5. [RobotMap.java](../src/main/java/frc/robot/RobotMap.java): constructs the lead controllers and configures the additional motors to follow them.

**Compare:** The sample block and OnBot programs use `-1` and `1` (full output); this WPILib command currently uses `-0.2` and `0.2` (20% output). All three use a one-second interval and then command zero, but their start timing differs: the WPILib command is scheduled on entering autonomous, while the supplied OnBot example places its movement before `waitForStart()`.

Unlike `sleep(1000)`, the WPILib timer does not pause the scheduler. The command keeps its drivetrain requirement after the first second, holding the motors at zero until interrupted. That prevents the default joystick command from taking control during the rest of autonomous. When teleop starts, cancellation calls `end()` and the default drive command can resume.

**Check for understanding:** What would happen if the command released the drivetrain after one second? The default command could run again, including during autonomous. Why stop in `end()` as well as `execute()`? The command might be interrupted before its timer reaches one second.

## 4. Predict, change, test (15-20 minutes)

1. With the robot disabled, predict what would change if `timer.hasElapsed(1.0)` became `timer.hasElapsed(0.5)`. Write the prediction before editing.
2. Make the change and build the project with `./gradlew compileJava` from the project root.
3. Under supervision, run autonomous in a simulator or with the robot secured and wheels off the ground. Confirm both sides stop after about half a second and remain stopped, including with the joystick moved. Disable early in a separate trial and confirm the outputs stop.
4. Restore the original duration and build again. Record any difference between commanded motor signs and observed wheel directions; do not assume signs alone determine robot travel.

The scheduler runs periodically, so stopping occurs on a scheduler iteration after the threshold, not necessarily at the exact millisecond. Do not test by placing body parts near moving wheels or by letting an unsecured robot drive in a crowded area.

## Assessment and answer guide

Ask students to answer independently:

1. How many seconds is `sleep(1000)`? **One second.**
2. Where does the supplied OnBot example place its motor action relative to `waitForStart()`? **Before it.**
3. Which WPILib method chooses the autonomous command? **`RobotContainer.getAutonomousCommand()`.**
4. Why does `DriveAuto` require the drivetrain? **To prevent other commands from controlling it at the same time.**
5. What stops the motors if autonomous is canceled early? **`DriveAuto.end()`.**
6. Why does the command keep running after one second? **To hold zero output and keep the default drive command from taking over during autonomous.**

**Extension:** Plan a second timed motion using a command sequence. Describe how each step ends, how the drivetrain stays claimed during autonomous, and how interruption stops the motors before implementing or testing it.