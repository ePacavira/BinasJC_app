package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class activity_bike_list extends AppCompatActivity {

    ListView lstvwBike07;
    int[] arrNo = new int[]{1,2,3,4,5};
    ArrayList<String> arrNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bike_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lstvwBike07 = findViewById(R.id.lstvwBike07);

        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");
        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");
        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");
        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");
        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");
        arrNames.add("Bike Orlando");
        arrNames.add("Bike 11 de novembro");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, arrNames);
        lstvwBike07.setAdapter(adapter);

        lstvwBike07.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    Toast.makeText(activity_bike_list.this, "First Bike Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ConstraintLayout btnBack = findViewById(R.id.btnBackBikeList);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_bike_list.this, activity_station.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_bike_list.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}