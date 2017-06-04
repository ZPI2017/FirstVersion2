package zpi.lyjak.anna;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import zpi.lignarski.janusz.AttDetailsFragment;
import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.ShowAtrakcje;

/**
 * @author Anna Łyjak
 * swaggerhub
 */
public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public LatLng currentlatLng; //current user's LatLng
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private int MY_LOCATION_REQUEST_CODE = 100; //necessary to permission // w sumie to nie wiem jaka powinna być wielkość tego czegoś... w przykładach było 100, ale jak tego nie ustawiałam, to też działało xD
    private Location currentLocation; //current user's location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapFragment);
        transaction.commit();
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        onLocationEnabled();

        // Add a marker in Wrocław and move the camera
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatabaseReference mLocations = FirebaseDatabase.getInstance().getReference().child("locations");
                mLocations.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot lokacja : dataSnapshot.getChildren()) {
                            LatLng position = new LatLng((Double)lokacja.child("latitude").getValue(), (Double)lokacja.child("longitude").getValue());
                            mMap.addMarker(new MarkerOptions().position(position).title(lokacja.child("nazwa").getValue().toString())).setTag(lokacja.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MapsMainActivity.this, "Coś się z bało", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
        }.execute();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                AttDetailsFragment fragment = new AttDetailsFragment();
                Bundle b = new Bundle();
                b.putString("attId", marker.getTag().toString());
                fragment.setArguments(b);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.map, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(wroclaw));
    }

    /**
     * Getter for this activity
     *
     * @return this activity
     */
    public Activity getActivity() {
        return this;
    }

    /**
     * The method is necessary for run mGoogleApiClient (user's location)
     */
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    /**
     * The method is necessary for disconnect mGoogleApiClient (user's location)
     */
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Połączenie z Google Map's powiodło się", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Połączenie się z Google Map's nie było możliwe", Toast.LENGTH_SHORT).show();
    }

    /**
     * Setting the zoom and camera position
     */
    private void setCamera(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng)); // podstawowa metoda, bez kontroli nad zoomem - nie używać!
    }

    /**
     * Granting access to read the current position of the user
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0].equals(android.Manifest.permission.ACCESS_FINE_LOCATION) && //==
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 23) { // Marshmallow
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
                    } else {
                        //TODO zastanowić się czy robimy coś w przypadku, gdy użytkownik nie zezwolił na udostępnianie swojej lokalizacji - moim zdaniem możemy elsa po prostu olać xD
                        // (gdyż dla api > 23 już wymusiłam ponownego permissiona, nie chce mi się szukać jak to zrobić dla niższych wersji)
                    }
                    return;
                }
                mMap.setMyLocationEnabled(true);
                Toast.makeText(this, "Permission ACCESS_FINE_LOCATION granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission was denied!", Toast.LENGTH_SHORT).show(); // Permission was denied. Display an error message.
            }
        }
    }

    /**
     * Connected to Location (permission in AndroidManifest), user MUST accept this permission if he want use app.
     *
     * @param connectionHint simple bundle (I don't use it)
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) { // Marshmallow
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
            } // else - podobnie jak w onRequestPermissionsResult
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (currentLocation != null) {
            currentlatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

            //TODO zrobiłam zoom na aktualną pozycję użytkownika, moim zdaniem najsensowniejsze rozwiązanie, ale możemy zmienić ;)
            setCamera(currentlatLng); // set camera to actuall position

            //TODO delete the code : //dodany żeby pokazać Wam, o co w tym chodzi ;)
            try {
                Toast.makeText(this, getActualLocactionAdress(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO end to delete.
        }

    }

    /**
     * Method inclusive current user's point's position
     */
    private void onLocationEnabled() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(MapsMainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);

        }
    }

    /**
     * Read the name of the current user's position from Geocoder
     * returning string of address of 'Feature name - Locality - Thoroughfare - Country name'
     */
    private String getActualLocactionAdress() throws IOException {
        String address = "";
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = gc.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        if (addressList.size() > 0 && addressList != null) {
            address = (addressList.get(0).getFeatureName() + "-" +
                    "" + addressList.get(0).getLocality() + "-" + addressList.get(0).getThoroughfare() + "-" + addressList.get(0).getCountryName());
        }
        return address;
    }

    /**
     * The method is calling when Activity is disabled
     */
    @Override
    protected void onDestroy() {
        //TODO Niezbędne jak już wprowadzimy bazę danych (wystarczy wyłączyć komentarz)
//        if (dbHelper != null) {
//            dbHelper.close();
//        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
