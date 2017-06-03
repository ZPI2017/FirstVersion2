package zpi.mazurek.tomasz.firstversion;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import zpi.lyjak.anna.firstversion.R;


public class TripHistoryActivity extends AppCompatActivity implements OnItemClickListener {

    Trip[] trips;

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

        ListView lv = (ListView) this.findViewById(R.id.tripHistoryList);
        lv.setAdapter(new TripAdapter(this, trips));

        TextView title = (TextView) this.findViewById(R.id.history_title);
        if(trips.length < 1)
            title.setText("Nie masz żadnych odbytych wycieczek!");
        else
            title.setText("Przebyte wyciecki");

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

    class TripAdapter extends BaseAdapter {

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
        public int getCount() {
            return trips.length;
        }

        @Override
        public Trip getItem(int i) {
            return trips[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View tripRow = inflater.inflate(R.layout.trips_history_row, parent,false);
            TextView duration = (TextView) tripRow.findViewById(R.id.trip_duration);
            duration.setText("Czas trwania: " + getItem(position).getTripTimeAsString());

            TextView startDate = (TextView) tripRow.findViewById(R.id.start_date);
            startDate.setText("Początek: " + getItem(position).getStartDate());

            TextView endDate = (TextView) tripRow.findViewById(R.id.end_date);
            endDate.setText("Koniec: " + getItem(position).getEndDate());

            RatingBar rating = (RatingBar) tripRow.findViewById(R.id.trip_rate);
            rating.setRating(getItem(position).getRate());
            return tripRow;
        }
    }

