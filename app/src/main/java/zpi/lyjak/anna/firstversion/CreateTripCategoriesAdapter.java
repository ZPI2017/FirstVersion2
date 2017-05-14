package zpi.lyjak.anna.firstversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class CreateTripCategoriesAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> cats;
    private LayoutInflater inflater;

    public CreateTripCategoriesAdapter(Context c, List<String> categories) {
        mContext = c;
        cats = categories;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return cats.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_create_trip_category, parent, false);
            ((TextView)convertView.findViewById(R.id.textView)).setText(cats.get(position));
        }
        return convertView;
    }
}
