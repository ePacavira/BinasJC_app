package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_sign extends AppCompatActivity {

    EditText txtnamesign, txtemailsign, txtpasssign, txtconfpasssign;
    Button btnsign1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtnamesign = findViewById(R.id.txtNameSign);
        txtemailsign = findViewById(R.id.txtEmailSign);
        txtpasssign = findViewById(R.id.txtPassSign);
        txtconfpasssign = findViewById(R.id.txtConfPassSign);

        btnsign1 = findViewById(R.id.btn_Sign1);

        btnsign1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                processFormFields();
            }
        });

        // Botão de navegação para a nova Activity
       /*Button btnSig = findViewById(R.id.btn_Sign1);
        btnSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_sign.this, activity_login.class); // Nome correto da Activity de destino
                startActivity(intent);
            }
        });*/

        // Botão de navegação para a nova Activity
        TextView txtLog = findViewById(R.id.txtLogin);
        txtLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_sign.this, activity_login.class); // Nome correto da Activity de destino
                startActivity(intent);
            }
        });
    }

    public void processFormFields(){

        if(!validateName() || !validateemail() || !validatepass()){
            return;
        }

        Toast.makeText(activity_sign.this,"Registro Feito com Sucesso!!", Toast.LENGTH_SHORT).show();

    }

    public boolean validateName(){

        String txtnameSign = txtnamesign.getText().toString();

        if(txtnameSign.isEmpty()){
            txtnamesign.setError("O campo Nome Completo está vazio!!");
            return false;
        }else{
            txtnamesign.setError(null);
            return true;
        }

    }

    public boolean validateemail(){

        String txtemailSign = txtemailsign.getText().toString();

        if(txtemailSign.isEmpty()){
            txtemailsign.setError("O campo Email está vazio!!");
            return false;
        }else if(!support_string_helper.regexEmailValidationPattern(txtemailSign)){

            txtemailsign.setError("Este email é invalido, introduza um email Valido!!");
            return false;

        }else{
            txtemailsign.setError(null);
            return true;
        }

    }

    public boolean validatepass(){

        String txtpassSign = txtpasssign.getText().toString();
        String txtconfpassSign = txtconfpasssign.getText().toString();

        if(txtpassSign.isEmpty() || txtconfpassSign.isEmpty()){
            txtpasssign.setError("O campo Palavra-Passe está vazio!!");
            txtconfpasssign.setError("O campo Confirmar Palavra-Passe está vazio!!");
            return false;
        }else if(!txtpassSign.toString().equals(txtconfpassSign.toString())){
            txtconfpasssign.setError("As palavra-passe diferente, tente novamente!!");
            return false;
        }
        else{
            txtpasssign.setError(null);
            txtconfpasssign.setError(null);
            return true;
        }
    }

    private void register() {
        String email = txtemailsign.getText().toString().trim();
        String username = txtnamesign.getText().toString().trim();
        String password = txtpasssign.getText().toString().trim();
        String confirmPassword = txtconfpasssign.getText().toString().trim();

        // Desabilitar o botão enquanto processa
        btnsign1.setEnabled(false);

        request_register registerRequest = new request_register(username, password, email, confirmPassword);

        retrofit_client.getApiService().register(registerRequest).enqueue(new Callback<model_users>() {
            @Override
            public void onResponse(Call<model_users> call, Response<model_users> response) {
                btnsign1.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(activity_sign.this,
                            "Registro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_sign.this, activity_login.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ?
                                response.errorBody().string() : "Erro desconhecido";

                        Log.e("activity_sign", "Erro no registro: " +
                                "\nCódigo: " + response.code() +
                                "\nMensagem: " + errorBody);

                        Toast.makeText(activity_sign.this,
                                "Erro no registro: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("activity_sign", "Erro ao ler resposta: " + e.getMessage());
                        Toast.makeText(activity_sign.this,
                                "Erro no registro. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<model_users> call, Throwable t) {
                btnsign1.setEnabled(true);
                Log.e("activity_sign", "Falha na requisição: " + t.getMessage(), t);

                String errorMessage;
                if (t instanceof java.net.ConnectException) {
                    errorMessage = "Não foi possível conectar ao servidor. Verifique sua conexão.";
                } else if (t instanceof java.net.SocketTimeoutException) {
                    errorMessage = "Tempo de conexão esgotado. Tente novamente.";
                } else {
                    errorMessage = "Erro: " + t.getMessage();
                }

                Toast.makeText(activity_sign.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}