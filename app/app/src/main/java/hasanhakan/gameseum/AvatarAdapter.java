package hasanhakan.gameseum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikhaellopez.circularimageview.CircularImageView;

public class AvatarAdapter extends BaseAdapter {
    CircularImageView circularImageView;
    private int[] images;
    private Context context;

    public AvatarAdapter(int[] images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.avatars, null);
        circularImageView = view.findViewById(R.id.avatar_circularImage);
        circularImageView.setImageResource(images[position]);
        return view;
    }
}