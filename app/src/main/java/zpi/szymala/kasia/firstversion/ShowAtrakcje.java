package zpi.szymala.kasia.firstversion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zpi.lyjak.anna.firstversion.R;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class ShowAtrakcje extends AppCompatActivity {
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
        int[] zdjecia=new int[]{
                R.drawable.afrykarium,
                R.drawable.hala,
                R.drawable.ogrod,
                R.drawable.rynek,
                R.drawable.wieza,

        };

        Atrakcja atrakcja=new Atrakcja("Wrocławskie ZOO","Ogród zoologiczny znajdujący się przy ul. Wróblewskiego 1–5 we Wrocławiu, otwarty 10 lipca 1865. Jest najstarszym na obecnych ziemiach polskich ogrodem zoologicznym w Polsce. Powierzchnia ogrodu to 33 hektary.",zdjecia[0]);
        myDataset.add(atrakcja);

        atrakcja=new Atrakcja("Hala stulecia","Hala widowiskowo-sportowa znajdująca się we Wrocławiu, na osiedlu Zalesie, w parku Szczytnickim. Wzniesiona w latach 1911–1913 według projektu Maxa Berga, w stylu ekspresjonistycznym.",zdjecia[1]);
        myDataset.add(atrakcja);

        atrakcja=new Atrakcja(" Ogród japoński","Znajduje się w Parku Szczytnickim w Śródmieściu Wrocławia.Ogród japoński został założony w latach 1909-1913 wokół dawnego stawu Ludwiga Theodora Moritza-Eichborna w obrębie obecnego Parku Szczytnickiego.",zdjecia[2]);
        myDataset.add(atrakcja);

        atrakcja=new Atrakcja("Rynek","Sredniowieczny plac targowy we Wrocławiu, obecnie centralna część strefy pieszej. Stanowi prostokąt o wymiarach 213 na 178 m, oraz powierzchni 3,8 ha. Jest to jeden z największych rynków staromiejskich Europy, z największymi ratuszami w Polsce. ",zdjecia[3]);
        myDataset.add(atrakcja);

        atrakcja=new Atrakcja("Wieża ciśnień","Historyzująca wieża ciśnień o wysokości całkowitej 62 m, zlokalizowana na osiedlu Borek we Wrocławiu. Oficjalny adres wieży to: ul. Sudecka 125a, jednak znajduje się ona na pasie rozdzielającym jezdnie al. Wiśniowej w ciągu obwodnicy śródmiejskiej. ",zdjecia[4]);
        myDataset.add(atrakcja);

        mAdapter.notifyDataSetChanged();
    }
}
