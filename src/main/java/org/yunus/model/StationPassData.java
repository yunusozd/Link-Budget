package org.yunus.model;

import org.orekit.time.AbsoluteDate;


/**
 *  Data for satellite position relative to a ground station at a specific time
 */
public class StationPassData {

    private final AbsoluteDate date;
    private final String stationName;
    private final double distance; // km
    private final double elevation; // degrees
    private final double azimuth; // degrees
    private final boolean isVisible; // elevation > 0 boolean

    public StationPassData(AbsoluteDate date, String stationName, double distance, double elevation, double azimuth) {

        this.date = date;
        this.stationName = stationName;
        this.distance = distance;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.isVisible = elevation > 0;
    }

    public AbsoluteDate getDate() { return date; }
    public double getDistance() { return distance; }
    public double getElevation() { return elevation; }
    public double getAzimuth() { return azimuth; }
    public boolean isVisible() { return isVisible; }

    @Override
    public String toString() {
        return String.format("%s %s %.4f %.4f %.4f %s",
                date.toString(), stationName, distance, elevation, azimuth,
                isVisible ? "VISIBLE" : "BELOW_HORIZON");
    }

    public String toReadableString() {
        return String.format("%s | %s | Dist: %.1f km | El: %.1f° | Az: %.1f° | %s",
                date, stationName, distance, elevation, azimuth,
                isVisible ? "VISIBLE" : "BELOW HORIZON");
    }



}
