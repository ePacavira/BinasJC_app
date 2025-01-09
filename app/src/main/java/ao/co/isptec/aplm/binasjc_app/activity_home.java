package ao.co.isptec.aplm.binasjc_app;

import static android.content.ContentValues.TAG;
import static android.location.Location.distanceBetween;

import android.Manifest;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    private ArrayList<Trajectoria> allTrajectories = new ArrayList<>();
    private LocationCallback locationCallback;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private PontoIntermediario ponto ;
    List<Estacao> estacoes = new ArrayList<>();
    Integer idEstacao = 0;
    private Trajectoria currentTrajectory = new Trajectoria();
    private boolean isTrackingStarted = false;

    List<PontoIntermediario> pontosBuffer = new ArrayList<>() ;
    private LatLng lastRecordedPosition = null;
    private static final float MIN_DISTANCE_BETWEEN_POINTS = 150;


    // Obter instância de Retrofit
    private Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    // Criar a interface do serviço da API
    private ApiService apiService = retrofit.create(ApiService.class);


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
        // Inicializa FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity_home.this);
        getLastLocation();
        predefinedLocations = new HashMap<>();
        apiService.getAllEstacoes().enqueue(new Callback<List<Estacao>>() {
            @Override
            public void onResponse(Call<List<Estacao>> call, Response<List<Estacao>> response) {
                if (response.isSuccessful()){
                    //obter a lista de estacoes
                    estacoes = response.body();
                    Log.d("EstacaoDebug", "Número de estações recebidas: " + estacoes.size());
                    for (Estacao estacao : estacoes) {
                        // Adiciona Estações predefinidas
                        predefinedLocations.put(estacao.getNome(), new LatLng((estacao.getLatitude()), estacao.getLongitude()));
                        Log.d("EstacaoDebug", "ID: " + estacao.getIdEstacao() + ", Nome: " + estacao.getNome() + " Latitude: " + estacao.getLatitude() + " Longitude: " + estacao.getLongitude());
                    }
                    Log.d("EstacaoDebug", "Tamanho final do predefinedLocations: " + predefinedLocations.size());
                }else{
                    Log.e("Erro", "Falha ao obter estações: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Estacao>> call, Throwable t) {
                Log.e("Erro", "Erro na chamada à API: " + t.getMessage());
            }
        });

        // Configurar o mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


      locationCallback = new LocationCallback() {
          private User currentUser = null;
          @Override
          public void onLocationResult(@NonNull LocationResult locationResult) {
              if(locationResult != null){
                  Location location = locationResult.getLastLocation();
                  if(location != null){
                      LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                      //obter o usuario
                      if(currentUser == null){
                          ApiService apiService = retrofit.create(ApiService.class);
                          Call<User> call = apiService.getUserById(userId);
                          call.enqueue(new Callback<User>() {
                              @Override
                              public void onResponse(Call<User> call, Response<User> response) {
                                  if (response.isSuccessful() && response.body() != null) {
                                      currentUser = response.body();;
                                      processarLocalizacao(location, currentLatLng);
                                  }
                              }
                              @Override
                              public void onFailure(Call<User> call, Throwable t) {
                                  Log.e("API", "Erro ao obter usuário", t);
                              }
                          });
                      } else if(!isTrackingStarted) {
                          processarLocalizacao(location, currentLatLng);
                      } else {
                          processarLocalizacao(location, currentLatLng);
                      }
                  }

              }
          }

          private void processarLocalizacao(Location location, LatLng currentLatLng) {

              //inicio da analise de pontos
                if(isTrackingStarted){
                    Log.d("PontoIntermediario","Iniciando a analise dos pontos intermediarios");
                    recordPointIfNeeded(currentLatLng);
                }

              // Verificar se o usuário está perto de uma estação
              for (String stationName : predefinedLocations.keySet()) {
                  LatLng stationLatLng = predefinedLocations.get(stationName);
                  float[] results = new float[1];
                  distanceBetween(
                          location.getLatitude(), location.getLongitude(),
                          stationLatLng.latitude, stationLatLng.longitude,
                          results
                  );
                  if (results[0] <= 20 && currentUser != null) {
                      Log.d("API", "Utilizador encontrado: " + currentUser.toString());
                      Log.d("API", "Verificando o conjunto de reservas para " + currentUser.getId());

                      // Obter o ID da estação onde o usuário se encontra
                      for (Estacao estacao : estacoes) {
                          float[] result = new float[1];
                          Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                                  estacao.getLatitude(), estacao.getLongitude(), result);
                          if (result[0] <= 20) {
                              idEstacao = estacao.getIdEstacao();
                          }
                      }
                      Log.d("API", "1) IdEstacao: " + idEstacao);
                      verificarReservas(currentUser, location);
                      break;
                  }
              }
          }

          private void recordPointIfNeeded(LatLng currentLatLng) {
              if(lastRecordedPosition == null){
                  // primeiro Ponto da trajectoria
                  lastRecordedPosition = currentLatLng;
                  addPointToBuffer(currentLatLng);
                  Log.d("PontoIntermediario","Primeiro ponto rastreado");
                  return;
              }
              float[] results = new float[1];
              Location.distanceBetween(
                      lastRecordedPosition.latitude,
                      lastRecordedPosition.longitude,
                      currentLatLng.latitude,
                      currentLatLng.longitude,
                      results
              );
              Log.d("PontoIntermediario","Distancia Calculada: " + results[0]);

              if (results[0] >= MIN_DISTANCE_BETWEEN_POINTS) {
                  //Incrementar o ponto do utilizador;
                  int pontuacao = this.currentUser.getPontuacao() + 1;
                  this.currentUser.setPontuacao(pontuacao);
                  //envio dos pontos
                  lastRecordedPosition = currentLatLng;
                  addPointToBuffer(currentLatLng);
                  Log.d("PontoIntermediario", "Novo ponto adicionado ao buffer Total points: " + pontosBuffer.size());
              }
          }

          private void addPointToBuffer(LatLng position) {
              PontoIntermediario ponto = new PontoIntermediario(
                      position.latitude,
                      position.longitude
              );
              pontosBuffer.add(ponto);
              Log.d("PontoIntermediario", "Ponto adicionado ao Buffer");
          }

          private void verificarReservas(User user, Location location) {
              Log.d("API"," 2) IdEstacao: " + idEstacao);
              apiService.getAllReservas().enqueue(new Callback<List<Reserva>>() {
                  @Override
                  public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                      if (response.isSuccessful() && response.body() != null) {
                          Log.d("API", "Status HTTP: " + response.code());
                          List<Reserva> reservas = response.body();
                          Log.d("API", "Conjunto de Reservas Encontradas: " + reservas.size());
                          for (Reserva reserva : reservas) {
                              if (user.getId().equals(reserva.getUsuario().getId())) {
                                  Log.d("API", " Id usuario: " + user.getId() + " Id Reserva: " + reserva.getIdReserva());
                                  processarReserva(reserva, location, user);
                              }
                          }
                      }
                  }

                  @Override
                  public void onFailure(Call<List<Reserva>> call, Throwable t) {
                      Log.e("API", "Erro na chamada: " + t.getMessage());
                  }
              });
          }

          private void processarReserva(Reserva reserva, Location location, User user) {
              Bicicleta bicicleta = reserva.getBicicleta();
              StatusBicicleta statusBackend = StatusBicicleta.fromString(bicicleta.getStatus().toString());

              Log.d("API", "Status da bicicleta: " + statusBackend);
              Log.d("API", "3) IdEstacao: " + idEstacao);

              if (statusBackend == StatusBicicleta.RESERVADA) {
                  Log.d("API", "Bicicleta reservada encontrada: " + bicicleta.getIdBicicleta());
                  inicializarTrajectoria(user, reserva, location);
                  levantarBicicleta(reserva.getIdReserva(), user.getId());
              } else if (statusBackend == StatusBicicleta.EM_USO) {
                  Log.d("API", "Bicicleta em uso encontrada: " + bicicleta.getIdBicicleta());
                  devolverBicicleta(reserva.getIdReserva(), user.getId(), idEstacao);
                  finalizarTrajectoria(location);

              }
          }


          private void inicializarTrajectoria(User user, Reserva reserva, Location location) {
              currentTrajectory = new Trajectoria();

              currentTrajectory.setUser(new Trajectoria.UserId(user.getId()));
              currentTrajectory.setReserva(new Trajectoria.ReservaId(reserva.getIdReserva()));
              // Define as coordenadas iniciais
              currentTrajectory.setLatitudeInicio(location.getLatitude());
              currentTrajectory.setLongitudeInicio(location.getLongitude());
              // inicializar o pontosBuffer
              pontosBuffer.clear();
              lastRecordedPosition = null;
              isTrackingStarted = true;


              Log.d("Trajectory", "Trajetória inicializada: " + new Gson().toJson(currentTrajectory));
          }

          private void finalizarTrajectoria(Location location) {
              try {
                  if (currentTrajectory != null && isTrackingStarted) {
                      currentTrajectory.setLatitudeFim(location.getLatitude());
                      currentTrajectory.setLongitudeFim(location.getLongitude());

                      enviarTrajectoria(currentTrajectory, new TrajectoryCallback() {
                          @Override
                          public void onTrajectoryCreated(Integer trajectoryId) {
                              // Now send all accumulated points
                              enviarPontosIntermediarios(trajectoryId, pontosBuffer);
                          }

                          @Override
                          public void onError(String error) {
                              Log.e("API", "Error ao enviar a trajectoria: " + error);
                          }
                      });
                      // reiniciar o percurso
                      currentTrajectory = null;
                      isTrackingStarted = false;
                      pontosBuffer.clear();
                      lastRecordedPosition = null;

                  }

              } catch (Exception e) {
                  Log.e("Trajectory", "Erro ao finalizar trajetória", e);
              }
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
    @Override
    protected void onResume() {
        super.onResume();
        getLastLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        if (currentLocation != null) {
            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Actual"));
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
            getLastLocation();
            initializeMapWithStations();
        } else {
            Toast.makeText(activity_home.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
        }
    }
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            Log.d("LocationDebug", "Localização obtida com sucesso: " +
                                    "\nLatitude: " + location.getLatitude() +
                                    "\nLongitude: " + location.getLongitude() +
                                    "\nPrecisão: " + location.getAccuracy() +
                                    "\nTempo: " + new Date(location.getTime()).toString());
                            currentLocation = location;
                            LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            currentMarket = gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Localização Atual"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
                            initializeMapWithStations();
                        } else {
                            Log.e("LocationDebug", "Localização obtida é nula.");
                            Toast.makeText(activity_home.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Log.e("LocationDebug", "Erro ao obter localização: " + e.getMessage()));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    private void initializeMapWithStations() {
        Log.d("MapDebug", "initializeMapWithStations - Map: " + (gMap != null) +
                ", Location: " + (currentLocation != null) +
                ", Stations: " + (!predefinedLocations.isEmpty()));

        if (gMap != null && currentLocation != null && !predefinedLocations.isEmpty()) {
            showAllStations();
        }
    }

    private void showAllStations() {
        if (gMap == null) {
            Log.d("ShowStations", "Mapa nulo");
            return;
        }

        gMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        boolean hasMarkers = false;
        float[] results = new float[1];

        // Adiciona o marcador da localização atual
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        currentMarket = gMap.addMarker(new MarkerOptions()
                .position(currentLatLng)
                .title("Localização Atual"));
        builder.include(currentLatLng);

        if (!predefinedLocations.isEmpty()) {
            for (String stationName : predefinedLocations.keySet()) {
                LatLng location = predefinedLocations.get(stationName);

                distanceBetween(
                        currentLocation.getLatitude(), currentLocation.getLongitude(),
                        location.latitude, location.longitude, results
                );

                if (results[0] < 2000) {
                    gMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(stationName)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    builder.include(location);
                    hasMarkers = true;
                }
            }
        }

        if (hasMarkers) {
            try {
                LatLngBounds bounds = builder.build();
                gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            } catch (IllegalStateException e) {
                Log.e("ShowStations", "Erro ao ajustar limites do mapa", e);
            }
        }

        gMap.setOnMarkerClickListener(marker -> {
            if (!marker.getTitle().equals("Localização Atual")) {
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


    interface TrajectoryCallback {
        void onTrajectoryCreated(Integer trajectoryId);
        void onError(String error);
    }

    private void enviarTrajectoria(Trajectoria trajectory, TrajectoryCallback callback) {
        apiService.saveTrajectory(trajectory)
                .enqueue(new Callback<Trajectoria>() {
                    @Override
                    public void onResponse(Call<Trajectoria> call, Response<Trajectoria> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Trajectoria savedTrajectory = response.body();
                            callback.onTrajectoryCreated(savedTrajectory.getIdTrajetoria());
                            Log.d("API", "##Trajectoria salva com sucesso com ID: " + savedTrajectory.getIdTrajetoria());
                        } else {
                            callback.onError("Server error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Trajectoria> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    private  void levantarBicicleta(Integer idReserva, Integer idUsuario) {
        /*final boolean[] retorno = {false};*/
        apiService.levantarBicicleta(idReserva, idUsuario).enqueue(new Callback<Bicicleta>() {
            @Override
            public void onResponse(Call<Bicicleta> call, Response<Bicicleta> response) {
                if (response.isSuccessful()) {
                    Log.d("API","Bicicleta Levantada com Sucesso");
                    Toast.makeText(activity_home.this,"Bicicleta Levantada",Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("API","Erro ao levantar bicicleta: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<Bicicleta> call, Throwable t) {
                Log.e("API_Levantas","Falha na chamada da API: " + t.getMessage());
            }
        });
    }

    private  void devolverBicicleta (Integer idReserva, Integer idUsuario, Integer idEstacaoDevolucao){
        apiService.devolverBicicleta(idReserva,idUsuario,idEstacaoDevolucao).enqueue(new Callback<Map<String, Object>>() {

            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d("API","Indo Devolver a Bicicleta!!!");
                if(response.isSuccessful() && response.body() != null){
                    Map<String,Object> responseBody = response.body();
                    if((Boolean) responseBody.get("success")){
                        Log.d("API","Bicicleta Devolvida com Sucesso");
                        Toast.makeText(activity_home.this,"Bicicleta Devolvida",Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("API", "Erro: " + responseBody.get("message"));
                    }
                    }else{
                    Log.d("API","Erro ao devolver a bicicleta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("API_Devolver", "ERRO EM SE COMUNICAR COM A API : "+ t.getMessage());
            }
        });

    }

    private void enviarPontosIntermediarios(Integer trajectoryId, List<PontoIntermediario> pontos) {

        // Configurar o Gson para formatar corretamente
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Preparar os pontos
        List<PontoIntermediario> pontosParaEnviar = new ArrayList<>();

        for (PontoIntermediario ponto : pontos) {
            ponto.setTrajetoria(new PontoIntermediario.Trajectoria(trajectoryId));
            pontosParaEnviar.add(ponto);

            // Log para debug
            String jsonPonto = gson.toJson(ponto);
            Log.d("API", "Ponto preparado: " + jsonPonto);
        }

        // Log da lista completa
        String jsonCompleto = gson.toJson(pontosParaEnviar);
        Log.d("API", "JSON completo a ser enviado: " + jsonCompleto);

        apiService.createPontoIntermediario(pontosParaEnviar).enqueue(
                new Callback<List<PontoIntermediario>>() {
                    @Override
                    public void onResponse(Call<List<PontoIntermediario>> call, Response<List<PontoIntermediario>> response) {
                        if (response.isSuccessful()) {
                            Log.d("API", "Pontos enviados com sucesso: " + pontosParaEnviar.size());
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("API", "Erro ao enviar pontos. Código: " + response.code() +
                                        " Corpo do erro: " + errorBody);
                            } catch (IOException e) {
                                Log.e("API", "Erro ao ler resposta", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PontoIntermediario>> call, Throwable t) {
                        Log.e("API", "Falha ao enviar pontos: " + t.getMessage());
                        t.printStackTrace();
                    }
                }
        );

    }



    private void actualizarPontucao(User user){
        //apiService.updateUser(user)
    }
}