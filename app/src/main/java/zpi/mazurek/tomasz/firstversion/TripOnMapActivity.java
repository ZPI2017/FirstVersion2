package zpi.mazurek.tomasz.firstversion;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import zpi.lignarski.janusz.AttDetailsFragment;
import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;


public class TripOnMapActivity extends AppCompatActivity {

    MapView mapView;
    HashMap<String, HashMap<String, String>> positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_on_map);

        Intent intent = getIntent();
        positions = (HashMap<String, HashMap<String, String>>)intent.getSerializableExtra("map");

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setUpMap(googleMap);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void setUpMap(GoogleMap map)
    {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.109489, 17.03284), 13));
        for (String entry : positions.keySet())
        {
            String name = positions.get(entry).get("nazwa");
            double lat = Double.parseDouble(positions.get(entry).get("latitude"));
            double lon = Double.parseDouble(positions.get(entry).get("longitude"));
            map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name)).setTag(entry);
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                AttDetailsFragment fragment = new AttDetailsFragment();
                Bundle b = new Bundle();
                b.putString("attId", marker.getTag().toString());
                fragment.setArguments(b);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.yripOnMapLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
