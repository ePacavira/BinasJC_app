package ao.co.isptec.aplm.binasjc_app;

 enum Status{
     DISPONIVEL,
     RESERVADA,
     EM_USO,
     MANUTENCAO;
}
public class Bike {

    private int idBicicleta;
    private Estacao estacao;
    private int idUser;
    private Status status;



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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
