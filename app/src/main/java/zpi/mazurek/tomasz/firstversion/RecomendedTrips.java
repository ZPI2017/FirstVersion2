package zpi.mazurek.tomasz.firstversion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import zpi.lignarski.janusz.ImageLoadTask;
import zpi.lyjak.anna.firstversion.R;

public class RecomendedTrips extends AppCompatActivity {

    ArrayList<BaseTrip> trips;
    RecomendedTripAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        trips = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomended_trips);
        prepareTrips();
        mAdapter = new RecomendedTripAdapter(this,trips);
        RecyclerView list = (RecyclerView) this.findViewById(R.id.recomended_trips);
        final RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(mAdapter);

    }

    private void prepareTrips() {
        DatabaseReference mRecommended = FirebaseDatabase.getInstance().getReference().child("baseTrips");
        mRecommended.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    trips.add((snap.getValue(BaseTrip.class)));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RecomendedTrips.this, "Coś się z bało", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class RecomendedTripAdapter extends RecyclerView.Adapter<RecomendedTripAdapter.ViewHolder2>
{
    Context context;
    ArrayList<BaseTrip> trips;
    LayoutInflater inflater;

    RecomendedTripAdapter(Context context, ArrayList<BaseTrip> trips)
    {
        this.context = context;
        this.trips = trips;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecomendedTripAdapter.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View tripCell = inflater.inflate(R.layout.recomended_trip_cell, parent, false);

        RecomendedTripAdapter.ViewHolder2 vHolder=new ViewHolder2(tripCell);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(RecomendedTripAdapter.ViewHolder2 holder, int position) {

        BaseTrip trip = trips.get(position);
        holder.name.setText(trip.getName());
        new ImageLoadTask(trip.getPhotoURL(), holder.cover).execute();
        holder.rate.setRating(trip.getRate());

        Iterator<String> iterator = trip.getAttractions().keySet().iterator();
        switch(trip.getAttractions().size()) {
            case 0:
                holder.att0.setVisibility(View.INVISIBLE);
                holder.att1.setVisibility(View.INVISIBLE);
                holder.att2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.att0.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                holder.att1.setVisibility(View.INVISIBLE);
                holder.att2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                holder.att0.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                holder.att1.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                holder.att2.setVisibility(View.INVISIBLE);
                break;
            default:
                holder.att0.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                holder.att1.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                holder.att2.setText(trip.getAttractions().get(iterator.next()).get("nazwa"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView name, att0, att1, att2;
        ImageView cover;
        RatingBar rate;
        ViewHolder2(View tripCell) {
            super(tripCell);
            name = (TextView)tripCell.findViewById(R.id.trip_name);
            cover = (ImageView) tripCell.findViewById(R.id.trip_cover);
            rate = (RatingBar) tripCell.findViewById(R.id.recomended_trip_rate);
            att0 = (TextView) tripCell.findViewById(R.id.attraction0);
            att1 = (TextView) tripCell.findViewById(R.id.attraction1);
            att2 = (TextView) tripCell.findViewById(R.id.attraction2);
        }
    }
}
