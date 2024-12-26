package ao.co.isptec.aplm.binasjc_app;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Trajectory {
   // private List <LatLng> intermediatePoints;
    private String stationStart;
    private String stationEnd;

    public Trajectory(String stationEnd, String stationStart) {
        this.stationEnd = stationEnd;
        this.stationStart = stationStart;
    }

    public String getStationStart() {
        return stationStart;
    }

    public void setStationStart(String stationStart) {
        this.stationStart = stationStart;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(String stationEnd) {
        this.stationEnd = stationEnd;
    }

   /* public List<LatLng> getIntermediatePoints() {
        return intermediatePoints;
    }

    public void setIntermediatePoints(List<LatLng> intermediatePoints) {
        this.intermediatePoints = intermediatePoints;
    }*/


    /*public List<Point> convertLatLngToPoints(List<LatLng> latLngs) {
        List<Point> points = new ArrayList<>();
        for (LatLng latLng : latLngs) {
            points.add(new Point(latLng.latitude, latLng.longitude));
        }
        return points;
    }*/

}
