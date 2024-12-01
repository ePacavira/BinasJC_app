package ao.co.isptec.aplm.binasjc_app;

public class request_register {

    private String userName;
    private String email;
    private String password;
    private String confirmPassword;

    public request_register(String userName, String password, String email, String confirmPassword) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.confirmPassword = confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
