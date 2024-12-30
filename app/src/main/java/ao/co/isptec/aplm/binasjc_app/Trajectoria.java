package ao.co.isptec.aplm.binasjc_app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Trajectoria {
    private int id_trajectory;

    private int idBike;

    private User user;

    private double latitudeInicio;

    private double latitudeFim;

    private double longitudeInicio;

    private double longitudeFim;

    private String stationStart;
    private String stationEnd;

    public int getId_trajectory() {
        return id_trajectory;
    }

    public void setId_trajectory(int id_trajectory) {
        this.id_trajectory = id_trajectory;
    }

    public int getIdBike() {
        return idBike;
    }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getLatitudeInicio() {
        return latitudeInicio;
    }

    public void setLatitudeInicio(double latitudeInicio) {
        this.latitudeInicio = latitudeInicio;
    }

    public double getLatitudeFim() {
        return latitudeFim;
    }

    public void setLatitudeFim(double latitudeFim) {
        this.latitudeFim = latitudeFim;
    }

    public double getLongitudeInicio() {
        return longitudeInicio;
    }

    public void setLongitudeInicio(double longitudeInicio) {
        this.longitudeInicio = longitudeInicio;
    }

    public double getLongitudeFim() {
        return longitudeFim;
    }

    public void setLongitudeFim(double longitudeFim) {
        this.longitudeFim = longitudeFim;
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
}
