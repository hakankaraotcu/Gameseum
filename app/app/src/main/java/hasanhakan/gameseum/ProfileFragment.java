package hasanhakan.gameseum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class ProfileFragment extends Fragment {

    private Toolbar toolbar;
    private TextView page_name;
    private ImageButton backButton;
    private CircularImageView profile_image;
    int image;
    private ListView listView;
    private ProfileListAdapter adapter;
    private String[] titles = {"Played List", "Wishlist", "Reviews", "Game List", "Friends"};
    private int[] count = {0, 0, 0, 0, 0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        page_name = getActivity().findViewById(R.id.bar_layout_pagename);
        backButton = getActivity().findViewById(R.id.bar_layout_backButton);
        profile_image = view.findViewById(R.id.profile_circularImage);

        if(getArguments() != null){
            image = getArguments().getInt("image");
            profile_image.setImageResource(image);
            getArguments().clear();
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.profile_listView);
        adapter = new ProfileListAdapter(titles, count, getContext());
        listView.setAdapter(adapter);

        toolbar.setTitle("");
        page_name.setText("USERNAME");
        backButton.setVisibility(View.INVISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(titles[i], "çalışıyor");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_avatar:
                Log.d("avatars", "1");
                AvatarFragment avatarFragment = new AvatarFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout,avatarFragment).addToBackStack(null).commit();
                break;
            case R.id.settings_password:
                Log.d("password", "2");
                break;
            case R.id.settings_darkMode:
                Log.d("darkmode", "3");
                break;
            case R.id.settings_logOut:
                Log.d("logout", "4");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}