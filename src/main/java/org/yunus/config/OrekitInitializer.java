package org.yunus.config;

import org.orekit.data.DataContext;
import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import java.io.File;

/**
 * Handles Orekit initialization
 */
public class OrekitInitializer {

    public static void initialize(String orekitDataPath) throws Exception {
        File orekitData = new File(orekitDataPath);

        if (!orekitData.exists() || !orekitData.isDirectory()) {
            throw new IllegalArgumentException(
                    "Orekit data directory not found: " + orekitDataPath);
        }

        DataProvidersManager manager = DataContext.getDefault().getDataProvidersManager();
        manager.addProvider(new DirectoryCrawler(orekitData));

        System.out.println("Orekit initialized with data from: " + orekitDataPath);
    }
}