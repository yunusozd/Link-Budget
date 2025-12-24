package org.yunus.propagation;

import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.bodies.BodyShape;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.frames.*;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.SGP4;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.time.AbsoluteDate;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;
import org.orekit.attitudes.AttitudeProvider;
import org.orekit.attitudes.NadirPointing;
import org.yunus.Main;
import org.yunus.model.OrbitData;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles satellite orbit propagation using SGP4
 */
public class OrbitPropagator {
    private final TLE tle;
    private final SGP4 propagator;
    private final BodyShape earth;
    private final Frame temeFrame;
    private final Frame itrfFrame;
    private final Frame gcrfFrame;

    public OrbitPropagator(TLE tle, double satelliteMass) throws Exception {
        this.tle = tle;

        // Initialize Earth model
        this.earth = new OneAxisEllipsoid(
                Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                Constants.WGS84_EARTH_FLATTENING,
                FramesFactory.getITRF(IERSConventions.IERS_2010, true)
        );

        // Initialize frames
        /*
        TEME frame is only used for TLE propagation using SPG4. Anything else is unrecommended.
         */
        this.temeFrame = FramesFactory.getTEME();
        // Terrestrial Reference Frame
        this.itrfFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);

        // Create attitude provider and propagator
        AttitudeProvider attitudeProvider = new NadirPointing(
                FramesFactory.getEME2000(),
                earth
        );

        this.propagator = new SGP4(tle, attitudeProvider, satelliteMass);
    }

    /**
     * Get orbital period in seconds
     */
    public double getOrbitalPeriod() {
        double meanMotion = tle.getMeanMotion();  // rad/s
        return (2.0 * Math.PI) / meanMotion;  // seconds
    }

    /**
     * Propagate orbit and collect data at regular timesteps
     */
    public List<OrbitData> propagate(int numOrbits, double timestepSeconds) throws Exception {
        List<OrbitData> dataList = new ArrayList<>();

        double orbitalPeriod = getOrbitalPeriod();
        double totalDuration = numOrbits * orbitalPeriod;
        AbsoluteDate startDate = tle.getDate();

        for (double t = 0; t <= totalDuration; t += timestepSeconds) {
            AbsoluteDate currentDate = startDate.shiftedBy(t);
            OrbitData data = propagateToDate(currentDate);
            dataList.add(data);
        }

        return dataList;
    }

    /**
     * Propagate to a specific date and return orbit data
     */
    public OrbitData propagateToDate(AbsoluteDate date) throws Exception {
        SpacecraftState state = propagator.propagate(date);

        // Get position and velocity in TEME (inertial frame)
        PVCoordinates pvTeme = state.getPVCoordinates(temeFrame);
        Vector3D position = pvTeme.getPosition();
        Vector3D velocity = pvTeme.getVelocity();

        // Get geodetic coordinates (lat/lon/alt)
        PVCoordinates pvItrf = state.getPVCoordinates(itrfFrame);
        GeodeticPoint geoPoint = earth.transform(pvItrf.getPosition(), itrfFrame, date);

        return new OrbitData(
                date,
                position,
                velocity,
                geoPoint.getLatitude(),
                geoPoint.getLongitude(),
                geoPoint.getAltitude() / 1000.0  // Convert to km
        );
    }

    public SGP4 getPropagator() {
        return propagator;
    }
}
