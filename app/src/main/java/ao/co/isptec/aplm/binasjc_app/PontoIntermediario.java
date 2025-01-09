package ao.co.isptec.aplm.binasjc_app;

import com.google.gson.annotations.SerializedName;

public class PontoIntermediario {

    @SerializedName("idPonto")
    private Integer idPonto;

    @SerializedName("trajetoria")
    private Trajectoria trajetoria;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public static class Trajectoria{
        @SerializedName("idTrajetoria")
        private Integer idTrajetoria;

        public Trajectoria(Integer idTrajetoria) {
            this.idTrajetoria = idTrajetoria;
        }

        public Integer getIdTrajetoria() {
            return idTrajetoria;
        }

        public void setIdTrajetoria(Integer idTrajetoria) {
            this.idTrajetoria = idTrajetoria;
        }
    }

    public PontoIntermediario(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(Integer idPonto) {
        this.idPonto = idPonto;
    }

    public Trajectoria getTrajetoria() {
        return trajetoria;
    }

    public void setTrajetoria(Trajectoria trajetoria) {
        this.trajetoria = trajetoria;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
