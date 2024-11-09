package ao.co.isptec.aplm.binasjc_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_main extends AppCompatActivity {

    Dialog dialog;
    Button btnDialogLogout, btnDialogCancel;
    NumberPicker numberPicker;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dialog = new Dialog(activity_main.this);
        dialog.setContentView(R.layout.activity_dialog_share);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.activity_dialog_share_bg));
        dialog.setCancelable(false);

        txtView = dialog.findViewById(R.id.txtSend);
        numberPicker = dialog.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(10);
        numberPicker.setWrapSelectorWheel(true);

        numberPicker.setFormatter(value -> String.format("%03d", value));

        /*numberPicker.setTextColor(getColor(R.color.black));
        numberPicker.setTextSize(64.0f);*/

        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Checa se o novo valor é igual ao limite inferior ou superior e impede o crash
            if (newVal < numberPicker.getMinValue()) {
                numberPicker.setValue(numberPicker.getMinValue());
            } else if (newVal > numberPicker.getMaxValue()) {
                numberPicker.setValue(numberPicker.getMaxValue());
            } else {
                txtView.setText("Você vai enviar: " + String.format("%03d", newVal));
            }
        });

        btnDialogLogout = dialog.findViewById(R.id.btnDialogEntregar);
        btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);

        btnDialogLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_main.this, activity_login.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_main.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        CardView cardEnviarPontos = findViewById(R.id.cardEnviarPontos);
        cardEnviarPontos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });

        CardView cardReservar = findViewById(R.id.cardReservar);
        cardReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_main.this, activity_station.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_main.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        ConstraintLayout btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_main.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_main.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

    }
}