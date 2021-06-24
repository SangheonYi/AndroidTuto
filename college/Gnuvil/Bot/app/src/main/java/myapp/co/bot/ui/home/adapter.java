package myapp.co.bot.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import myapp.co.bot.R;

class adapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<FoodMenu> values;

    public adapter(Context context, ArrayList<FoodMenu> values) {
        super(context, 0, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {

        return values.size();
    }



    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        TextView textView = (TextView) itemView.findViewById(R.id.label);
        TextView rkrur = (TextView) itemView.findViewById(R.id.label1);
        TextView wksdu = (TextView) itemView.findViewById(R.id.label2);

        FoodMenu foodMenu = (FoodMenu) getItem(position);
        textView.setText(foodMenu.getName());
        rkrur.setText(foodMenu.getPrice());
        wksdu.setText(foodMenu.getSideDish());

        return itemView;
    }
}
