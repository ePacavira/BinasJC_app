package ao.co.isptec.aplm.binasjc_app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Trajectoria {

    private Integer idTrajetoria;
    private ReservaId reserva;
    private UserId user;
    private double latitudeInicio;
    private double longitudeInicio;
    private double latitudeFim;
    private double longitudeFim;

    // Classe interna para representar ID da reserva
    public static class ReservaId {
        private Integer idReserva;

        public ReservaId(Integer idReserva) {
            this.idReserva = idReserva;
        }
    }

    // Classe interna para representar ID do usuário
    public static class UserId {
        private Integer id;

        public UserId(Integer id) {
            this.id = id;
        }
    }

    // Getters e setters


    public Integer getIdTrajetoria() {
        return idTrajetoria;
    }

    public void setIdTrajetoria(Integer idTrajetoria) {
        this.idTrajetoria = idTrajetoria;
    }

    public ReservaId getReserva() {
        return reserva;
    }

    public void setReserva(ReservaId reserva) {
        this.reserva = reserva;
    }

    public UserId getUser() {
        return user;
    }

    public void setUser(UserId user) {
        this.user = user;
    }

    public double getLatitudeInicio() {
        return latitudeInicio;
    }

    public void setLatitudeInicio(double latitudeInicio) {
        this.latitudeInicio = latitudeInicio;
    }

    public double getLongitudeInicio() {
        return longitudeInicio;
    }

    public void setLongitudeInicio(double longitudeInicio) {
        this.longitudeInicio = longitudeInicio;
    }

    public double getLatitudeFim() {
        return latitudeFim;
    }

    public void setLatitudeFim(double latitudeFim) {
        this.latitudeFim = latitudeFim;
    }

    public double getLongitudeFim() {
        return longitudeFim;
    }

    public void setLongitudeFim(double longitudeFim) {
        this.longitudeFim = longitudeFim;
    }
}
