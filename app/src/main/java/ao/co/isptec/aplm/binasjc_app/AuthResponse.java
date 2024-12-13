package ao.co.isptec.aplm.binasjc_app;


public class AuthResponse {

    private boolean success;
    private String message;
    private int userId; // Campo para armazenar o ID do usuário
    private String userName;
    private String userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserPontuacao() {
        return userPontuacao;
    }

}
