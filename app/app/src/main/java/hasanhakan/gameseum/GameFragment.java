package hasanhakan.gameseum;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GameFragment extends Fragment {

    private Toolbar toolbar;
    private ImageButton backButton, playedButton, wishlistButton;
    private ImageView gameImage;
    private TextView playedTxt, gameNameTxt, gameDevTxt, gameGenreTxt, gameMetacriticTxt;
    private Boolean wishlistCheck = false;
    private String name, image, dev, genre;
    private Long metacritic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        backButton = getActivity().findViewById(R.id.bar_layout_game_backButton);
        playedButton = view.findViewById(R.id.played_button);
        wishlistButton = view.findViewById(R.id.wishList_button);
        playedTxt = view.findViewById(R.id.played_description);
        gameImage = view.findViewById(R.id.gamePage_image);
        gameNameTxt = view.findViewById(R.id.gameName);
        gameDevTxt = view.findViewById(R.id.developer_description);
        gameGenreTxt = view.findViewById(R.id.genres_description);
        gameMetacriticTxt = view.findViewById(R.id.metacritic_description);

        image = getArguments().getString("image");
        name = getArguments().getString("name");
        dev = getArguments().getString("dev");
        genre = getArguments().getString("genre");
        metacritic = getArguments().getLong("metacritic");
        Glide.with(view.getContext()).load(image).into(gameImage);
        gameNameTxt.setText(name);
        gameDevTxt.setText(dev);
        gameGenreTxt.setText(genre);
        gameMetacriticTxt.setText(metacritic.toString());

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
        toolbar.findViewById(R.id.game_bar).setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = getArguments().getString("previousPage");
                switch (page) {
                    case "wishlist":
                        WishListFragment wishListFragment = new WishListFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, wishListFragment).commit();
                        break;
                    case "playedList":
                        PlayedListFragment playedListFragment = new PlayedListFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, playedListFragment).commit();
                        break;
                    case "games":
                        GamesFragment gamesFragment = new GamesFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, gamesFragment).commit();
                        break;
                }
            }
        });

        playedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playedTxt.getText().toString().equals("Play")) {
                    playedButton.setImageTintList(ColorStateList.valueOf(Color.BLUE));
                    playedTxt.setText("Played");
                    //add to playedList
                } else if (playedTxt.getText().toString().equals("Played")) {
                    playedButton.setImageTintList(ColorStateList.valueOf(Color.RED));
                    playedTxt.setText("Play");
                    //remove from playedList
                }
            }
        });

        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!wishlistCheck) {
                    wishlistButton.setImageResource(R.drawable.ic_removewishlist);
                    wishlistButton.setImageTintList(ColorStateList.valueOf(Color.BLUE));
                    wishlistCheck = !wishlistCheck;
                    //add to wishlist
                } else {
                    wishlistButton.setImageResource(R.drawable.ic_addwishlist);
                    wishlistButton.setImageTintList(ColorStateList.valueOf(Color.RED));
                    wishlistCheck = !wishlistCheck;
                    //remove from wishlist
                }
            }
        });
    }
}