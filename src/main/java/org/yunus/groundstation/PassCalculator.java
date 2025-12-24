package org.yunus.groundstation;


import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.bodies.BodyShape;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.frames.TopocentricFrame;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.SGP4;
import org.orekit.time.AbsoluteDate;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;
import org.yunus.model.GroundStation;
import org.yunus.model.StationPassData;

import java.util.Vector;


/**
 * Calculates satellite passes and distances relative to ground stations
 */
public class PassCalculator {
    private final SGP4 propagator;
    private final BodyShape earth;
    private final Frame itrfFrame;

    public PassCalculator(SGP4 propagator) {
        this.propagator = propagator;
        this.earth = new OneAxisEllipsoid(
                Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                Constants.WGS84_EARTH_FLATTENING,
                FramesFactory.getITRF(IERSConventions.IERS_2010, true)
        );
        this.itrfFrame = FramesFactory.getITRF(IERSConventions.IERS_2010, true);
    }


    public StationPassData calculatePass(GroundStation station, AbsoluteDate date) throws Exception {

        GeodeticPoint stationPoint = station.toGeodeticPoint();
        TopocentricFrame topocentricFrame = new TopocentricFrame(
                earth, stationPoint, station.getName()
        );

        SpacecraftState state = propagator.propagate(date);

        PVCoordinates pvTOPO = state.getPVCoordinates();
        Vector3D satPosInTOPO = pvTOPO.getPosition();

        double distance = satPosInTOPO.getNorm() / 1000.0; // in kms

        double elevation = Math.toDegrees(topocentricFrame.getElevation(satPosInTOPO, itrfFrame, date));
        double azimuth = Math.toDegrees(topocentricFrame.getAzimuth(satPosInTOPO, itrfFrame, date));

        if (azimuth < 360.0) { azimuth += 360.0; }

        return new StationPassData(date, station.getName(), distance, elevation, azimuth);

    }
}
