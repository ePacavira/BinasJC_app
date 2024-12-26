package ao.co.isptec.aplm.binasjc_app;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;

import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private final static int REQUEST_CHECK_CODE = 1001;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SearchView mapSearchView;
    private HashMap<String,LatLng>predefinedLocations; //conjunto de Localizações que corresponderão as localizações das estações
    private Marker currentMarket;
    private ArrayList<Trajectory> allTrajectories = new ArrayList<>();
    private LocationCallback locationCallback;
    private User user = new User();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Trajectory currentTrajectory;
    private int totalPoints = 0;


    private LocationRequest locationRequest = new LocationRequest.Builder(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            30000
    )
            .setMinUpdateIntervalMillis(100)
            .build();
    private int pontuacao = 0;

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

        //PEGAR AS ESTAÇÕES
        predefinedLocations = new HashMap<>();

        apiService.getAllEstacoes().enqueue(new Callback<List<Estacao>>() {
            @Override
            public void onResponse(Call<List<Estacao>> call, Response<List<Estacao>> response) {
                if (response.isSuccessful()){
                    //obter a lista de estacoes
                    List<Estacao> estacoes = response.body();
                    for (Estacao estacao : estacoes) {
                        Log.d("Estacao", "ID: " + estacao.getIdEstacao() + ", Nome: " + estacao.getNome());
                        // Adiciona Estações predefinidas
                        predefinedLocations.put(estacao.getNome(), new LatLng((estacao.getLatitude()), estacao.getLongitude()));

                    }
                }else{
                    Log.e("Erro", "Falha ao obter estações: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Estacao>> call, Throwable t) {
                Log.e("Erro", "Erro na chamada à API: " + t.getMessage());
            }
        });

        // Inicializa FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity_home.this);
        getLastLocation();

        // Configurar o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        locationCallback = new LocationCallback() {
            @SuppressLint("NewApi")
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Inicia a trajetória, se necessário
                        if (currentTrajectory == null) {
                            currentTrajectory = new Trajectory("EstacaoInicial", "EstacaoFinal");
                            Log.d("Trajectory", "Trajetória iniciada em: " + currentLatLng);
                        }

                        // Adiciona o ponto atual à trajetória
                        Log.d("Trajectory", "Ponto adicionado: " + currentLatLng);

                        // Verifica se o usuário chegou a uma estação
                        for (String stationName : predefinedLocations.keySet()) {
                            LatLng stationLatLng = predefinedLocations.get(stationName);
                            float[] results = new float[1];
                            Location.distanceBetween(
                                    location.getLatitude(), location.getLongitude(),
                                    stationLatLng.latitude, stationLatLng.longitude,
                                    results
                            );

                            // Verifica se está dentro do raio da estação
                            if (results[0] <= 20) {
                                //verificar a logica de Levantamento e devolução da Bicicleta
                                /*Armazenamento  do ponto actual na estrutura*/
                                allTrajectories.add(currentTrajectory);

                                //Mandar ao servidor
                                //Enviar no Servidor simplemente quando a bicicleta for devolvida!
                                Log.d("Trajectory","Enviando trajectoria o Servidor");

                                RetrofitClient retrofitService = new RetrofitClient();
                                ApiService trajectoriApi = retrofitService.getRetrofit().create(ApiService.class);

                                trajectoriApi.save(currentTrajectory).enqueue(new Callback<Trajectory>() {
                                    @Override
                                    public void onResponse(Call<Trajectory> call, Response<Trajectory> response) {
                                        if (response.isSuccessful()) {
                                            Log.d("API", "Trajetória enviada com sucesso: " + response.body());
                                        } else {
                                            Log.d("API", "Código de resposta: " + response.code());
                                            Log.e("API", "Erro no servidor: " + response.code() + ", " + response.errorBody());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Trajectory> call, Throwable t) {
                                        Log.e("API", "Erro ao enviar a trajetória", t);
                                    }
                                });


                                /*reinicia o rastreamento*/
                                currentTrajectory = null;
                                Toast.makeText(activity_home.this, "Você chegou na estação: " + stationName, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                       /* List<LatLng> points = currentTrajectory.getIntermediatePoints();
                        for (int i = 1; i < points.size(); i++) {
                            float[] results = new float[1];
                            LatLng currentPoint = points.get(i);
                            LatLng previousPoint = points.get(i - 1);

                            Location.distanceBetween(
                                    previousPoint.latitude, previousPoint.longitude,
                                    currentPoint.latitude, currentPoint.longitude,
                                    results
                            );

                            if (results[0] >= 10) {
                                totalPoints++;
                                user.setPontuacao(totalPoints);
                                Log.d("MyPoint","Pontuação actual: " + totalPoints);
                            }
                        }*/
                        pontuacao += totalPoints;
                        Log.d("LocationUpdate", "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                    }
                }
                //showAllPoint();
            }


        };


        setupLocationUpdates();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    /*private void showAllPoint(){
        for(Trajectory trajectory : allTrajectories){
            if(trajectory != null){
                Log.e("TrajectoryPoints", trajectory.getEndLocation().toString());
            }else{
                Log.e("TrajectoryPoints", "Location is null");

            }
        }
    }*/

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
                                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                currentMarket = gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Actual"));
                                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
                            } else {
                                Toast.makeText(activity_home.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Actual"));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
            getLastLocation();
        } else {
            Toast.makeText(activity_home.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
        }

        gMap.setOnMapLoadedCallback(() -> {
            showAllStations();
            Log.e("Verificar estações proximas", "FUNÇÃO showAllStations CHAMADA!!!!!!!!");
        });

    }

    private void showAllStations() {
        if (gMap == null) {
            return;
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        boolean hasMarkers = false;
        float[] results = new float[1];

        // Certifique-se de que currentLocation não é nulo
        if (currentLocation != null) {
            for (String stationName : predefinedLocations.keySet()) {
                LatLng location = predefinedLocations.get(stationName);

                // Obter a distância entre a localização atual e as estações
                Location.distanceBetween(
                        currentLocation.getLatitude(), currentLocation.getLongitude(),
                        location.latitude, location.longitude, results
                );

                if (results[0] < 150) { // Comparando a distância real
                    gMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(stationName)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    builder.include(location);
                    hasMarkers = true;
                }
            }
        } else {
            Log.e("ShowStations", "Localização atual é nula, não é possível calcular distâncias.");
        }

        if (hasMarkers) {
            try {
                LatLngBounds bounds = builder.build();
                gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            } catch (IllegalStateException e) {
                Log.e("ShowStations", "Erro ao ajustar limites do mapa", e);
            }
        } else {
            Log.e("ShowStations", "Nenhum marcador adicionado para ajustar limites.");
        }

        // Adicionando o listener para clique nos marcadores
        gMap.setOnMarkerClickListener(marker -> {
            if(!currentMarket.getTitle().equals(marker.getTitle())){
                String stationName = marker.getTitle();
                Intent intent = new Intent(activity_home.this, activity_bike_list.class);
                intent.putExtra("STATION_NAME", stationName);
                startActivity(intent);
            }
            return false;
        });
    }


    private void handleSearchQuery(String query) {
        if (gMap == null || currentLocation == null) {
            Log.e("HandleSearchQuery", "Mapa ou localização atual não disponível");
            return;
        }

        if (predefinedLocations.containsKey(query)) {
            LatLng destination = predefinedLocations.get(query);

            // Criar uma Polyline entre a localização atual e o destino
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            gMap.addPolyline(new PolylineOptions()
                    .add(currentLatLng, destination)
                    .width(10)
                    .color(Color.BLUE)
                    .geodesic(true));

            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            boundsBuilder.include(currentLatLng);
            boundsBuilder.include(destination);

            gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100));
        } else {
            Log.e("HandleSearchQuery", "Nenhuma estação encontrada para a consulta: " + query);
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

    private void setupLocationUpdates() {
        // Configuração da Localização
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build();

        // Verificar as configurações de Localização
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                        if (task.isSuccessful()) {
                            // As configurações de Localização estão habilitadas
                            if (ActivityCompat.checkSelfPermission(
                                    activity_home.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(
                                            activity_home.this,
                                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // Solicitar permissões se não foram concedidas
                                ActivityCompat.requestPermissions(
                                        activity_home.this,
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                        },
                                        REQUEST_CHECK_CODE
                                );
                                return;
                            }

                            // Iniciar as atualizações de localização
                            fusedLocationProviderClient.requestLocationUpdates(
                                    locationRequest, // Configuração da solicitação de localização
                                    locationCallback, // Callback para processar os resultados
                                    Looper.getMainLooper() // Thread principal
                            );

                        } else {
                            // Lidar com configurações de localização desativadas
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) task.getException();
                                resolvableApiException.startResolutionForResult(
                                        activity_home.this,
                                        REQUEST_CHECK_CODE
                                );
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}