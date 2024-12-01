package ao.co.isptec.aplm.binasjc_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface service_api {

    @POST("/login")
    Call<model_users> login(@Body request_login request);

    @POST("/register")
    Call<model_users> register(@Body request_register request);

}
