package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_station extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_station);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConstraintLayout btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_main.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation01 = findViewById(R.id.btnStation01);
        btnStation01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation02 = findViewById(R.id.btnStation02);
        btnStation02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation03 = findViewById(R.id.btnStation03);
        btnStation03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation04 = findViewById(R.id.btnStation04);
        btnStation04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation05 = findViewById(R.id.btnStation05);
        btnStation05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation06 = findViewById(R.id.btnStation06);
        btnStation06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

        ConstraintLayout btnStation07 = findViewById(R.id.btnStation07);
        btnStation07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_station.this, activity_bike_list.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_station.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}