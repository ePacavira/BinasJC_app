package ao.co.isptec.aplm.binasjc_app;


public class AuthResponse {

    private String message;
    private boolean success = true;

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
