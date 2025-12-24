package org.yunus;

import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;
import org.yunus.analysis.OrbitStatistics;
import org.yunus.analysis.PassStatistics;
import org.yunus.config.OrekitInitializer;
import org.yunus.io.OrbitDataExporter;
import org.yunus.io.PassDataExporter;
import org.yunus.model.OrbitData;
import org.yunus.propagation.OrbitPropagator;
import org.yunus.groundstation.GroundStationManager;
import org.yunus.groundstation.PassCalculator;
import org.yunus.model.GroundStation;
import org.yunus.model.StationPassData;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application entry point
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Configuration
            String orekitDataPath = "D:/orekit-data";
            double satelliteMass = 4.5;  // kg
            int numOrbits = 300;
            double timestepSeconds = 30.0;
            String outputFilename = "iss_orbit_data.csv";

            // Initialize Orekit
            OrekitInitializer.initialize(orekitDataPath);

            // ISS TLE
            String line1 = "1 25544U 98067A   25352.21621920  .00008098  00000+0  15154-3 0  9993";
            String line2 = "2 25544  51.6313 115.3593 0003170 265.6107  94.4519 15.49648163543704";

            TimeScale utc = TimeScalesFactory.getUTC();
            TLE issTLE = new TLE(line1, line2, utc);
            AbsoluteDate startDate = issTLE.getDate();

            // Create propagator
            OrbitPropagator propagator = new OrbitPropagator(issTLE, satelliteMass);

            System.out.println("Orbital Period: " +
                    String.format("%.2f minutes", propagator.getOrbitalPeriod() / 60.0));
            System.out.println("Propagating ISS for " + numOrbits + " orbits...");

            // Propagate orbit
            List<OrbitData> orbitData = propagator.propagate(numOrbits, timestepSeconds);

            System.out.println("Propagation complete! Collected " + orbitData.size() + " data points.");

            // Print summary
            OrbitDataExporter.printSummary(orbitData, 3);

            // Save to CSV
            OrbitDataExporter.saveToCSV(orbitData, outputFilename);
            System.out.println("\nData saved to: " + outputFilename);

            // Print statistics
            OrbitStatistics stats = new OrbitStatistics(orbitData, timestepSeconds);
            stats.printStatistics();

            GroundStationManager stationManager = new GroundStationManager();

            stationManager.addStation("TÜBİTAK UZAY", 39.9334, 32.8597, 938);
            stationManager.addStation("NASA_Houston", 29.5583, -95.0853, 15);

            stationManager.printStations();

            GroundStation tuzay = stationManager.getStation("TÜBİTAK UZAY");
            GroundStation nasa = stationManager.getStation("NASA_Houston");

            PassCalculator passCalculator = new PassCalculator(propagator.getPropagator());

            System.out.println("\n Calculating passes over " + tuzay.getName() + " ...");

            List<StationPassData> passData = new ArrayList<>();
            double duration = numOrbits * propagator.getOrbitalPeriod();

            for (double t = 0; t <= duration; t += timestepSeconds) {
                AbsoluteDate date = startDate.shiftedBy(t);
                StationPassData data = passCalculator.calculatePass(tuzay, date);
                passData.add(data);
            }

            System.out.println("Calculation complete! Collected " + passData.size() + " data points.");

            PassDataExporter.saveToCSV(passData, "iss_ankara_passes.csv");
            System.out.println("\nData saved to: iss_ankara_passes.csv");

            PassDataExporter.printVisiblePasses(passData);

            PassStatistics passStatistics = new PassStatistics(passData);
            passStatistics.printStatistics();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
