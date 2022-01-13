package hasanhakan.gameseum;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopularGamesFragment extends Fragment {

    private Toolbar toolbar;
    private ImageButton backButton, filterButton;
    private TextView pageName;
    private GridView gridView;
    private GamesAdapter gamesAdapter;
    private HomeFragment homeFragment;
    private GameFragment gameFragment;
    private List<Game> games;

    public PopularGamesFragment(){

    }

    public PopularGamesFragment(List<Game> games) {
        this.games = games;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        homeFragment = new HomeFragment();
        gameFragment = new GameFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_games, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        backButton = getActivity().findViewById(R.id.bar_layout_popularGamesAndNewReleasedGames_backButton);
        filterButton = getActivity().findViewById(R.id.bar_layout_popularGamesAndNewReleasedGames_filterButton);
        pageName = getActivity().findViewById(R.id.popularNewReleased_pageName);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 1; i < toolbar.getChildCount() - 1; i++) {
            if (toolbar.getChildAt(i).getVisibility() == View.VISIBLE) {
                Log.d("page number", String.valueOf(i));
                toolbar.getChildAt(i).setVisibility(View.GONE);
            }
        }
        toolbar.findViewById(R.id.popularGamesAndNewReleasedGames_bar).setVisibility(View.VISIBLE);
        pageName.setText("Popular Games");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, homeFragment).commit();
            }
        });

        gridView = view.findViewById(R.id.popularGames_gridView);
        gamesAdapter = new GamesAdapter(games, getContext());
        gridView.setAdapter(gamesAdapter);
        gridView.setOverScrollMode(GridView.OVER_SCROLL_NEVER);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle args = new Bundle();
                args.putString("previousPage", "games");
                args.putString("name", games.get(i).getName());
                args.putString("image", games.get(i).getUrl());
                args.putString("dev", games.get(i).getDev());
                args.putString("genre", games.get(i).getGenre());
                args.putLong("metacritic", games.get(i).getMetacritic());
                gameFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, gameFragment).commit();
            }
        });
    }
}