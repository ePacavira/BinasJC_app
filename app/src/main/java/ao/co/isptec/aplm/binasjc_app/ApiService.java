package ao.co.isptec.aplm.binasjc_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface ApiService {

    @GET("/usuarios")
    Call<List<User>> getUsers();

    @GET("usuarios/get/{id}")
    Call<User> getUserById(@Path("id") Integer userId);

    @POST("auth/signup")
    Call<AuthResponse> signUp(@Body User user);

    @POST("auth/login")
    Call<AuthResponse> login(@Body User user);

    @PUT("usuarios/update/{id}")
    Call<AuthResponse> updateUser(@Path("id") int id, @Body Map<String, String> updatePayload);

    @PUT("auth/update-password/{id}")
    Call<AuthResponse> updatePassword(@Path("id") int userId, @Body PasswordPayload passwordPayload);

    @POST("/trajetorias/add")
    Call<Trajectoria> saveTrajectory(@Body Trajectoria trajectorie);

    @GET("/estacoes")
    Call<List<Estacao>>getAllEstacoes();
    @GET
    Call<List<Bicicleta>>getAllBicicletas();

    @POST("/bicicletas/levantar")
    Call<Bicicleta> levantarBicicleta(
            @Query("idReserva") Integer idReserva,
            @Query("idUsuario") Integer idUsuario
    );
    @POST("/bicicletas/devolver")
    Call<Map<String,Object>>devolverBicicleta(
            @Query("idReserva") Integer idReserva,
            @Query("idUsuario") Integer idUsuario,
            @Query("idEstacaoDevolucao") Integer idEstacaoDevolucao
    );

    @GET("/reservas")
    Call<List<Reserva>>getAllReservas();

    @POST("/pontoIntermediario")
    Call<PontoIntermediario>createOrUpdatePontoIntermediario(@Body PontoIntermediario pontoIntermediário);

}
