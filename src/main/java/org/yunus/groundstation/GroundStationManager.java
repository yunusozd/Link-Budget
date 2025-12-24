package org.yunus.groundstation;

import org.yunus.model.GroundStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages multiple ground stations
 */
public class GroundStationManager {
    private final List<GroundStation> stations;

    public GroundStationManager() {
        this.stations = new ArrayList<>();
    }

    public void addStation(GroundStation station) {
        stations.add(station);
    }

    public void addStation(String name, double lat, double lon, double alt) {
        stations.add(new GroundStation(name, lat, lon, alt));
    }

    public List<GroundStation> getStations() {
        return new ArrayList<>(stations);
    }

    public GroundStation getStation(String name) {
        return stations.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void printStations() {
        System.out.println(Arrays.toString(stations.toArray()));

    }
}
