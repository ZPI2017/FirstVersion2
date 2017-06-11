package zpi.szymala.kasia.firstversion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;
import zpi.lignarski.janusz.CreateTripActivity;
import zpi.lyjak.anna.MapsMainActivity;
import zpi.lyjak.anna.firstversion.R;
import zpi.lyjak.anna.fragments.ExampleFragment;
import zpi.mazurek.tomasz.firstversion.ActiveTripActivity;
import zpi.mazurek.tomasz.firstversion.RecomendedTrips;
import zpi.mazurek.tomasz.firstversion.TripHistoryActivity;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class ShowAtrakcje extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Atrakcja> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showatrakcje);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //  mRecyclerView.setHasFixedSize(true);
        myDataset=new ArrayList<>();
        mAdapter = new MyAdapter(this, myDataset);

        // use a linear layout manager
        final RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        prepareAtrakcje();
    }

    private void prepareAtrakcje() {
        DatabaseReference mAtrakcje = FirebaseDatabase.getInstance().getReference().child("places");
        mAtrakcje.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot atrakcja : dataSnapshot.getChildren()) {
                    myDataset.add(atrakcja.getValue(Atrakcja.class));
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowAtrakcje.this, "Coś się z bało", Toast.LENGTH_SHORT).show();
            }
        });

//        String[] zdjecia=new String[]{
//                "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/afrykarium.jpg?alt=media&token=7f4bb4aa-ba1a-42d6-9297-92c65f50c5bb",
//                "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/hala.jpeg?alt=media&token=a3ed343e-8f79-434a-9b67-2b72a20366a7",
//                "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/ogrod.jpg?alt=media&token=1e7154bc-f65f-44cc-9e8e-68e40620c0ee",
//                "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/rynek.jpg?alt=media&token=283fa1ea-53e9-4181-899a-80700d75a1be",
//                "https://firebasestorage.googleapis.com/v0/b/zpi2017-77741.appspot.com/o/wieza.jpg?alt=media&token=864e564e-bff4-4e1f-9820-f0d0cc7c9c7c",
//
//        };
//
//        Atrakcja atrakcja=new Atrakcja("Wrocławskie ZOO","Ogród zoologiczny znajdujący się przy ul. Wróblewskiego 1–5 we Wrocławiu, otwarty 10 lipca 1865. Jest najstarszym na obecnych ziemiach polskich ogrodem zoologicznym w Polsce. Powierzchnia ogrodu to 33 hektary.",
//                zdjecia[0], 51.104126, 17.074197);
//        myDataset.add(atrakcja);
//
//        atrakcja=new Atrakcja("Hala stulecia","Hala widowiskowo-sportowa znajdująca się we Wrocławiu, na osiedlu Zalesie, w parku Szczytnickim. Wzniesiona w latach 1911–1913 według projektu Maxa Berga, w stylu ekspresjonistycznym.",
//                zdjecia[1], 51.106869, 17.077285);
//        myDataset.add(atrakcja);
//
//        atrakcja=new Atrakcja("Ogród japoński","Znajduje się w Parku Szczytnickim w Śródmieściu Wrocławia.Ogród japoński został założony w latach 1909-1913 wokół dawnego stawu Ludwiga Theodora Moritza-Eichborna w obrębie obecnego Parku Szczytnickiego.",
//                zdjecia[2], 51.109937, 17.080077);
//        myDataset.add(atrakcja);
//
//        atrakcja=new Atrakcja("Rynek","Sredniowieczny plac targowy we Wrocławiu, obecnie centralna część strefy pieszej. Stanowi prostokąt o wymiarach 213 na 178 m, oraz powierzchni 3,8 ha. Jest to jeden z największych rynków staromiejskich Europy, z największymi ratuszami w Polsce. ",
//                zdjecia[3], 51.109454, 17.031332);
//        myDataset.add(atrakcja);
//
//        atrakcja=new Atrakcja("Wieża ciśnień","Historyzująca wieża ciśnień o wysokości całkowitej 62 m, zlokalizowana na osiedlu Borek we Wrocławiu. Oficjalny adres wieży to: ul. Sudecka 125a, jednak znajduje się ona na pasie rozdzielającym jezdnie al. Wiśniowej w ciągu obwodnicy śródmiejskiej. ",
//                zdjecia[4], 51.085329, 17.017575);
//        myDataset.add(atrakcja);

    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ExampleFragment.CLOSE:
                return screenShotable;
            case "Active Trip":
                startActivity(new Intent(this, ActiveTripActivity.class));
                return screenShotable;
            case "Trip History":
                startActivity(new Intent(this, TripHistoryActivity.class));
                return screenShotable;
            case "Recomended Trips":
                startActivity(new Intent(this, RecomendedTrips.class));
                return screenShotable;
            case "Map":
                startActivity(new Intent(this, MapsMainActivity.class));
                return screenShotable;
            case "Add Trips":
                startActivity(new Intent(this, CreateTripActivity.class));
                return screenShotable;
            case "Details":
                startActivity(new Intent(this, ShowAtrakcje.class));
                return screenShotable;
            default:
                startActivity(new Intent(this, MapsMainActivity.class));
                return screenShotable;
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
//        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
    private LinearLayout linearLayout;


}
