package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_login extends AppCompatActivity {
    private EditText emailAdress;
    private EditText password;

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
        emailAdress = findViewById(R.id.txtEmailLogin);
        password = findViewById(R.id.txtPassLogin);

        // Botão de navegação para o login do Usuário
        Button btnLog = findViewById(R.id.btn_Login);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate(emailAdress,password)){
                    Intent intent = new Intent(activity_login.this, activity_main.class); // Nome correto da Activity de destino
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Analise os dados iseridos",Toast.LENGTH_SHORT);
                }

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

    private boolean validate(EditText emailAdress, EditText password){
        boolean isValid = true;

        // Validação do e-mail
        String emailInput = emailAdress.getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailAdress.setError("Por favor, insira um e-mail.");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailAdress.setError("Por favor, insira um e-mail válido.");
            isValid = false;
        }

        // Validação da senha
        String pass = password.getText().toString();
        if (pass.isEmpty()) {
            password.setError("Por favor, insira sua senha.");
            isValid = false;
        }

        return isValid;
    }
/*
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
    
 */
}