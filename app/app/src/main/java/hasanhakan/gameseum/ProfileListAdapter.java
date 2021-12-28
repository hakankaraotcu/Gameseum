package hasanhakan.gameseum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileListAdapter extends ArrayAdapter<String> {
    private String[] titlesList;
    private int[] countList;
    private Context context;
    private TextView title, count;

    public ProfileListAdapter(String[] titlesList, int[] countList, Context context) {
        super(context, R.layout.titles, titlesList);
        this.titlesList = titlesList;
        this.countList = countList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.titles, null);

        if (view != null) {
            title = view.findViewById(R.id.profile_listTitle);
            count = view.findViewById(R.id.profile_listTitleCount);

            title.setText(titlesList[position]);
            count.setText(String.valueOf(countList[position]));
        }
        return view;
    }
}
