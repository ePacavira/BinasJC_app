package ao.co.isptec.aplm.binasjc_app;

public class model_users {

    private Long id;
    private String userName;
    private String email;
    private String password;

    public model_users(String email, Long id, String password, String userName) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    public model_users(String email, Long id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
