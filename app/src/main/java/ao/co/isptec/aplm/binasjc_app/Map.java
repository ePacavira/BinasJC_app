package ao.co.isptec.aplm.binasjc_app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.List;

import ao.co.isptec.aplm.binasjc_app.R;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private SearchView mapSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TAG", "Entrei em oncreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapSearchView = findViewById(R.id.mapSearch);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if(location != null && !location.isEmpty()){
                    Geocoder geocoder = new Geocoder(Map.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1); // 1 é o número máximo de resultados
                        if(addressList != null && !addressList.isEmpty()){
                            Address address = addressList.get(0);
                            // Obtenção da localização física
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            // Adiciona o marcador da pesquisa com a cor roxa
                            gMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(location)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10)); // Foco no marcador pesquisado
                        } else {
                            Toast.makeText(Map.this, "Endereço não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(Map.this);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = location;
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
            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Minha Localização"));
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
