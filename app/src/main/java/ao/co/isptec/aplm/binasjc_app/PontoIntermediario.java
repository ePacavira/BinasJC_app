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

    // Update the setter to accept TrajetoriaRef instead of Trajectoria
    public void setTrajetoria(TrajetoriaRef trajetoria) {
        this.trajetoria = trajetoria;
    }
}
