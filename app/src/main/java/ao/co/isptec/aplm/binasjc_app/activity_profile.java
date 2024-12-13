package ao.co.isptec.aplm.binasjc_app;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);

        // Obter instância de Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();

        // Criar a interface do serviço da API
        ApiService apiService = retrofit.create(ApiService.class);

        // Fazer a chamada à API
        Call<User> call = apiService.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    TextView textUserName = findViewById(R.id.txtUserName);
                    textUserName.setText(user.getNome());
                    TextView textUserEmail = findViewById(R.id.txtUserEmail);
                    textUserEmail.setText(user.getEmail());

                } else {
                    Toast.makeText(activity_profile.this, "Erro ao carregar dados do usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity_profile.this, "Erro na comunicação com o servidor", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
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

        userName = findViewById(R.id.txtUserName);
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

                Intent intent = new Intent(activity_profile.this, activity_home.class);
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