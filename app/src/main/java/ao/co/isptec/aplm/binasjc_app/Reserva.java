package ao.co.isptec.aplm.binasjc_app;

enum StatusReserva {
    EMCURSO,
    CANCELADA,
    FINALIZADA;
}

public class Reserva {
    private int idReserva;
    private int idUser;
    private int idBike;
    private StatusReserva status;

    private int idEstacaoRetirada;

    private int idEstacaoDevolvida;

    public Reserva() {
    }

    public Reserva(int idUser, int idBike, int idEstacaoRetirada) {
        this.idUser = idUser;
        this.idBike = idBike;
        this.idEstacaoRetirada = idEstacaoRetirada;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBike() {
        return idBike;
    }

    public void setIdBike(int idBike) {
        this.idBike = idBike;
    }

    public int getIdEstacaoRetirada() {
        return idEstacaoRetirada;
    }

    public void setIdEstacaoRetirada(int idEstacaoRetirada) {
        this.idEstacaoRetirada = idEstacaoRetirada;
    }

    public int getIdEstacaoDevolvida() {
        return idEstacaoDevolvida;
    }

    public void setIdEstacaoDevolvida(int idEstacaoDevolvida) {
        this.idEstacaoDevolvida = idEstacaoDevolvida;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }
}
