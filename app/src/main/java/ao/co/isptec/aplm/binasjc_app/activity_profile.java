package ao.co.isptec.aplm.binasjc_app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_profile extends AppCompatActivity {

    Dialog dialog, dialog_1;
    Button btnDialogLogout, btnDialogCancel, btnDialogCancel1;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dialog = new Dialog(activity_profile.this);
        dialog.setContentView(R.layout.activity_dialog_logout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.activity_dialog_share_bg));
        dialog.setCancelable(false);

        dialog_1 = new Dialog(activity_profile.this);
        dialog_1.setContentView(R.layout.activity_dialog_return_bike);
        dialog_1.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.activity_dialog_share_bg));
        dialog_1.setCancelable(false);

        btnDialogLogout = dialog.findViewById(R.id.btnDialogEntregar);
        btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);

        userName = findViewById(R.id.txtName);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_profile.this,activity_edit_sign.class);
                startActivity(intent);
            }
        });


        btnDialogCancel1 = dialog_1.findViewById(R.id.btnDialogCancelBike);

        btnDialogLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_profile.this, activity_login.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_profile.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_1.dismiss();
            }
        });

        ConstraintLayout btnHistoryReserv = findViewById(R.id.btnHistoryReserv);
        btnHistoryReserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_profile.this, activity_reservation_history.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_profile.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnBackProfile = findViewById(R.id.btnBackProfile);
        btnBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_profile.this, activity_main.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_profile.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnEntregarBike = findViewById(R.id.btnEntregarBike);
        btnEntregarBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_1.show();

            }
        });

        ConstraintLayout btnHistoryPoint = findViewById(R.id.btnHistoryPoint);
        btnHistoryPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_profile.this, activity_point_history.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_profile.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnHistoryTraj = findViewById(R.id.btnHistoryTraj);
        btnHistoryTraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_profile.this, activity_traject_history.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_profile.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });
    }
}