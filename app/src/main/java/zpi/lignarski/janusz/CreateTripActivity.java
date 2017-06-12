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
    public void onTripCategoriesContinue(ArrayList<Category> categories, boolean powoli) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(CreateTripActivity.this, Long.toString(dataSnapshot.getChildrenCount()), Toast.LENGTH_LONG).show();
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
}
