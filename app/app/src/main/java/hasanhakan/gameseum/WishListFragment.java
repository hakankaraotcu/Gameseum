package hasanhakan.gameseum;

import android.net.Uri;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class WishListFragment extends Fragment {

    private Toolbar toolbar;
    private GridView gridView;
    private GamesAdapter gamesAdapter;
    private ImageButton backButton, searchButton, sliderButton;
    private TextView pageName;
    private SliderFragment sliderFragment;
    private ProfileFragment profileFragment;
    private GameFragment gameFragment;
    private StorageReference listRef;
    private ArrayList<Game> games = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        profileFragment = new ProfileFragment();
        gameFragment = new GameFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        sliderFragment = new SliderFragment(this);
        toolbar = getActivity().findViewById(R.id.toolbar);
        backButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_backButton);
        searchButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_searchButton);
        sliderButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_sliderButton);
        pageName = getActivity().findViewById(R.id.playedWishlist_pageName);
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
        toolbar.findViewById(R.id.playedListAndWishList_bar).setVisibility(View.VISIBLE);
        pageName.setText("Wishlist");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, profileFragment).commit();
            }
        });

        sliderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sliderFragment.show(getParentFragmentManager(), "WishlistSlider");
            }
        });

        gridView = view.findViewById(R.id.wishList_gridView);
        gamesAdapter = new GamesAdapter(games, getContext());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle args = new Bundle();
                args.putString("previousPage", "wishlist");
                args.putString("name", games.get(i).getName());
                args.putString("image", games.get(i).getUrl());
                args.putString("dev", games.get(i).getDev());
                args.putString("genre", games.get(i).getGenre());
                args.putLong("metacritic", games.get(i).getMetacritic());
                gameFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, gameFragment).commit();
            }
        });

        listRef = FirebaseStorage.getInstance().getReference();
        checkName();
    }

    public void checkName() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ref = firestore.collection("new_released_games");
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String formattedName = ((String) document.getData().get("name")).replaceAll(" ", "-").replaceAll(":", "").replaceAll("'", "").toLowerCase();
                    String name = (String) document.getData().get("name");
                    String dev = (String) document.getData().get("dev");
                    String genre = (String) document.getData().get("genre");
                    Long metacritic = (Long) document.getData().get("point");
                    Game game = new Game();
                    game.setName(name);
                    game.setDev(dev);
                    game.setGenre(genre);
                    game.setMetacritic(metacritic);
                    games.add(game);
                    download(game, formattedName);
                }
            }
        });
    }

    public void download(Game game, String gameName) {
        StorageReference imgReference = listRef.child("new_released_games/" + gameName + " .jpg");
        imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                game.setUrl(uri.toString());
                gridView.setAdapter(gamesAdapter);
                gridView.setOverScrollMode(GridView.OVER_SCROLL_NEVER);
            }
        });
    }

    public ArrayList<Game> getGames(){
        return games;
    }
}