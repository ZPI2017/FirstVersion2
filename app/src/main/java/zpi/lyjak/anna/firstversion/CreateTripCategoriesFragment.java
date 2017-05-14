package zpi.lyjak.anna.firstversion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CreateTripCategoriesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_trip_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) getView().findViewById(R.id.listViewCreateTripCat);
        listView.setAdapter(new CreateTripCategoriesAdapter(getContext(), cats));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Klk", Toast.LENGTH_SHORT).show();
                CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public interface OnFragmentInteractionListener {
        void onTripCategoriesContinue();
        //TODO przkazać odpowiednio zaznaczone
    }

    private static List<String> cats = Arrays.asList("Zabytki", "Kościoły", "Muzea", "Galerie sztuki",
            "Przyroda", "Jedzenie", "Galerie handlowe", "Obiekty kulturalne");
}
