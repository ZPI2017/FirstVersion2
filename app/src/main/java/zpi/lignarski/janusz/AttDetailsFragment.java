package zpi.lignarski.janusz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import zpi.lyjak.anna.firstversion.R;
import zpi.szymala.kasia.firstversion.Atrakcja;


/**
 * A simple {@link Fragment} subclass.
 */
public class AttDetailsFragment extends Fragment {

    String mAttId;
    Atrakcja atrakcja;

    ImageView imageView;
    TextView textViewName;
    TextView textViewDesc;
    TextView textViewHours;

    public AttDetailsFragment() {
        // Required empty public constructor
    }

    public AttDetailsFragment(Atrakcja at) {
        atrakcja = at;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (atrakcja == null) {
            Bundle args = getArguments();
            mAttId = args.getString("attId", "-1");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_att_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        imageView = (ImageView) getView().findViewById(R.id.attDetailsIm);
        textViewName = (TextView) getView().findViewById(R.id.attDetailsName);
        textViewDesc = (TextView) getView().findViewById(R.id.attDetailDesc);
        textViewHours = (TextView) getView().findViewById(R.id.attDetailsHours);
        if (atrakcja == null) {
            DatabaseReference mAtrakcje = FirebaseDatabase.getInstance().getReference().child("places");
            mAtrakcje.child(mAttId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    atrakcja = dataSnapshot.getValue(Atrakcja.class);
                    prepareData();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
            prepareData();
    }

    private void prepareData() {
        textViewName.setText(atrakcja.getNazwa());
        textViewDesc.setText(atrakcja.getSzczegoly());
        new ImageLoadTask(atrakcja.getZdjecieURL(), imageView).execute();
        if (atrakcja.isZawszeOtwarte())
            textViewHours.setVisibility(View.GONE);
        textViewHours.setText(atrakcja.buildGodzinyOtwarcia());
    }
}
