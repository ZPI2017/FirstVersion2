package zpi.mazurek.tomasz.firstversion;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import zpi.lyjak.anna.DayOfTrip;
import zpi.szymala.kasia.firstversion.Atrakcja;

/**
 * Created by Tomasz on 28.05.2017.
 */

public class BaseTrip {

    private long estimatedTime;
    private float rate;
    private String photoURL;
    private String name;
    private HashMap<String, HashMap<String, String>> attractions;
    private int attSize;
    private ArrayList<DayOfTrip> days;

    public BaseTrip() {
//        setName("");
        attractions = new HashMap<>();
//        setRate(0);
    }

    public BaseTrip(String name, String photoURL) {
        this.setPhotoURL(photoURL);
        this.setName(name);
        setRate(0);
    }

    public long getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, HashMap<String, String>> getAttractions() {
        return attractions;
    }

    public void setAttractions(HashMap<String, HashMap<String, String>> attractions) {
        this.attractions = attractions;
    }

    public void buildTripDays(Calendar start, final RecomendedTrips context) {
        days = new ArrayList<>();
        for (int i = 0; i < estimatedTime; i++) {
            DayOfTrip day = new DayOfTrip();
            Calendar date = Calendar.getInstance();
            date.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH) + i);
            day.setDate(date);
            day.setAttractions(new ArrayList<Atrakcja>());

            //TODO czy trzeba ustawić id?

            days.add(day);
        }
        attSize = attractions.size();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Atrakcja atrakcja = dataSnapshot.getValue(Atrakcja.class);
                addToDay(dataSnapshot.getKey(), atrakcja);
                if (decrementAttSize() == 0)
                    context.finishCreating(days);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("places");
        for (String key : attractions.keySet()) {
            database.child(key).addListenerForSingleValueEvent(listener);
        }
    }

    private synchronized int decrementAttSize() {
        attSize--;
        return attSize;
    }

    private synchronized void addToDay(String id, Atrakcja atrakcja) {
        days.get(Integer.parseInt(attractions.get(id).get("day"))).getAttractions().add(atrakcja);
    }
}
