package zpi.lignarski.janusz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zpi.lyjak.anna.firstversion.R;


public class CreateTripCategoriesAdapter extends BaseAdapter {

    private Context mContext;
    private List<Category> cats;
    private LayoutInflater inflater;
    private ArrayList<CheckBox> boxes;

    public CreateTripCategoriesAdapter(Context c, List<Category> categories) {
        mContext = c;
        cats = categories;
        inflater = LayoutInflater.from(c);
        boxes = new ArrayList<>();
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
            ((TextView)convertView.findViewById(R.id.textView)).setText(cats.get(position).getName());
            boxes.add((CheckBox) convertView.findViewById(R.id.checkBox));
        }
        return convertView;
    }

    public ArrayList<Category> getCheckedCategories() {
        ArrayList<Category> checked = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isChecked())
                checked.add(cats.get(i));
        }
        return checked;
    }
}
