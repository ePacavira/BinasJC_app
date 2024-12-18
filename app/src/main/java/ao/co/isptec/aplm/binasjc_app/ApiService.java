package ao.co.isptec.aplm.binasjc_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;
import java.util.Map;

public interface ApiService {

    @GET("/usuarios")
    Call<List<User>> getUsers();

    @GET("usuarios/get/{id}")
    Call<User> getUserById(@Path("id") int userId);

    @POST("auth/signup")
    Call<AuthResponse> signUp(@Body User user);

    @POST("auth/login")
    Call<AuthResponse> login(@Body User user);

    @PUT("usuarios/update/{id}")
    Call<AuthResponse> updateUser(@Path("id") int id, @Body Map<String, String> updatePayload);

    @PUT("auth/update-password/{id}")
    Call<AuthResponse> updatePassword(@Path("id") int userId, @Body PasswordPayload passwordPayload);
}
