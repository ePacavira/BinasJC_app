package ao.co.isptec.aplm.binasjc_app;


public class AuthResponse {

    private boolean success;
    private String message;
    private String userName;
    private int userId; // Campo para armazenar o ID do usuário
    private int userPontuacao; // Número de pontos do usuário

    // Getters e Setters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserPontuacao() {
        return userPontuacao;
    }

}
