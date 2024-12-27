package ao.co.isptec.aplm.binasjc_app;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Trajectory {
    private int id_trajectory;

    private int idBike;

    private User user;

    private double latitudeStart;

    private double latitudeEnd;

    private double longitudeStart;

    private double longitudeEnd;

    private String stationStart;
    private String stationEnd;

    private float distance;

    private LocalDate trajectoryDate;
    private LocalTime trajectoryTimeStart;
    private LocalTime trajectoryTimeEnd;


   private List<Coordenada> intermediatePoints;

    public int getId_trajectory() {
        return id_trajectory;
    }

    public void setId_trajectory(int id_trajectory) {
        this.id_trajectory = id_trajectory;
    }

    public LocalTime getTrajectoryTimeEnd() {
        return trajectoryTimeEnd;
    }

    public void setTrajectoryTimeEnd(LocalTime trajectoryTimeEnd) {
        this.trajectoryTimeEnd = trajectoryTimeEnd;
    }

    public LocalTime getTrajectoryTimeStart() {
        return trajectoryTimeStart;
    }

    public void setTrajectoryTimeStart(LocalTime trajectoryTimeStart) {
        this.trajectoryTimeStart = trajectoryTimeStart;
    }

    public LocalDate getTrajectoryDate() {
        return trajectoryDate;
    }

    public void setTrajectoryDate(LocalDate trajectoryDate) {
        this.trajectoryDate = trajectoryDate;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getStationEnd() {
        return stationEnd;
    }

    public void setStationEnd(String stationEnd) {
        this.stationEnd = stationEnd;
    }

    public String getStationStart() {
        return stationStart;
    }

    public void setStationStart(String stationStart) {
        this.stationStart = stationStart;
    }

    public double getLongitudeEnd() {
        return longitudeEnd;
    }

    public void setLongitudeEnd(double longitudeEnd) {
        this.longitudeEnd = longitudeEnd;
    }

    public double getLongitudeStart() {
        return longitudeStart;
    }

    public void setLongitudeStart(double longitudeStart) {
        this.longitudeStart = longitudeStart;
    }

    public double getLatitudeEnd() {
        return latitudeEnd;
    }

    public void setLatitudeEnd(double latitudeEnd) {
        this.latitudeEnd = latitudeEnd;
    }

    public double getLatitudeStart() {
        return latitudeStart;
    }

    public void setLatitudeStart(double latitudeStart) {
        this.latitudeStart = latitudeStart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdBike() {
        return idBike;
    }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }
}
