package org.yunus.model;


import org.hipparchus.geometry.euclidean.threed.Rotation;
import org.hipparchus.geometry.euclidean.threed.RotationConvention;
import org.hipparchus.geometry.euclidean.threed.RotationOrder;
import org.hipparchus.geometry.euclidean.threed.Vector3D;


/**
 * Satellite antenna configuration with position and orientation of the antenna with respect to the body frame and
 */
public class AntennaConfiguration {
    private final String name;
    private final Vector3D antennaPosition;
    private final Rotation orientation;

    public AntennaConfiguration(String name, double x, double y, double z,
                                double yaw, double pitch, double roll) {

        this.name = name;
        this.antennaPosition = new Vector3D(x, y, z);
        this.orientation = new Rotation(
                RotationOrder.ZYX,
                RotationConvention.FRAME_TRANSFORM,
                Math.toRadians(yaw),
                Math.toRadians(pitch),
                Math.toRadians(roll)
        );
    }
    public AntennaConfiguration(String name, double x, double y, double z) {
       this(name, x, y, z, 0,0, 0);
    }

    public static AntennaConfiguration createPointingAntenna(String name, Vector3D position, Vector3D pointingDirection) {

        // This creates a rotation to align antenna pointing direction to body z axis (nadir)
        // I don't really understand what is going on here but okay. CHECK THIS
        Rotation rot = new Rotation(Vector3D.PLUS_K, pointingDirection);

        return new AntennaConfiguration(
                name, position.getX(), position.getY(), position.getZ(),
                0, 0, 0
        ) {
            @Override
            public Rotation getOrientation() {
                return  rot;
            }
        };

    }

}
