package org.yunus.model;

import org.orekit.bodies.GeodeticPoint;


/**
 * Represents a ground station with geographic coordinates
 */
public class GroundStation {

    private final String name;
    private final double latitude;
    private final double longitude;
    private final double altitude;

    public GroundStation(String name, double latitude, double longitude, double altitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getAltitude() { return altitude; }

    public GeodeticPoint toGeodeticPoint() {
        return new GeodeticPoint(
                Math.toRadians(latitude),
                Math.toRadians(longitude),
                altitude
        );
    }

    @Override
    public String toString() {
        return String.format("%s (%.4f deg, %.4f deg, %.4f m",
                name, latitude, longitude, altitude);
    }

}
