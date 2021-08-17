package sg.edu.rp.c346.id20033454.chores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Chores> choreList;

    public CustomAdapter(Context context, int resource,
                         ArrayList<Chores> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        choreList = objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView=inflater.inflate(layout_id, parent, false);

        TextView tvName = rowView.findViewById(R.id.tvTitle);
        TextView tvDetails = rowView.findViewById(R.id.tvDetails);
        TextView tvDay = rowView.findViewById(R.id.tvDay);
        TextView tvTime = rowView.findViewById(R.id.tvTime);
        RatingBar rbImpt = rowView.findViewById(R.id.ratingBar);

        Chores currentChore = choreList.get(position);

        tvName.setText(currentChore.getName());
        tvDetails.setText(currentChore.getDetails());
        tvDay.setText(currentChore.getDay());
        tvTime.setText(currentChore.getTime());
        rbImpt.setRating(currentChore.getImportance());

        return rowView;

    }
}
