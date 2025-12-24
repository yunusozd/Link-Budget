package org.yunus.analysis;

import org.yunus.model.OrbitData;
import java.util.List;

/**
 * Calculate and display statistics for orbit data
 */
public class OrbitStatistics {
    private final List<OrbitData> dataList;
    private final double timestepSeconds;

    public OrbitStatistics(List<OrbitData> dataList, double timestepSeconds) {
        this.dataList = dataList;
        this.timestepSeconds = timestepSeconds;
    }

    public double getMinAltitude() {
        return dataList.stream()
                .mapToDouble(OrbitData::getAltitude)
                .min()
                .orElse(0.0);
    }

    public double getMaxAltitude() {
        return dataList.stream()
                .mapToDouble(OrbitData::getAltitude)
                .max()
                .orElse(0.0);
    }

    public double getAverageAltitude() {
        return dataList.stream()
                .mapToDouble(OrbitData::getAltitude)
                .average()
                .orElse(0.0);
    }

    public void printStatistics() {
        System.out.println("\n=== Orbit Statistics ===");
        System.out.printf("Min Altitude: %.2f km\n", getMinAltitude());
        System.out.printf("Max Altitude: %.2f km\n", getMaxAltitude());
        System.out.printf("Avg Altitude: %.2f km\n", getAverageAltitude());
        System.out.printf("Total data points: %d\n", dataList.size());
        System.out.printf("Time span: %.2f minutes\n",
                (dataList.size() - 1) * timestepSeconds / 60.0);
    }
}
