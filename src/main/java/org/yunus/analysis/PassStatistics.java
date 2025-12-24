package org.yunus.analysis;


import org.yunus.model.StationPassData;

import java.util.List;

/**
 * Calculate statistics for ground station passes
 */
public class PassStatistics {
    private final List<StationPassData> dataList;

    public PassStatistics(List<StationPassData> dataList) {
        this.dataList = dataList;
    }

    public double getMinDistance() {
        return dataList.stream()
                .mapToDouble(StationPassData::getDistance)
                .min()
                .orElse(0.0);
    }

    public double getMaxElevation() {
        return dataList.stream()
                .mapToDouble(StationPassData::getElevation)
                .max()
                .orElse(0.0);
    }

    public long getVisibleCount() {
        return dataList.stream()
                .filter(StationPassData::isVisible)
                .count();
    }

    public void printStatistics() {
        System.out.println("\n=== Pass Statistics ===");
        System.out.printf("Min Distance: %.2f km\n", getMinDistance());
        System.out.printf("Max Elevation: %.2f°\n", getMaxElevation());
        System.out.printf("Visible Points: %d / %d (%.1f%%)\n",
                getVisibleCount(), dataList.size(),
                100.0 * getVisibleCount() / dataList.size());
    }

}
