package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


public class activity_dialog_reservation_bike extends AppCompatActivity {
    private EditText bike_name;
    private EditText station_name;
    private EditText dateRestore;
    private Button restore;
    private Button cancelar;


    private String name;
    private String station;
    private String dateInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dialog_reservation_bike);


        bike_name = findViewById(R.id.bikeName);
        station_name = findViewById(R.id.stationName);
        restore = findViewById(R.id.btnDialogReservar);
        cancelar = findViewById(R.id.btnDialogCancel);

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = bike_name.getText().toString();
                 station = station_name.getText().toString();
                 dateInput = dateRestore.getText().toString();
                 validate(name,station,dateInput);
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_dialog_reservation_bike.this, activity_station.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate(String name, String station, String dateInput){
        boolean valid = true;
        if(TextUtils.isEmpty(name)){
            bike_name.setError("Por favor informe o codigo da bicicleta");
            valid = false;
        }
        if(TextUtils.isEmpty(station)){
            station_name.setError("Por favor informe a estação");
            valid = false;
        }
        if (TextUtils.isEmpty(dateInput)){
            dateRestore.setError("Por Favor, informe a data para a sua reserva");
        }

        //validar o formato da data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(dateInput);
        } catch (ParseException e) {
            dateRestore.setError("Formato de data inválido. Use: yyyy-MM-dd.");
            valid =  false;
        }

        return valid;
    }
}