package ao.co.isptec.aplm.binasjc_app;

public class Reserva {
    private Long idReserva;

    private User usuario;


    private Bicicleta bicicleta;

    private Estacao estacaoRetirada;

   private Estacao estacaoDevolucao;

    private StatusBicicleta status;

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Estacao getEstacaoRetirada() {
        return estacaoRetirada;
    }

    public void setEstacaoRetirada(Estacao estacaoRetirada) {
        this.estacaoRetirada = estacaoRetirada;
    }

    public Estacao getEstacaoDevolucao() {
        return estacaoDevolucao;
    }

    public void setEstacaoDevolucao(Estacao estacaoDevolucao) {
        this.estacaoDevolucao = estacaoDevolucao;
    }

    public StatusBicicleta getStatus() {
        return status;
    }

    public void setStatus(StatusBicicleta status) {
        this.status = status;
    }
}
