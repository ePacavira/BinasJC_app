package ao.co.isptec.aplm.binasjc_app;

public class User {
        private String nome;
        private String email;
        private String palavra_passe;
        private int pontuacao;

        // Getters e setters
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

}
