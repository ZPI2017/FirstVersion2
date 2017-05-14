package zpi.lyjak.anna.firstversion;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateTripDatesFragment extends Fragment {

    Context mContext;
    EditText startDate;
    EditText endDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_trip_dates, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        startDate = (EditText) getView().findViewById(R.id.editTextPocz);
        endDate = (EditText) getView().findViewById(R.id.editTextKon);
        startDate.setOnClickListener(dateListener);
        endDate.setOnClickListener(dateListener);
        Button button = (Button) getView().findViewById(R.id.buttonCreateTripDatesNext);
        button.setOnClickListener(buttonListener);
    }

    View.OnClickListener dateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((OnDateInputSelectedListener)mContext).onDateInputSelected(v);
        }
    };

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String start = startDate.getText().toString();
            String end = endDate.getText().toString();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            try {
                if (start.length() == 0)
                    Toast.makeText(getActivity(), "Wprowadź datę rozpoczęcia!", Toast.LENGTH_LONG).show();
                else if (end.length() == 0)
                    Toast.makeText(getActivity(), "Wprowadź datę zakończenia!", Toast.LENGTH_LONG).show();
                else if (df.parse(start).after(df.parse(end)))
                    Toast.makeText(getActivity(), "Nie można zakończyć wycieczki przed jej rozpoczęciem!", Toast.LENGTH_LONG).show();
                else {
                    if (!((CheckBox)getView().findViewById(R.id.checkBoxPlanAut)).isChecked()) {
                        Toast.makeText(getActivity(), "Utworzono nową wycieczkę!", Toast.LENGTH_LONG).show();
                        //TODO zrobić przejście do mapy w kontekscie nowej wycieczki
                    }
                    else {
                        ((CreateTripDatesContinueListener)mContext).createTripDatesContinue(start, end);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    public interface OnDateInputSelectedListener {
        void onDateInputSelected(View v);
    }

    public interface CreateTripDatesContinueListener {
        void createTripDatesContinue(String startDate, String endDate);
    }
}