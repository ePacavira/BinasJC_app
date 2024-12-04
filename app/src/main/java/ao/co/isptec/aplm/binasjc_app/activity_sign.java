package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_sign extends AppCompatActivity {

    EditText txtnamesign, txtemailsign, txtpasssign, txtconfpasssign;
    Button btnsign1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // Ajusta as margens para dispositivos com barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincula os elementos da interface
        txtnamesign = findViewById(R.id.txtNameSign);
        txtemailsign = findViewById(R.id.txtEmailSign);
        txtpasssign = findViewById(R.id.txtPassSign);
        txtconfpasssign = findViewById(R.id.txtConfPassSign);
        btnsign1 = findViewById(R.id.btn_Sign1);

        // Configura o botão de registro
        btnsign1.setOnClickListener(v -> {
            if (!validateFields()) {
                return; // Se a validação falhar, sai.
            }

            // Cria o objeto User com os dados do formulário
            User user = new User();
            user.setNome(txtnamesign.getText().toString().trim());
            user.setEmail(txtemailsign.getText().toString().trim());
            user.setPalavra_passe(txtpasssign.getText().toString().trim());
            user.setPontuacao(10);

            // Faz a requisição para a API
            AuthService authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
            Call<AuthResponse> call = authService.signUp(user);

            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        Toast.makeText(activity_sign.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if (authResponse.isSuccess()) {
                            // Navega para a tela de login ou próxima tela
                            Intent intent = new Intent(activity_sign.this, activity_login.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(activity_sign.this, "Falha no registro: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Toast.makeText(activity_sign.this, "Erro na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", t.getMessage());
                }
            });
        });


        // Navegação para a Activity de Login
        TextView txtLog = findViewById(R.id.txtLogin);
        txtLog.setOnClickListener(v -> {
            Intent intent = new Intent(activity_sign.this, activity_login.class);
            startActivity(intent);
        });
    }

    // Valida os campos do formulário
    private boolean validateFields() {
        boolean valid = true;

        if (txtnamesign.getText().toString().trim().isEmpty()) {
            txtnamesign.setError("O campo Nome Completo está vazio!");
            valid = false;
        }

        String email = txtemailsign.getText().toString().trim();
        if (email.isEmpty()) {
            txtemailsign.setError("O campo Email está vazio!");
            valid = false;
        } else if (!support_string_helper.regexEmailValidationPattern(email)) {
            txtemailsign.setError("Este email é inválido!");
            valid = false;
        }

        String password = txtpasssign.getText().toString();
        String confirmPassword = txtconfpasssign.getText().toString();
        if (password.isEmpty()) {
            txtpasssign.setError("O campo Palavra-Passe está vazio!");
            valid = false;
        }
        if (!password.equals(confirmPassword)) {
            txtconfpasssign.setError("As palavras-passe são diferentes!");
            valid = false;
        }

        return valid;
    }

    // Método para registrar o usuário
    private void register() {
        String name = txtnamesign.getText().toString().trim();
        String email = txtemailsign.getText().toString().trim();
        String password = txtpasssign.getText().toString().trim();

        // Desabilita o botão para evitar cliques repetidos
        btnsign1.setEnabled(false);
    }
}
