package org.yunus.io;

import org.yunus.model.OrbitData;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles exporting orbit data to various formats
 */
public class OrbitDataExporter {

    /**
     * Save orbit data to CSV file
     */
    public static void saveToCSV(List<OrbitData> dataList, String filename) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Write header
            writer.println("Date,X_km,Y_km,Z_km,VX_km_s,VY_km_s,VZ_km_s,Latitude_deg,Longitude_deg,Altitude_km");

            // Write data
            for (OrbitData data : dataList) {
                writer.println(data.toString());
            }
        }
    }

    /**
     * Print summary of first and last entries
     */
    public static void printSummary(List<OrbitData> dataList, int numEntries) {
        System.out.println("\nFirst " + numEntries + " entries:");
        for (int i = 0; i < Math.min(numEntries, dataList.size()); i++) {
            System.out.println(dataList.get(i).toReadableString());
        }

        System.out.println("\nLast " + numEntries + " entries:");
        for (int i = Math.max(0, dataList.size() - numEntries); i < dataList.size(); i++) {
            System.out.println(dataList.get(i).toReadableString());
        }
    }
}
