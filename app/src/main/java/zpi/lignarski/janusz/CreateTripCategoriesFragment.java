package zpi.lignarski.janusz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import zpi.lyjak.anna.firstversion.R;


public class CreateTripCategoriesFragment extends Fragment {

    private CreateTripCategoriesListener mListener;
    CreateTripCategoriesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_trip_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button button = (Button) getView().findViewById(R.id.button);
        button.setOnClickListener(buttonListener);
        final ListView listView = (ListView) getView().findViewById(R.id.listViewCreateTripCat);
        final List<Category> cats = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cats.clear();
                for (DataSnapshot category : dataSnapshot.getChildren()) {
                    cats.add(category.getValue(Category.class));
                }
                listView.setAdapter(mAdapter = new CreateTripCategoriesAdapter(getContext(), cats));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Coś się z bało", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onTripCategoriesContinue(mAdapter.getCheckedCategories(), ((RadioButton) getView().findViewById(R.id.radioSlow)).isChecked());
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateTripCategoriesListener) {
            mListener = (CreateTripCategoriesListener) context;
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

    public interface CreateTripCategoriesListener {
        void onTripCategoriesContinue(ArrayList<Category> categories, boolean slow);
    }

}
