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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GameFragment extends Fragment {

    private Toolbar toolbar;
    private ImageButton backButton, playedButton, wishlistButton;
    private ImageView gameImage;
    private TextView playedTxt, gameNameTxt, gameDevTxt, gameGenreTxt, gameMetacriticTxt;
    private Boolean wishlistCheck = false;
    private String name, image, dev, genre;
    private Long metacritic;
    private Map<String, Object> game = new HashMap<>();

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

        game.put("name", name);
        game.put("dev", dev);
        game.put("genre", genre);
        game.put("point", metacritic);

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
                    case "search":
                        SearchFragment searchFragment = new SearchFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, searchFragment).commit();
                        break;
                    case "popular games":
                        PopularGamesFragment popularGamesFragment = new PopularGamesFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, popularGamesFragment).commit();
                        break;
                    case "new released games":
                        NewReleasedGamesFragment newReleasedGamesFragment  = new NewReleasedGamesFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, newReleasedGamesFragment).commit();
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
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            FirebaseFirestore.getInstance().collection(userProfile.username)
                                    .document("playedList").collection(name).document(name).set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Game Added to Played List", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to add to Played List", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (playedTxt.getText().toString().equals("Played")) {
                    playedButton.setImageTintList(ColorStateList.valueOf(Color.RED));
                    playedTxt.setText("Play");
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            FirebaseFirestore.getInstance().collection(userProfile.username).document("playedList")
                                    .collection(name).document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Removed game from Played List", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            FirebaseFirestore.getInstance().collection(userProfile.username)
                                    .document("wishList").collection(name).document(name).set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Game Added to WishList", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to add to WishList", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    wishlistButton.setImageResource(R.drawable.ic_addwishlist);
                    wishlistButton.setImageTintList(ColorStateList.valueOf(Color.RED));
                    wishlistCheck = !wishlistCheck;
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userProfile = snapshot.getValue(User.class);
                            FirebaseFirestore.getInstance().collection(userProfile.username).document("wishList")
                                    .collection(name).document(name).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Removed game from Wish List", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}