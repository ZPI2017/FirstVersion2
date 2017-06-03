package zpi.mazurek.tomasz.firstversion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import zpi.lignarski.janusz.ImageLoadTask;
import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;
import zpi.szymala.kasia.firstversion.ShowAtrakcje;

public class RecomendedTrips extends AppCompatActivity {

    BaseTrip[] trips;
    RecomendedTripAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        trips = new BaseTrip[3];
//        trips[0] = new BaseTrip("Najlepsze atrakce", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/hala.jpeg?alt=media&token=a3ed343e-8f79-434a-9b67-2b72a20366a7");
//        trips[0].setRate(4);
//        trips[1] = new BaseTrip("Wycieczka2", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/hala.jpeg?alt=media&token=a3ed343e-8f79-434a-9b67-2b72a20366a7");
//        trips[2] = new BaseTrip("Wycieczka3", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/hala.jpeg?alt=media&token=a3ed343e-8f79-434a-9b67-2b72a20366a7");
//
//        trips[0].addAttraction(new Atrakcja("Hala Stulecia", "Costam", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/hala.jpeg?alt=media&token=a3ed343e-8f79-434a-9b67-2b72a20366a7", 51.106869, 17.077285));
//        trips[0].addAttraction(new Atrakcja("Rynek", "Costam", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/rynek.jpg?alt=media&token=283fa1ea-53e9-4181-899a-80700d75a1be", 51.110108, 17.032062));
//        trips[0].addAttraction(new Atrakcja("Afrykarium", "Costam", "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/afrykarium.jpg?alt=media&token=7f4bb4aa-ba1a-42d6-9297-92c65f50c5bb", 51.104389, 17.075356));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomended_trips);

        ListView list = (ListView) this.findViewById(R.id.recomended_trips);
        prepareTrips(list);

    }

    private void prepareTrips(final ListView list) {
        DatabaseReference mRecommended = FirebaseDatabase.getInstance().getReference().child("baseTrips");
        mRecommended.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trips = new BaseTrip[(int)(dataSnapshot.getChildrenCount())];
                int i = 0;
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    trips[i] = (snap.getValue(BaseTrip.class));
                    i++;
                }

                list.setAdapter(new RecomendedTripAdapter(RecomendedTrips.this, trips));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RecomendedTrips.this, "Coś się z bało", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class RecomendedTripAdapter extends BaseAdapter
{
    Context context;
    BaseTrip[] trips;
    LayoutInflater inflater;

    RecomendedTripAdapter(Context context, BaseTrip[] trips)
    {
        this.context = context;
        this.trips = trips;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return trips.length;
    }

    @Override
    public Object getItem(int i) {
        return trips[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View tripCell = inflater.inflate(R.layout.recomended_trip_cell, viewGroup,false);
        TextView name = (TextView) tripCell.findViewById(R.id.trip_name);
        name.setText(trips[i].getName());
        ImageView cover = (ImageView) tripCell.findViewById(R.id.trip_cover);
        new ImageLoadTask(trips[i].getPhotoURL(), cover).execute();
        RatingBar rate = (RatingBar) tripCell.findViewById(R.id.recomended_trip_rate);
        rate.setRating(trips[i].getRate());

        TextView att0 = (TextView) tripCell.findViewById(R.id.attraction0);
        TextView att1 = (TextView) tripCell.findViewById(R.id.attraction1);
        TextView att2 = (TextView) tripCell.findViewById(R.id.attraction2);

        switch(trips[i].getAttractions().size()) {
            case 0:
                att0.setVisibility(View.INVISIBLE);
                att1.setVisibility(View.INVISIBLE);
                att2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                att0.setText(trips[i].getAttractions().get(0).getNazwa());
                att1.setVisibility(View.INVISIBLE);
                att2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                att0.setText(trips[i].getAttractions().get(0).getNazwa());
                att1.setText(trips[i].getAttractions().get(1).getNazwa());
                att2.setVisibility(View.INVISIBLE);
                break;
            default:
                att0.setText(trips[i].getAttractions().get(0).getNazwa());
                att1.setText(trips[i].getAttractions().get(1).getNazwa());
                att2.setText(trips[i].getAttractions().get(2).getNazwa());
                break;
        }

        tripCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, LatLng> map = new HashMap<>();
                for(int j = 0; j < trips[i].getAttractions().size(); j++)
                {
                    map.put(trips[i].getAttractions().get(j).getNazwa(), trips[i].getAttractions().get(j).buildLocation());
                }
                Intent intent = new Intent(context, TripOnMapActivity.class);
                intent.putExtra("map", map);
                context.startActivity(intent);
            }
        });

        return tripCell;
    }
}
