package ao.co.isptec.aplm.binasjc_app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    //private LatLng startLocation;
    private LatLng endLocation;
    private List <LatLng> intermediatePoints;
    private long startTime;
    private long endTime;
    private String stationStart;
    private String stationEnd;


    public Trajectory(LatLng startLocation) {
        this.intermediatePoints = new ArrayList<>();
        this.startTime = System.currentTimeMillis(); // Timestamp de início
    }

    public void addPoint(LatLng point) {
        intermediatePoints.add(point);
    }

    /*public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }*/

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
        this.endTime = System.currentTimeMillis();
    }

    public List<LatLng> getIntermediatePoints() {
        return intermediatePoints;
    }

    public void setIntermediatePoints(List<LatLng> intermediatePoints) {
        this.intermediatePoints = intermediatePoints;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
