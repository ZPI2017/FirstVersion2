package zpi.mazurek.tomasz.firstversion;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;


public class TripHistoryActivity extends AppCompatActivity {

    Trip[] trips;
    private TripAdapter listAdapter;
    private ExpandableListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trip_history);


        //Przykładowe wartości do prezentacji

        trips = new Trip[2];
        trips[0] = new Trip();
        trips[0].endTrip();
        trips[0].setRate(3);
        trips[1] = new Trip();
        trips[1].endTrip();
        trips[1].setRate(5);
        trips[0].attractions.add(new Atrakcja("Hala Stulecia", "", ""));
        trips[0].attractions.add(new Atrakcja("Zoo", "", ""));
        trips[0].attractions.add(new Atrakcja("Stadion Olimpijski", "", ""));


        lv = (ExpandableListView) this.findViewById(R.id.tripHistoryList);
        listAdapter = new TripAdapter(getApplicationContext(), trips);

        lv.setAdapter(listAdapter);

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

        TextView title = (TextView) this.findViewById(R.id.history_title);
        if(trips.length < 1)
            title.setText("Nie masz żadnych odbytych wycieczek!");
        else
            title.setText("Przebyte wyciecki");

    }

}

    class TripAdapter extends BaseExpandableListAdapter {

        private Context context;
        private Trip[] trips;
        private LayoutInflater inflater;

        TripAdapter(Context context, Trip[] trips)
        {
            this.context = context;
            this.trips = trips;
            inflater = LayoutInflater.from(context);

        }


        @Override
        public int getGroupCount() {
            return trips.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return trips[i].getAttractions().size();
        }

        @Override
        public Trip getGroup(int i) {
            return trips[i];
        }

        @Override
        public Atrakcja getChild(int i, int i1) {
            return trips[i].getAttractions().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false    ;
        }

        @Override
        public View getGroupView(final int position, final boolean isExpanded, View view, ViewGroup parent) {


            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.trips_history_row, parent,false);
            }
            TextView duration = (TextView) view.findViewById(R.id.trip_duration);
            duration.setText("Czas trwania: " + getGroup(position).getTripTimeAsString());

            TextView startDate = (TextView) view.findViewById(R.id.start_date);
            startDate.setText("Początek: " + getGroup(position).getStartDate());

            TextView endDate = (TextView) view.findViewById(R.id.end_date);
            endDate.setText("Koniec: " + getGroup(position).getEndDate());

            RatingBar rating = (RatingBar) view.findViewById(R.id.trip_rate);
            rating.setRating(getGroup(position).getRate());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup parent) {

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.attraction_row, parent,false);
            }

            TextView att = (TextView) view.findViewById(R.id.att_name);
            att.setText(getChild(i, i1).getNazwa());

            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

