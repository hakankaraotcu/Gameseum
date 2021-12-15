package hasanhakan.gameseum;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ProfileFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageButton settingsButton;
    private CircularImageView profile_image;
    private SwitchCompat switch_theme;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int image;
    private ListView listView;
    private ProfileListAdapter adapter;
    private String[] titles = {"Played List", "Wishlist", "Reviews", "Game List", "Friends"};
    private int[] count = {0, 0, 0, 0, 0};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        drawerLayout = view.findViewById(R.id.profile_drawerLayout);
        navigationView = view.findViewById(R.id.profile_navigationView);
        toolbar = getActivity().findViewById(R.id.toolbar);
        settingsButton = getActivity().findViewById(R.id.bar_layout_settingsButton);
        profile_image = view.findViewById(R.id.profile_circularImage);

        preferences = getActivity().getSharedPreferences("hasanhakan.gameseum", Context.MODE_PRIVATE);
        editor = preferences.edit();

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if (getArguments() != null) {
            image = getArguments().getInt("image");
            profile_image.setImageResource(image);
            getArguments().clear();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 1; i < toolbar.getChildCount() - 1; i++) {
            if (toolbar.getChildAt(i).getVisibility() == View.VISIBLE) {
                Log.d("count", String.valueOf(i));
                toolbar.getChildAt(i).setVisibility(View.GONE);
            }
        }
        toolbar.findViewById(R.id.profile_bar).setVisibility(View.VISIBLE);

        switch_theme = (SwitchCompat) navigationView.getMenu().findItem(R.id.settings_darkMode).getActionView();

        switch_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();
                } else if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                }
            }
        });

        listView = view.findViewById(R.id.profile_listView);
        adapter = new ProfileListAdapter(titles, count, getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(titles[i], "working");
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings_avatar:
                        AvatarFragment avatarFragment = new AvatarFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, avatarFragment).addToBackStack(null).commit();
                        break;
                    case R.id.settings_password:
                        Log.d("password", "2");
                        break;
                    case R.id.settings_darkMode:
                        switch_theme.setChecked(!switch_theme.isChecked());
                        if (switch_theme.isChecked()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            editor.putBoolean("isDarkModeOn", true);
                            editor.apply();
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            editor.putBoolean("isDarkModeOn", false);
                            editor.apply();
                        }
                        break;
                    case R.id.settings_logOut:
                        Log.d("logout", "4");
                        break;
                }
                return false;
            }
        });
    }
}