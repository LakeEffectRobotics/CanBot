package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismObject2d;

public class MechanismFlywheel2d extends MechanismObject2d {

    StringPublisher dummyTypePub;
    // Only line seems to be supported by glass/advantageScope, so use that
    private static String type = "line";

    private double radius;
    private double numSpokes;

    private double currentAngle = 0;
    private long lastUpdate = System.currentTimeMillis();
    private double RPM = 0;

    private MechanismLigament2d[] spokes;

    public MechanismFlywheel2d(String name, double radius, int numSpokes) {
        super(name);

        this.radius = radius;
        this.numSpokes = numSpokes;

        spokes = new MechanismLigament2d[numSpokes];
        for(int i = 0; i < numSpokes; i++){
            MechanismLigament2d spoke = new MechanismLigament2d("Spoke " + i, radius, 0);
            append(spoke);
            spokes[i] = spoke;
        }
        update();
    }

    public synchronized void setRPM(double RPM) {
        this.RPM = RPM;
    }

    public void update(){
        long time = System.currentTimeMillis();
        currentAngle += RPM * 360 * (time - lastUpdate) / 60;
        lastUpdate = time;

        
        double dTheta = 360 / numSpokes;
        for(int i = 0; i < numSpokes; i++) {
            spokes[i].setAngle(i * dTheta + currentAngle);
        }
    }

    @Override
    protected void updateEntries(NetworkTable table) {
        if(dummyTypePub != null){
            dummyTypePub.close();
        }
        dummyTypePub = table.getStringTopic(".type").publishEx(StringTopic.kTypeString, "{\"SmartDashboard\":\"" + type + "\"}");
        dummyTypePub.set(type);
    }

    @Override
    public void close(){
        super.close();
        if(dummyTypePub != null){
            dummyTypePub.close();
        }
        for (MechanismLigament2d spoke : spokes) {
            spoke.close();
        }
    }
}
