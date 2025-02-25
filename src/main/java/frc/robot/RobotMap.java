package frc.robot;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;

public class RobotMap {

    private static final int LEFT_DRIVE_1 = 4;
    private static final int LEFT_DRIVE_2 = 3;
    private static final int RIGHT_DRIVE_1 = 1;
    private static final int RIGHT_DRIVE_2 = 2;

    public static final BaseMotorController leftDrive1;
    public static final BaseMotorController leftDrive2;
    public static final BaseMotorController rightDrive1;
    public static final BaseMotorController rightDrive2;

    static {
        leftDrive1 = new VictorSPX(LEFT_DRIVE_1);
        leftDrive2 = new VictorSPX(LEFT_DRIVE_2);
        leftDrive2.follow(leftDrive1);

        rightDrive1 = new VictorSPX(RIGHT_DRIVE_1);
        rightDrive2 = new VictorSPX(RIGHT_DRIVE_2);
        rightDrive2.follow(rightDrive1);
    }
}
