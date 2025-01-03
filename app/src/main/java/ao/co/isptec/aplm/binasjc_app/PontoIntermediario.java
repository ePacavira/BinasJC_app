package ao.co.isptec.aplm.binasjc_app;

public class PontoIntermediario {
    private Integer idPonto;
    private TrajetoriaRef trajetoria;
    private double latitude;
    private double longitude;

    public static class TrajetoriaRef {
        private Integer idTrajetoria;

        public TrajetoriaRef(Integer idTrajetoria) {
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

    public TrajetoriaRef getTrajetoria() {
        return trajetoria;
    }

    // Update the setter to accept TrajetoriaRef instead of Trajectoria
    public void setTrajetoria(TrajetoriaRef trajetoria) {
        this.trajetoria = trajetoria;
    }
}
