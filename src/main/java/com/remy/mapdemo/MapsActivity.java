package com.remy.mapdemo;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        builder = new AlertDialog.Builder(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cph = new LatLng(55.6761, 12.5683);
        mMap.addMarker(new MarkerOptions().position(cph).title("Marker in Copenhagen"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cph));

        LatLng pizza = new LatLng(55,12);
        mMap.addMarker(new MarkerOptions().position(pizza).title("Pizza"));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                final EditText editText = new EditText(MapsActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMap.addMarker(new MarkerOptions().position(latLng).title(editText.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
                //markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                googleMap.clear();

                float zoomLevel = 8.0f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

                googleMap.addMarker(markerOptions);
            }
        });
    }
}
