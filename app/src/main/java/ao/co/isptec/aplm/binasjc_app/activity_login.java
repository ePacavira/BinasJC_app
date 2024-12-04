package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os elementos da UI
        txtEmail = findViewById(R.id.txtEmailLogin);
        txtPassword = findViewById(R.id.txtPassLogin);
        btnLogin = findViewById(R.id.btn_Login);

        // Configura o botão de login
        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            // Validação dos campos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity_login.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.setError("Email inválido!");
                return;
            }

            // Cria o objeto User com os dados de login
            User loginUser = new User();
            loginUser.setEmail(email);
            loginUser.setPalavra_passe(password);

            // Cria a instância Retrofit
            AuthService authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
            Call<AuthResponse> call = authService.login(loginUser);

            // Faz a requisição assíncrona
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();

                        // Verifica se o login foi bem-sucedido
                        if (authResponse.isSuccess()) {
                            Toast.makeText(activity_login.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            // Navega para a próxima Activity (activity_main)
                            Intent intent = new Intent(activity_login.this, activity_main.class);
                            startActivity(intent);
                            finish();  // Finaliza a activity de login
                        } else {
                            Toast.makeText(activity_login.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity_login.this, "Erro no servidor. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                    Toast.makeText(activity_login.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Botão de navegação para o Registro do Usuário
        TextView txtSign = findViewById(R.id.txtSign);
        txtSign.setOnClickListener(v -> {
            Intent intent = new Intent(activity_login.this, activity_sign.class); // Nome correto da Activity de destino
            startActivity(intent);
        });
    }
}