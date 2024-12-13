package ao.co.isptec.aplm.binasjc_app;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataUtil {

    private static final String BASE_URL = "http://10.0.2.2:8081/"; // Substitua pelo URL do seu servidor

    // Método para buscar dados do usuário e atualizar o TextView
    public static void fetchAndSetUserField(Context context, int userId, TextView textView, String fieldName) {
        // Configuração do Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Chamando o endpoint para obter o usuário pelo ID
        Call<User> call = apiService.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        // Atualiza o TextView com o valor do campo especificado
                        switch (fieldName.toLowerCase()) {
                            case "nome":
                                textView.setText("Olá, " + user.getNome());
                                break;
                            case "email":
                                textView.setText(user.getEmail());
                                break;
                            // Adicione outros campos conforme necessário
                            default:
                                textView.setText("Campo não encontrado");
                                break;
                        }
                    } else {
                        Toast.makeText(context, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Erro ao buscar dados: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
