package ao.co.isptec.aplm.binasjc_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/signup")
    Call<AuthResponse> signUp(@Body User user);

    @POST("/auth/login")
    Call<AuthResponse> login(@Body User user);

}
