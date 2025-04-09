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

    private int i;
    private boolean isCentered;

    public DriveTowardsRing(Drivetrain drivetrain, Camera camera) {
        addRequirements(drivetrain, camera);
        this.drivetrain = drivetrain;
        this.camera = camera;
    }

    @Override
    public void initialize() {
        System.out.println("AUTO - DRIVETOWARDSRING: INIT");
        timeout = System.currentTimeMillis() + 20000;
        i = 0;
        isCentered = false;
    }

    @Override
    public void execute() {
        x = camera.getX();
        isCentered = (x >= 106 && x < 212 );
        // TODO: do something if lock is lost
        if (!isCentered) { // only periodically stop if camera is not centered
            if ( i >= 25 ) {
                drivetrain.setOutput(0, 0);
            if ( i >= 50 ) {
                i = 0;
            }
            i += 1;
            return; // if i is between 25 and 50, dont move
        }
        }
         System.out.println("AUTO - DRIVETOWARDSRING: EXECUTE");
        // age = getArrayAverage(camera.getAgeHist());
            if( x < 53) {
                System.out.println("AUTO - DRIVETOWARDSRING: LEFT");
                drivetrain.setOutput(0.35, -0.35);
            } else if( x >= 53 && x < 106) {
                drivetrain.setOutput(0.25, 0.35);
                System.out.println("AUTO - DRIVETOWARDSRING: SLIGHT LEFT");
            } else if(x >= 106 && x < 212) {
                System.out.println("AUTO - DRIVETOWARDSRING: STRAIGHT");
                drivetrain.setOutput(0.25, 0.25);
            } else if( x >= 212 && x < 265) {
                isCentered = true;
                System.out.println("AUTO - DRIVETOWARDSRING: SLIGHT RIGHT");
                drivetrain.setOutput(0.35, 0.25);
            } else {
                System.out.println("AUTO - DRIVETOWARDSRING: RIGHT");
                drivetrain.setOutput(-0.35, 0.35);
            }
            i += 1;
    }
    private int getArrayAverage(int[] ar) {
    /* get mean value of int[] AR */
        int val = 0;
        for (int i = 0; i < ar.length; i++) {
            val = val + ar[i];
        }
        return val;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setOutput(0.0, 0.0);
    }

    /* returns true when 'timeout' has been reached */
    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() >= timeout;
    }
}
