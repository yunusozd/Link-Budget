package org.yunus.io;


import org.yunus.model.StationPassData;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Exports ground station pass data
 */
public class PassDataExporter {

    public static void saveToCSV(List<StationPassData> dataList, String filename) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Date, Station, Distance_km, Elevation_deg, Azimuth_deg, Visible");

            for (StationPassData data : dataList) {
                writer.println(data.toString());
            }
        }
    }

    public static void printVisiblePasses(List<StationPassData> dataList) {
        System.out.println("\n Visible Passes (Elevation > 0 degrees)");

        long visibleCount = dataList.stream()
                .filter(StationPassData::isVisible)
                .count();

        System.out.println("Satellite was visible for " + visibleCount + " over the ground station " + dataList.size() + " times.");

    }
}
