package zpi.mazurek.tomasz.firstversion;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import zpi.lignarski.janusz.CreateTripActivity;
import zpi.lyjak.anna.MainActivity;
import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;

public class ActiveTripActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public LatLng currentlatLng;
    private Location currentLocation;
    private TextView label;
    private ImageButton button;
    private Button changeButton;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private boolean isTripOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_trip);

        label = (TextView) findViewById(R.id.add_finish_label);
        button = (ImageButton) findViewById(R.id.add_finish_button);
        changeButton = (Button) findViewById(R.id.visited);
        changeButton.setVisibility(View.INVISIBLE);
        changeButton.setClickable(false);

        setButtonText();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTripOn)
                {
                    clickEnd();
                }
                else
                {
                    clickAdd();
                }
            }
        });

        SupportMapFragment mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.active_map, mapFragment);
        transaction.commit();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onResume()
    {
        if(mMap != null) {
            addTripMarkes();
            setButtonText();
        }
        super.onResume();
    }


        @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if(isTripOn)
        {
            addTripMarkes();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if(MainActivity.visitedAtractions.get(marker.getTitle()))
                {
                    marker.showInfoWindow();
                    changeButton.setText("Nie odwiedzono");
                    changeButton.setClickable(true);
                    changeButton.setVisibility(View.VISIBLE);
                    changeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            MainActivity.visitedAtractions.put(marker.getTitle(),false);
                            changeButton.setClickable(false);
                            changeButton.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else
                {
                    marker.showInfoWindow();
                    changeButton.setText("Odwiedzono");
                    changeButton.setClickable(true);
                    changeButton.setVisibility(View.VISIBLE);
                    changeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            MainActivity.visitedAtractions.put(marker.getTitle(),true);
                            changeButton.setClickable(false);
                            changeButton.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                return true;
            }
        });


    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            currentlatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        }
        else
            currentlatLng = new LatLng(51.110199, 17.031705);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatLng, 13));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Połączenie z Google Map's powiodło się", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Połączenie się z Google Map's nie było możliwe", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void addTripMarkes()
    {
        mMap.clear();
        ArrayList<Atrakcja> atractions = MainActivity.activeTrip.getAttractions();
        float color;
        for (Atrakcja att: atractions)
        {
            if(MainActivity.visitedAtractions.get(att.getNazwa()))
            {
                color = BitmapDescriptorFactory.HUE_GREEN;
            }
            else
            {
                color = BitmapDescriptorFactory.HUE_RED;
            }
                mMap.addMarker(new MarkerOptions().position(new LatLng(att.getLatitude(),
                        att.getLongitude())).title(att.getNazwa()).icon(BitmapDescriptorFactory.defaultMarker(color)));
        }
    }

    public void clickEnd()
    {
        final Dialog rankDialog = new Dialog(ActiveTripActivity.this, R.style.FullHeightDialog);
        rankDialog.setContentView(R.layout.rank_dialog);
        RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.new_trip_rate);
        ratingBar.setEnabled(true);

        Button yes = (Button) rankDialog.findViewById(R.id.yes_button);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTripOn = false;
                MainActivity.activeTrip = null;
                mMap.clear();
                setButtonText();
                rankDialog.dismiss();
            }

        });
        Button no = (Button) rankDialog.findViewById(R.id.no_button);
        no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                rankDialog.dismiss();
            }
        });
        rankDialog.show();
    }

    public void clickAdd()
    {
        final Dialog rankDialog = new Dialog(ActiveTripActivity.this, R.style.FullHeightDialog);
        rankDialog.setContentView(R.layout.add_dialog);

        Button generate = (Button) rankDialog.findViewById(R.id.generate_button);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rankDialog.dismiss();
                Intent intent = new Intent(v.getContext(), CreateTripActivity.class);
                startActivity(intent);

            }

        });
        Button choose = (Button) rankDialog.findViewById(R.id.choose_button);
        choose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                rankDialog.dismiss();
                Intent intent = new Intent(v.getContext(), RecomendedTrips.class);
                startActivity(intent);
            }
        });
        rankDialog.show();
    }

    public void setButtonText()
    {
        if (MainActivity.activeTrip == null) {
            isTripOn = false;
            label.setText("Dodaj aktywną wycieczkę");
            button.setImageResource(R.drawable.add_icon);
        } else {
            isTripOn = true;
            label.setText("Zakończ wycieczkę");
            button.setImageResource(R.drawable.confirm_icon);
        }
    }



    /*public void drawRoute()
    {
        ArrayList<Location> temporary = locations;
        float minimum = Float.MAX_VALUE;
        Location current = currentLocation;
        Location temp;

        while(temporary.size() > 0) {
            for (Location location : locations) {
                if (currentLocation.distanceTo(location) < minimum) {
                    temp = location;
                    minimum = currentLocation.distanceTo(location);
                }

            }
        }

    }
    */

}
