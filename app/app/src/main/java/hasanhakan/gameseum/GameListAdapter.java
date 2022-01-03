package hasanhakan.gameseum;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private ArrayList<GameLists> listNames;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private ImageAdapter imageAdapter;
    private StorageReference listRef;
    private ArrayList<Game> games = new ArrayList<>();

    public GameListAdapter(ArrayList<GameLists> listNames, Context context) {
        this.listNames = listNames;
        this.context = context;
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gamelist_layout, parent, false);
        return new GameListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        GameLists gameList = listNames.get(position);
        holder.setData(gameList);
        /*imageAdapter = new ImageAdapter(games, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(imageAdapter);
        holder.recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);*/
        listRef = FirebaseStorage.getInstance().getReference();
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        checkName(holder);
    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }

    class GameListViewHolder extends RecyclerView.ViewHolder {
        TextView listName;
        RecyclerView recyclerView;

        public GameListViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.gameListName);
            recyclerView = itemView.findViewById(R.id.recyclerView_gameList);
        }

        public void setData(GameLists gameList) {
            this.listName.setText(gameList.getListName());
        }
    }

    public void checkName(@NonNull GameListViewHolder holder) {
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
                    download(holder, game, formattedName);
                }
            }
        });
    }

    public void download(@NonNull GameListViewHolder holder, Game game, String gameName) {
        StorageReference imgReference = listRef.child("new_released_games/ " + gameName + " .jpg");
        imgReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                game.setUrl(uri.toString());
                imageAdapter = new ImageAdapter(games.subList(0, 10), context);
                holder.recyclerView.setAdapter(imageAdapter);
                holder.recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            }
        });
    }
}
