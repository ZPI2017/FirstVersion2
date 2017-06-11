package zpi.mazurek.tomasz.firstversion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import zpi.lyjak.anna.firstversion.R;

public class ActiveTripActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private Trip activeTrip;
    private TextView label;
    private ImageButton button;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_trip);

        label = (TextView) findViewById(R.id.add_finish_label);
        button = (ImageButton) findViewById(R.id.add_finish_button);

        if(activeTrip == null)
        {
            label.setText("Dodaj aktywną wycieczkę");
            button.setImageResource(R.drawable.add_icon);
        }
        else
        {
            label.setText("Zakończ wycieczkę");
            button.setImageResource(R.drawable.confirm_icon);
        }

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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Połączenie z Google Map's powiodło się", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Połączenie się z Google Map's nie było możliwe", Toast.LENGTH_SHORT).show();

    }
}
