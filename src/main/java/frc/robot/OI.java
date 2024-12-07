package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.XboxController;

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
