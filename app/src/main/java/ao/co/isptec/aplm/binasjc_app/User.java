package ao.co.isptec.aplm.binasjc_app;

import java.util.ArrayList;
import java.util.List;

public class User {
        private int id;
        private String nome;
        private String email;
        private String palavra_passe;
        private int pontuacao;
        private List<Trajectoria> trajectories = new ArrayList<>();

        public void addTrajectory(Trajectoria trajectory) {
            this.trajectories.add(trajectory);
        }
        // Getters e setters
        public int getId() {
            return id;
        }

        public void setId(int id) { this.id = id; }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPalavra_passe() {
            return palavra_passe;
        }

        public void setPalavra_passe(String palavra_passe) {
            this.palavra_passe = palavra_passe;
        }

        public int getPontuacao() {
            return pontuacao;
        }

        public void setPontuacao(int pontuacao) {
            this.pontuacao = pontuacao;
        }

    /*public List<Trajectory> getTrajectories() {
        return trajectories;
    }

    public void setTrajectories(List<Trajectory> trajectories) {
        this.trajectories = trajectories;
    }*/
}
