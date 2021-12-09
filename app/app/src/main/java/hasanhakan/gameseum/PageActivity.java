package hasanhakan.gameseum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayDeque;
import java.util.Deque;

public class PageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Deque<Integer> integerDeque = new ArrayDeque<>(5);
    boolean flag = true;
    private Toolbar toolbar;
    private TextView page_name;
    private ImageButton backButton;

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        page_name = findViewById(R.id.bar_layout_pagename);
        backButton = findViewById(R.id.bar_layout_backButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        init();
        setSupportActionBar(toolbar);
        integerDeque.push(R.id.nav_home);
        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(integerDeque.contains(id)){
                    if(id == R.id.nav_home){
                        if(integerDeque.size() != 1){
                            if(flag){
                                integerDeque.addFirst(R.id.nav_home);
                                flag = false;
                            }
                        }
                    }
                    integerDeque.remove(id);
                }
                integerDeque.push(id);
                loadFragment(getFragment(item.getItemId()));
                return true;
            }
        });
    }

    private Fragment getFragment(int itemId) {
        switch(itemId){
            case R.id.nav_home:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                toolbar.setTitle("GAMESEUM");
                page_name.setText("");
                backButton.setVisibility(View.INVISIBLE);
                return new HomeFragment();
            case R.id.nav_search:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                toolbar.setTitle("");
                page_name.setText("SEARCH");
                backButton.setVisibility(View.INVISIBLE);
                return new SearchFragment();
            case R.id.nav_games:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                toolbar.setTitle("");
                page_name.setText("GAMES");
                backButton.setVisibility(View.INVISIBLE);
                return new GamesFragment();
            case R.id.nav_reviews:
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                toolbar.setTitle("");
                page_name.setText("REVIEWS");
                backButton.setVisibility(View.INVISIBLE);
                return new ReviewsFragment();
            case R.id.nav_profile:
                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                return new ProfileFragment();
        }
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment (Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    public void onBackPressed() {
        integerDeque.pop();
        if(!integerDeque.isEmpty()){
            loadFragment(getFragment(integerDeque.peek()));
        }else{
            finish();
        }
    }
}