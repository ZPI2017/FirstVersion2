package zpi.szymala.kasia.firstversion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zpi.lyjak.anna.firstversion.R;

/**
 * Created by Katarzyna on 2017-05-21.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Atrakcja> mDataset;
    private Context context;

    public MyAdapter(Context contexts, List<Atrakcja> myDataset) {
        context=contexts;
        mDataset=myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Atrakcja atrakcja=mDataset.get(position);
        holder.nazwa.setText(atrakcja.getNazwa());
        holder.szczegoly.setText(atrakcja.getSzczegoly());
        holder.imageView.setImageResource(atrakcja.getZdjecie());

        //Glide.with(context).load(atrakcja.getZdjecie()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nazwa, szczegoly;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            nazwa=(TextView)v.findViewById(R.id.textView2);
            szczegoly=(TextView)v.findViewById(R.id.textView);
            imageView=(ImageView)v.findViewById(R.id.imageView);
        }
    }
}
