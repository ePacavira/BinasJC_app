package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {

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

        // Botão de navegação para o login do Usuário
        Button btnLog = findViewById(R.id.btn_Login);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_main.class); // Nome correto da Activity de destino
                startActivity(intent);
            }
        });

        // Botão de navegação para o Registro do Usuário
        TextView txtSign = findViewById(R.id.txtSign);
        txtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_sign.class); // Nome correto da Activity de destino
                startActivity(intent);
            }
        });
    }

    private void login() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Desabilitar o botão enquanto processa o login
        btnLogin.setEnabled(false);

        LoginRequest loginRequest = new LoginRequest(username, password);

        RetrofitClient.getApiService().login(loginRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Reabilitar o botão
                btnLogin.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    // Login bem sucedido
                    try {
                        Intent intent = new Intent(LoginActivity.this, HomeView.class);
                        startActivity(intent);
                        finish(); // Fecha a activity de login
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this,
                                "Erro ao abrir a tela principal: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Login falhou
                    Toast.makeText(LoginActivity.this,
                            "Credenciais inválidas. Tente novamente.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Reabilitar o botão
                btnLogin.setEnabled(true);

                Toast.makeText(LoginActivity.this,
                        "Erro de conexão: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}