package zpi.mazurek.tomasz.firstversion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;

public class RecomendedTrips extends AppCompatActivity {

    BaseTrip[] trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        trips = new BaseTrip[3];
        trips[0] = new BaseTrip("Najlepsze atrakce", R.drawable.hala);
        trips[0].setRate(4);
        trips[1] = new BaseTrip("Wycieczka2", R.drawable.hala);
        trips[2] = new BaseTrip("Wycieczka3", R.drawable.hala);

        trips[0].addAttraction(new Atrakcja("Hala Stulecia", "Costam", R.drawable.hala, new LatLng(51.106869, 17.077258)));
        trips[0].addAttraction(new Atrakcja("Rynek", "Costam", R.drawable.rynek, new LatLng(51.110108, 17.032062)));
        trips[0].addAttraction(new Atrakcja("Afrykarium", "Costam", R.drawable.afrykarium, new LatLng(51.104389, 17.075356)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomended_trips);

        ListView list = (ListView) this.findViewById(R.id.recomended_trips);
        list.setAdapter(new RecomendedTripAdapter(this, trips));

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
        cover.setImageResource(trips[i].getPhoto());
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
                    map.put(trips[i].getAttractions().get(j).getNazwa(), trips[i].getAttractions().get(j).getLocation());
                }
                Intent intent = new Intent(context, TripOnMapActivity.class);
                intent.putExtra("map", map);
                context.startActivity(intent);
            }
        });

        return tripCell;
    }
}
