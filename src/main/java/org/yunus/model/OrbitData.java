package org.yunus.model;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.time.AbsoluteDate;

/**
 * Data class representing spacecraft position and velocity at a specific time
 */
public class OrbitData {
    private final AbsoluteDate date;
    private final double x, y, z;           // Position in km (TEME frame)
    private final double vx, vy, vz;        // Velocity in km/s (TEME frame)
    private final double latitude, longitude, altitude;  // Geodetic coordinates

    public OrbitData(AbsoluteDate date, Vector3D position, Vector3D velocity,
                     double latitude, double longitude, double altitude) {
        this.date = date;
        this.x = position.getX() / 1000.0;
        this.y = position.getY() / 1000.0;
        this.z = position.getZ() / 1000.0;
        this.vx = velocity.getX() / 1000.0;
        this.vy = velocity.getY() / 1000.0;
        this.vz = velocity.getZ() / 1000.0;
        this.latitude = Math.toDegrees(latitude);
        this.longitude = Math.toDegrees(longitude);
        this.altitude = altitude;
    }

    // Getters
    public AbsoluteDate getDate() { return date; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public double getVz() { return vz; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAltitude() { return altitude; }

    @Override
    public String toString() {
        return String.format("%s,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f",
                date.toString(), x, y, z, vx, vy, vz, latitude, longitude, altitude);
    }

    public String toReadableString() {
        return String.format("Time: %s | Pos: (%.2f, %.2f, %.2f) km | Alt: %.2f km | Lat: %.2f° Lon: %.2f°",
                date, x, y, z, altitude, latitude, longitude);
    }
}