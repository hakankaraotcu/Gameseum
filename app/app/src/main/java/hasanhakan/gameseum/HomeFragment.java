package hasanhakan.gameseum;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

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
import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerViewPopular, recyclerViewNewReleases;
    private ImageAdapter imageAdapter;
    private Toolbar toolbar;
    private StorageReference listRef;
    private List<Game> popularGames = new ArrayList<>();
    private List<Game> newReleasedGames = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
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

        listRef = FirebaseStorage.getInstance().getReference();

        recyclerViewPopular = view.findViewById(R.id.recyclerView_popularGames);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(linearLayoutManager);
        checkName("popular_games");

        recyclerViewNewReleases = view.findViewById(R.id.recyclerView_newReleasesGames);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewReleases.setLayoutManager(linearLayoutManager);
        checkName("new_released_games");
    }

    public void checkName(String tag) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ref = firestore.collection(tag);
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
                    if(tag.equals("popular_games")) {
                        popularGames.add(game);
                    }
                    else {
                        newReleasedGames.add(game);
                    }
                    download(tag, game, formattedName);
                    }
            }
        });
    }

    public void download(String tag, Game game, String gameName) {
        if (tag == "popular_games") {
            tag = "best_games";
            StorageReference imgReference = listRef.child(tag + "/"+ gameName + " .jpg");
            imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    game.setUrl(uri.toString());
                    imageAdapter = new ImageAdapter(popularGames.subList(0, 10), getContext());
                    recyclerViewPopular.setAdapter(imageAdapter);
                    recyclerViewPopular.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            PopularGamesFragment popularGamesFragment = new PopularGamesFragment(popularGames);
                            getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, popularGamesFragment).commit();
                        }
                    });
                }
            });
        } else {
            StorageReference imgReference = listRef.child(tag + "/" + gameName + " .jpg");
            imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    game.setUrl(uri.toString());
                    imageAdapter = new ImageAdapter(newReleasedGames.subList(0, 10), getContext());
                    recyclerViewNewReleases.setAdapter(imageAdapter);
                    recyclerViewNewReleases.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            NewReleasedGamesFragment newReleasedGamesFragment = new NewReleasedGamesFragment(newReleasedGames);
                            getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, newReleasedGamesFragment).commit();
                        }
                    });
                }
            });
        }
    }
}