package ao.co.isptec.aplm.binasjc_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_edit_sign extends AppCompatActivity {
    private EditText name;
    private EditText emailAdress;
    private EditText newPassword;
    private EditText oldPassword;
    private Button recreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_sign);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.txtNameEdit);
        emailAdress = findViewById(R.id.txtEmailEdit);
        newPassword = findViewById(R.id.txtNewPassEdit);
        oldPassword = findViewById(R.id.txtOldPassEdit);
        recreate = findViewById(R.id.btnEditUser);

        // Busca os dados do usuário ao abrir a tela
        fetchUserData();

        recreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(name, emailAdress, newPassword, oldPassword) && checkPass(newPassword, oldPassword)) {
                    updateUserData();
                }
            }
        });
    }

    private void fetchUserData() {
        // Obtém o ID do usuário salvo no SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);

        if (userId == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chama a API para buscar os dados do usuário
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<User> call = apiService.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    // Preenche os campos com os dados do usuário
                    name.setText(user.getNome());
                    emailAdress.setText(user.getEmail());
                    oldPassword.setText(""); // Deixe a senha em branco por segurança
                    oldPassword.setText("");
                    int userPoints = user.getPontuacao();
                } else {
                    Toast.makeText(activity_edit_sign.this, "Erro ao buscar dados do usuário.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity_edit_sign.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        // Obtém o ID do usuário salvo no SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);
        int userPoints = sharedPreferences.getInt("USER_POINTS", 0); // Busca a pontuação salva

        if (userId == -1) {
            Toast.makeText(this, "Erro: ID do usuário não encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Captura os campos
        String newName = name.getText().toString();
        String newEmail = emailAdress.getText().toString();
        String newPass = newPassword.getText().toString(); // Nova senha
        String oldPass = oldPassword.getText().toString(); // Senha antiga

        // Prepara o payload para a API
        HashMap<String, String> updatePayload = new HashMap<>();
        updatePayload.put("nome", newName);
        updatePayload.put("email", newEmail);
        updatePayload.put("oldPassword", oldPass); // Altere para "oldPassword"
        updatePayload.put("palavra_passe", newPass); // Mantém "palavra_passe"

        // Configurando o Retrofit
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<AuthResponse> call = apiService.updateUser(userId, updatePayload);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    Toast.makeText(activity_edit_sign.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (authResponse.isSuccess()) {
                        finish(); // Fecha a tela após a atualização bem-sucedida
                    }
                } else {
                    Toast.makeText(activity_edit_sign.this, "Erro ao atualizar os dados: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(activity_edit_sign.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validate(EditText name, EditText emailAdress, EditText password, EditText confPassword){
        String nameInput = name.getText().toString();
        String emailInput = emailAdress.getText().toString();
        String passwordInput = password.getText().toString();
        String confInput = confPassword.getText().toString();

        boolean validat = true;

        if(nameInput.isEmpty()){
            name.setError("Informe o nome");
            validat = false;
        }
        if(passwordInput.isEmpty()){
            password.setError("Defina a senha!");
            validat = false;
        }
        if(confInput.isEmpty()){
            confPassword.setError("Confirme a senha");
            validat = false;
        }
        if(emailInput.isEmpty()){
            emailAdress.setError("Informe um e-mail");
            validat = false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailAdress.setError("Informe um e-mail válido");
            validat = false;
        }

        return validat;
    }

    private boolean checkPass(EditText oldPassword, EditText newPassword){
        String oldPassInput = oldPassword.getText().toString();
        String newPassInput = newPassword.getText().toString();

        if (oldPassInput.isEmpty()) {
            oldPassword.setError("Informe a senha atual");
            return false;
        }

        if (newPassInput.isEmpty()) {
            newPassword.setError("Informe a nova senha");
            return false;
        }

        /* Opcional: Verifique se a nova senha atende aos critérios (ex: comprimento mínimo)
        if (newPassInput.length() < 6) {
            Password.setError("A nova senha deve ter pelo menos 6 caracteres");
            return false;
        }*/

        return true;
    }
}
