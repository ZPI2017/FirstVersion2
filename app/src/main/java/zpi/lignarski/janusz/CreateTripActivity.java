package zpi.lignarski.janusz;

import android.app.DatePickerDialog;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import zpi.lyjak.anna.DayOfTrip;
import zpi.lyjak.anna.MainActivity;
import zpi.lyjak.anna.firstversion.R;
import zpi.mazurek.tomasz.firstversion.Trip;
import zpi.szymala.kasia.firstversion.Atrakcja;

public class CreateTripActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener,
        CreateTripDatesFragment.CreateTripDatesListener,
        CreateTripCategoriesFragment.CreateTripCategoriesListener {

    EditText dateText;
    Trip nowaWycieczka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        CreateTripDatesFragment fragment = new CreateTripDatesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.LayoutMain, fragment);
        transaction.commit();
    }

    @Override
    public void onDateInputSelected(View v) {
        dateText = (EditText) v;
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void createTripDatesEnd(Date startDate, Date endDate) {
        nowaWycieczka = new Trip();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        nowaWycieczka.setStartDate(start);
        nowaWycieczka.setEndDate(end);

        //TODO zapisać wycieczkę
        MainActivity.activeTrip = nowaWycieczka;

        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (dateText != null) {
            dateText.setText(dayOfMonth + "-" + (++month<10?"0" + month:month) + "-" + year);
        }
    }

    @Override
    public void createTripDatesContinue(Date startDate, Date endDate) {
        nowaWycieczka = new Trip();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        nowaWycieczka.setStartDate(start);
        nowaWycieczka.setEndDate(end);
        CreateTripCategoriesFragment fragment = new CreateTripCategoriesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.LayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onTripCategoriesContinue(final ArrayList<Category> categories, final boolean powoli) {
        final ArrayList<Atrakcja> atrakcje = new ArrayList<>();
        final ValueEventListener listener = new ValueEventListener() {
            private final Object lock = new Object();
            int i = categories.size();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    synchronized (atrakcje) {
                        atrakcje.add(child.getValue(Atrakcja.class));
                    }
                }
                synchronized (lock) {
                    i--;
                    if (i == 0)
                        buildNewTrip(atrakcje, powoli);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        for (Category category : categories) {
            Query query = mDatabase.child("places").orderByChild("kategoria").startAt(category.getId()).endAt(category.getId());
            query.addListenerForSingleValueEvent(listener);
        }
    }

    private void buildNewTrip(ArrayList<Atrakcja> atrakcje, boolean powoli) {
        int numOfDays = nowaWycieczka.countDays();
        ArrayList<DayOfTrip> dni = new ArrayList<>();
        for (int i = 0; i < numOfDays; i++) {
            DayOfTrip day = new DayOfTrip();
            day.setAttractions(new ArrayList<Atrakcja>());
            dni.add(day);
        }
        nowaWycieczka.setDays(randomTrip(dni, atrakcje, powoli));
        Toast.makeText(this, "Wygenerowano wycieczkę! Liczba atrakcji: " + nowaWycieczka.countAttractions(), Toast.LENGTH_SHORT).show();

        MainActivity.activeTrip = nowaWycieczka;
        finish();
    }

    private ArrayList<DayOfTrip> randomTrip(ArrayList<DayOfTrip> dni, ArrayList<Atrakcja> atrakcje, boolean powoli) {
        MainActivity.visitedAtractions = new HashMap<>();
        int ilosc = powoli?3:5;
        ArrayList<Atrakcja> temp = (ArrayList<Atrakcja>) atrakcje.clone();
        Random random = new Random();
        for (int i = 0; i <dni.size() && !atrakcje.isEmpty(); i++) {
            for (int j = 0; j < ilosc && !atrakcje.isEmpty(); j++) {
                dni.get(i).getAttractions().add(atrakcje.remove(random.nextInt(atrakcje.size())));
                MainActivity.visitedAtractions.put(dni.get(i).getAttractions().get(dni.get(i).getAttractions().size()-1).getNazwa(), false);
            }
        }
        return dni;
    }
}
