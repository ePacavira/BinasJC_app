package ao.co.isptec.aplm.binasjc_app;

import java.util.ArrayList;
import java.util.List;

public class Estacao {
    private int idEstacao;
    private String nome;
    private double latitude;
    private double longitude;

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    /*Relacionamento com Bike
    private List<Bike> bikes = new ArrayList<>();*/
}
