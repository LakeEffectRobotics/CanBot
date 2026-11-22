package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Holds operator controls and converts them into values the robot can use.
 *
 * <p>The controller on USB/driver-station port 0 is shared by the robot program.
 * Speed suppliers here apply a 50 percent limit so the robot starts moving slowly.
 */
public class OI {
    public static final int CONTROLLER_PORT = 0;


    public static final XboxController controller = new XboxController(CONTROLLER_PORT);


    public static final DoubleSupplier leftDriveSupplier = () -> {
        return controller.getLeftY() * 0.50;
    };

    public static final DoubleSupplier rightDriveSupplier = () -> {
        return controller.getRightY() * 0.50;
    };
}
