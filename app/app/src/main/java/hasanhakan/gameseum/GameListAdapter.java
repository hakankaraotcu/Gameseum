package hasanhakan.gameseum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {

    private ArrayList<GameLists> listNames;
    private Context context;

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
        ImageAdapter imageAdapter = new ImageAdapter(Game.getData("Create"), context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(imageAdapter);
        holder.recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
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
}
