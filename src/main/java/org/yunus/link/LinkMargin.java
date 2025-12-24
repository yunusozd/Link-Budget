package org.yunus.link;

import org.orekit.attitudes.AttitudeProvider;

import java.util.List;

/**
 * Calculate link margin when visible
 */
public class LinkMargin {

    private final double distance;
    private final AttitudeProvider attitudeProvider;

    public LinkMargin(double distance, AttitudeProvider attitudeProvider) {
        this.distance = distance;
        this.attitudeProvider = attitudeProvider;
    }

    public double LinkMargin(double distance, AttitudeProvider attitudeProvider) {

        double c = 299792458.0; // meters per second
        double k = -288.60;
        double F = Math.pow(10, 1);
        double dataRate = 1 * Math.pow(10.0, 6.0);   // Bits per second

        double frequency = 2.205 * Math.pow(10.0, 9.0);
        double wavelength = c / frequency;

        // FSPL
        double FSPL = 20 * Math.log10(4 * Math.PI * distance / wavelength);

        System.out.println("Calculated free space path loss is " + FSPL + " dB");

        // Transmit Power
        double transmitPowerWatts = 2;      // watts
        double transmitPower = 10 * Math.log10(transmitPowerWatts); // dBW

        // Transmit cable loss
        double cableLossPerMeter = 0.2; // dB/m
        double cableLength = 0.005; // meters

        // Transmitter Antenna Gain

        double transmitAntennaGain =

    }


}
