package zpi.mazurek.tomasz.firstversion;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import zpi.lignarski.janusz.DatePickerFragment;
import zpi.lignarski.janusz.ImageLoadTask;
import zpi.lyjak.anna.DayOfTrip;
import zpi.lyjak.anna.firstversion.R;

public class RecomendedTrips extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList<BaseTrip> trips;
    RecomendedTripAdapter mAdapter;
    public BaseTrip chosenTrip;
    public Trip newTrip;

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        newTrip = new Trip();
        newTrip.setStartDate(calendar);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(year, month, (int) (dayOfMonth+chosenTrip.getEstimatedTime()-1));
        newTrip.setEndDate(calendarEnd);

        //TODO być może dodać ajdik

        chosenTrip.buildTripDays(calendar, this);
    }

    public void finishCreating(ArrayList<DayOfTrip> days) {
        newTrip.setDays(days);

        //TODO mamy gotową wycieczkę

        Toast.makeText(this, "Zaimportowano wycieczkę" + newTrip.countAttractions(), Toast.LENGTH_LONG).show();
        finish();
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

        tripCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
//
//        holder.itemView.setLongClickable(true);

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        TextView name, att0, att1, att2;
        CardView card;
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
            card = (CardView) tripCell.findViewById(R.id.card_view);
            card.setOnClickListener(this);
            card.setOnLongClickListener(this);
            
        }
        @Override
        public void onClick(View view)
        {
            HashMap<String, HashMap<String, String>> map = trips.get(getAdapterPosition()).getAttractions();
            Intent intent = new Intent(context, TripOnMapActivity.class);
            intent.putExtra("map", map);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            ((RecomendedTrips) context).chosenTrip = trips.get(getAdapterPosition());
            DialogFragment fragment = new DatePickerFragment();
            fragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "datePicker");
            Toast.makeText(context, "Wybierz datę rozpoczęcia", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}

