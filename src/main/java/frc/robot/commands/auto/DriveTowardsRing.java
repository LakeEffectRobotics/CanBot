package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Camera;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.GenericEntry;

public class DriveTowardsRing extends Command {
    Drivetrain drivetrain;
    Camera camera;
    private long timeout;
    private int x;
    private int age;


    public DriveTowardsRing(Drivetrain drivetrain, Camera camera) {
        addRequirements(drivetrain, camera);
        this.drivetrain = drivetrain;
        this.camera = camera;
    }

    @Override
    public void initialize() {
        System.out.println("AUTO - DRIVETOWARDSRING: INIT");
        timeout = System.currentTimeMillis() + 20000;
    }

    @Override
    public void execute() {
        System.out.println("AUTO - DRIVETOWARDSRING: EXECUTE");
        x = camera.getX();
        // age = getArrayAverage(camera.getAgeHist());
            if( x < 75) {
                System.out.println("AUTO - DRIVETOWARDSRING: LEFT");
                /* left */
                drivetrain.setOutput(-0.25, 0.25);
            } else if(x > 75 && x < 175) {
                System.out.println("AUTO - DRIVETOWARDSRING: STRAIGHT");
                /* straight */
                drivetrain.setOutput(0.5, 0.5);
            } else {
                System.out.println("AUTO - DRIVETOWARDSRING: RIGHT");
                /* right */
                drivetrain.setOutput(0.25, -0.25);
            }

    }
    private int getArrayAverage(int[] ar) {
    /* get mean value of int[] AR */
        int val = 0;
        for (int i = 0; i < ar.length; i++) {
            val = val + ar[i];
        }
        return val;
    }
}
