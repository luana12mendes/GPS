package com.example.luana.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        private TextView  txtResult;
        private Button btnOk;
        private EditText etxtLat, etxtLong;
        private GoogleApiClient mGoogleApiClient;
        private Address endereco;
        private double latitude = 0.0;
        private double longitude = 0.0;


        String[] permissoes = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtResult = (TextView) findViewById(R.id.txtResult);

        etxtLat = (EditText) findViewById(R.id.etxtLat);
        etxtLong = (EditText) findViewById(R.id.etxtLong);


        PermissionUtils.validate(this, 0, permissoes);

        callConnection();

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etxtLat.getText().toString().length() != 0){
                    latitude = Double.parseDouble(etxtLat.getText().toString());
                }

                if (etxtLong.getText().toString().length() != 0){
                    longitude = Double.parseDouble(etxtLong.getText().toString());
                }

                try {
                        endereco = buscarCoordenadas(latitude, longitude);
                        txtResult.setText("Rua: " + endereco.getAddressLine(0));
                    } catch (IOException e) {
                        Log.i("GPS", "Método buscar coordenadas: " + e.getMessage());
                }


            }

        });

    }


    private Address buscarCoordenadas(double latitude, double longitude) throws IOException {

        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        if (addresses.size() > 0) {
            address = addresses.get(0);
        }

        return address;

    }

    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("LOG", "onConnected(" + bundle + ")");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i("LOG", "GPS não permitido!");

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location != null){
            Log.i("LOG", "Latitude" + location.getLatitude());
            Log.i("LOG", "Longitude" + location.getLongitude());


        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG", "onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("LOG", "onConnectionFailed(" + connectionResult + ")");

    }
}
