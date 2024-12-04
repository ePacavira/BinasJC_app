package ao.co.isptec.aplm.binasjc_app;

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

public class activity_edit_sign extends AppCompatActivity {
    private EditText name;
    private EditText emailAdress;
    private EditText password;
    private EditText confPassword;
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
        password = findViewById(R.id.txtPassEdit);
        confPassword = findViewById(R.id.txtConfPassEdit);
        recreate = findViewById(R.id.button);

        recreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validate(name,emailAdress,password,confPassword);
               checkPass(password,confPassword);

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
            password.setError("Defina a Passwor!");
            validat = false;
        }
        if(confInput.isEmpty()){
            confPassword.setError("Confirme a Palavra passe");
            validat = false;
        }
        if(emailInput.isEmpty()){
            emailAdress.setError("Informe um email");
            validat = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            emailAdress.setError("Informe um email valido");
            validat = false;
        }

        return validat;
    }

    private boolean checkPass(EditText password, EditText confPassword){
        String passwordInput = password.getText().toString();
        String confInput = confPassword.getText().toString();
        if(passwordInput.equals(confInput)){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"A palavra passe nao concide",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}