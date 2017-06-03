package zpi.lignarski.janusz;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import zpi.lyjak.anna.firstversion.R;

public class CreateTripActivity extends AppCompatActivity
        implements CreateTripDatesFragment.OnDateInputSelectedListener,
        DatePickerDialog.OnDateSetListener,
        CreateTripDatesFragment.CreateTripDatesContinueListener,
        CreateTripCategoriesFragment.OnFragmentInteractionListener {

    Button buttonMain;
    EditText dateText;

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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (dateText != null) {
            dateText.setText(dayOfMonth + "-" + (++month<10?"0" + month:month) + "-" + year);
        }
    }

    @Override
    public void createTripDatesContinue(String startDate, String endDate) {
        //TODO zapisać daty
        CreateTripCategoriesFragment fragment = new CreateTripCategoriesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.LayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onTripCategoriesContinue() {

    }
}
