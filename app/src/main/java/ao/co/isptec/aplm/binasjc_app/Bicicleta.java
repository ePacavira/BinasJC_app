package ao.co.isptec.aplm.binasjc_app;

 enum StatusBicicleta{
     DISPONIVEL,
     RESERVADA,
     EM_USO,
     MANUTENCAO;
}

public class Bicicleta {

    private int idBicicleta;
    private Estacao estacao;
    private int idUser;
    private StatusBicicleta status;



    public int getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(int idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public StatusBicicleta getStatus() {
        return status;
    }

    public void StatusBicicleta(StatusBicicleta status) {
        this.status = status;
    }
}
