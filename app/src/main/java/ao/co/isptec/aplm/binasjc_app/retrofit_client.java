package ao.co.isptec.aplm.binasjc_app;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofit_client {

    private static final String TAG = "RetrofitClient";
    private static final String BASE_URL = "http://192.168.0.1:8080/api/";
    private static Retrofit retrofit = null;
    private static final int TIMEOUT = 60;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
                Log.d(TAG, "API Log: " + message);
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        okhttp3.Request original = chain.request();

                        // Log da requisição
                        Log.d(TAG, "Fazendo requisição para: " + original.url());

                        okhttp3.Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        try {
                            okhttp3.Response response = chain.proceed(request);
                            // Log da resposta
                            Log.d(TAG, "Resposta recebida: " + response.code());
                            return response;
                        } catch (Exception e) {
                            Log.e(TAG, "Erro na requisição: " + e.getMessage());
                            throw e;
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static service_api getApiService() {
        return getClient().create(service_api.class);
    }

    public static void clearInstance() {
        retrofit = null;
    }

}
