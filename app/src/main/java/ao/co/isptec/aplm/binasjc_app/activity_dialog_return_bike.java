package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_dialog_return_bike extends AppCompatActivity {
    private EditText bike_id;
    private EditText station_name;
    private Button entregar;
    private Button cancel;

    private String idString;
    private int id;
    private String station;

    //
    private boolean vedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog_return_bike);
        bike_id= findViewById(R.id.id_Bike);
        station_name = findViewById(R.id.stationName);
        entregar = findViewById(R.id.btnDialogEntregar);
        cancel = findViewById(R.id.btnDialogCancel2);

        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_dialog_return_bike.this,activity_profile.class);
                startActivity(intent);
            }
        });*/
        entregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Botao clicado",Toast.LENGTH_SHORT).show();
                idString = bike_id.getText().toString();
                station = station_name.getText().toString();
                validate(idString,station);
                vedt = validate(idString,station);

            }
        });

    }

    private boolean validate(String idString, String station){
        boolean isValid = true;
        if (TextUtils.isEmpty(idString)) {
            bike_id.setError("Por favor informe o Id da Bicicleta");
            isValid = false;
        } else {
            try {
                id = Integer.parseInt(idString);
                if (id <= 0) {
                    bike_id.setError("O Id deve ser um número positivo");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                bike_id.setError("Por favor insira um valor válido para o Id");
                isValid = false;
            }
        }


        // Validação do campo station_name
        if (TextUtils.isEmpty(station)) {
            station_name.setError("Por favor informe o nome da estação");
            isValid = false;
        }

        return isValid;
    }

    public EditText getStation_name() {
        return station_name;
    }

    public void setStation_name(EditText station_name) {
        this.station_name = station_name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public boolean isVedt() {
        return vedt;
    }

    public void setVedt(boolean vedt) {
        this.vedt = vedt;
    }
}