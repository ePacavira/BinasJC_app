package ao.co.isptec.aplm.binasjc_app;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {

    @GET("/usuarios")
    Call<List<User>> getUsers();

    @GET("usuarios/get/{id}")
    Call<User> getUserById(@Path("id") int userId);

    @POST("auth/signup")
    Call<AuthResponse> signUp(@Body User user);

    @POST("auth/login")
    Call<AuthResponse> login(@Body User user);

}
