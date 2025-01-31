package frc.robot.subsystems;


import javax.swing.plaf.basic.BasicLookAndFeel;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Camera extends SubsystemBase {
    private NetworkTable table;


    //camera values
    private int maxHistoryLen = 32;
    private int x = 0;
    private int[] xHist = new int[maxHistoryLen];
    private int y = 0;
    private int[] yHist = new int[maxHistoryLen];
    // private int age = 0;
    // private int[] ageHist = new int[maxHistoryLen];
    private int width = 0;
    private int[] widthHist = new int[maxHistoryLen];
    private int height = 0;
    private int[] heightHist = new int[maxHistoryLen];
    private int pushI = 0;

    private GenericEntry XShuffle;
    private GenericEntry YShuffle;
    private GenericEntry AgeShuffle;
    private GenericEntry WidthShuffle;
    private GenericEntry HeightShuffle;
    public ShuffleboardTab tab = Shuffleboard.getTab("my least favourite tab");

    // camera subsystem here
    public Camera() {
        table = NetworkTableInstance.getDefault().getTable("datatable");
       XShuffle = tab
            .add ("cameraX", x)
            .withPosition (0, 0)
            .getEntry();
        YShuffle = tab
            .add ("cameraY", y)
            .withPosition (1, 0)
            .getEntry();
        // AgeShuffle = tab
        //     .add ("cameraAge", age)
        //     .withPosition (2, 0)
        //     .getEntry();
        WidthShuffle = tab
            .add ("cameraWidth", width)
            .withPosition (4, 0)
            .getEntry();
        HeightShuffle = tab
            .add ("cameraHeight", height)
            .withPosition (3, 0)
            .getEntry();
    }
    // //TODO: make this not horrible (get values directly as int)
    public int getX() {
        return x;
    }

    // public int[] getXHist() {
    //     return xHist;
    // }

     public int getY() {
         return y;
    }

    // public int[] getYHist() {
    //     return yHist;
    // }

    // public int getAge() {
    //     return age;
    // }

    // public int[] getAgeHist() {
    //     return ageHist;
    // }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    private int[] pushData(int elt, int[] place) {
    /* place element ELT in array PLACE at the 0th index, push existing
      values up one index, remove oldest (highest index) value */
        for(int i = place.length-1; i > 0; i--) {
            place[i] = place[i-1];
        }
        place[0] = elt;
        return place;
    }

    @Override
    public void periodic() {
/* set x,y,age,width,height variable to current value */
        x = (int)Math.round(table.getEntry("x").getDouble(0.0));
        y = (int)Math.round(table.getEntry("y").getDouble(0.0));
            // age = (int)Math.round(table.getEntry("age").getDouble(0.0));
        width = (int)Math.round(table.getEntry("width").getDouble(0.0));
        height = (int)Math.round(table.getEntry("height").getDouble(0.0));
            //push data to history arrays
    //         if(pushI < maxHistoryLen) { // if arrays are not full yet
    //             xHist[pushI] = x;
    //             yHist[pushI] = y;
    //             // ageHist[pushI] = age;
    //             widthHist[pushI] = width;
    //             heightHist[pushI] = height;
    //             pushI++;
    //         } else { //arrays are full
    //             pushData(x, xHist);
    //             pushData(y, yHist);
    //             // pushData(age, ageHist);
    //             pushData(width, widthHist);
    //             pushData(height, heightHist);
    //         }
    //     }
    }


}
