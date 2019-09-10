package com.example.viniciuscanastacio.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocation;
    private static final int CODIGO_GPS = 1000;
    private TextView textView;
    private Location minhaLoc = new Location("Minha Localização");
    private Location catedralLoc = new Location("Localização da catedral");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewDistancia);
    }

    public void mostrarDistancia(View v){
        //Verifica se a permissão não foi concedida.
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Solicita o uso do GPS pata o usuário
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, CODIGO_GPS);

        }

        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        fusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                minhaLoc.setLatitude(location.getLatitude());
                minhaLoc.setLongitude(location.getLongitude());
                calculaDistancia();
            }
        });
    }

    public void calculaDistancia() {
        catedralLoc.setLatitude(-22.018229);
        catedralLoc.setLongitude(-47.8934337);

        Float distancia = minhaLoc.distanceTo(catedralLoc)/1000;
        textView.setText(String.format("%.2f", distancia) + " Km");
    }
}
