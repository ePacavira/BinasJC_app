package ao.co.isptec.aplm.binasjc_app;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_home extends AppCompatActivity implements OnMapReadyCallback {

    Dialog dialog;
    Button btnDialogLogout, btnDialogCancel;
    NumberPicker numberPicker;
    TextView txtView;
    private GoogleMap gMap;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private SearchView mapSearchView;
    private HashMap<String,LatLng>predefinedLocations; //conjunto de Localizações que corresponderão as localizações das estações
    private  Marker lastMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("USER_ID", -1);
        int userPoints = sharedPreferences.getInt("USER_POINTS", 0); // Valor padrão: 0

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
                    TextView textUserName = findViewById(R.id.textUserName);
                    textUserName.setText("Olá, " + user.getNome());
                    TextView textUserPoints = findViewById(R.id.textUserPoints);
                    textUserPoints.setText(userPoints + " Pts");

                } else {
                    Toast.makeText(activity_home.this, "Erro ao carregar dados do usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity_home.this, "Erro na comunicação com o servidor", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

        mapSearchView = findViewById(R.id.mapSearch);
        int searchTextId = mapSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchText = mapSearchView.findViewById(searchTextId);
        searchText.setHintTextColor(getResources().getColor(R.color.white)); // Define a cor do hint como branco

        int searchIconId = mapSearchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView searchIcon = mapSearchView.findViewById(searchIconId);
        searchIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN); // Define a cor do ícone

        predefinedLocations = new HashMap<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Adiciona Estações predefinidas
        predefinedLocations.put("EstacaoSoleno", new LatLng(-8.839, 13.234)); // Exemplo de uma estação fíticia
        predefinedLocations.put("EstacaoEunice", new LatLng(-8.828, 13.230));
        predefinedLocations.put("EstacaoNumbi", new LatLng(-8.825, 13.245));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);

        // Verificar permissões de localização
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        } else {
            getLastLocation();
        }

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(lastMarker != null){
                    lastMarker.remove();
                    lastMarker = null;
                }

                // Verifica se a pesquisa corresponde a um ponto predefinido
                if (predefinedLocations.containsKey(query)) {
                    LatLng location = predefinedLocations.get(query);

                    // Adiciona um marcador no ponto correspondente
                    lastMarker = gMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(query)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                    Toast.makeText(activity_home.this, "Estação encontrada: " + query, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity_home.this, "Estação não encontrada", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dialog = new Dialog(activity_home.this);
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
                Intent intent = new Intent(activity_home.this, activity_login.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_home.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
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

                Intent intent = new Intent(activity_home.this, activity_station.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_home.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        ConstraintLayout btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_home.this, activity_profile.class);
                startActivity(intent);
                finish();
                Toast.makeText(activity_home.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        CardView btnCardChat = findViewById(R.id.cardChat);
        btnCardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_list_chat.class);
                startActivity(intent);

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(activity_home.this, "Exectudado com Sucesso!!", Toast.LENGTH_SHORT).show();

            }
        });



    }
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
                                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Actual"));
                                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
                                onMapReady(gMap);  // Atualiza o mapa com a localização do usuário
                            }
                        }
                    });
        } else {
            // Solicita permissão se ela ainda não foi concedida
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            // Adiciona o marcador da localização atual
            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Actual"));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
        } else {
            Toast.makeText(this, "Localização não disponível", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            } else {
                Toast.makeText(this,"Permissão de Localização Negada, por favor aceite as permissões",Toast.LENGTH_SHORT).show();
            }
        }
    }
}