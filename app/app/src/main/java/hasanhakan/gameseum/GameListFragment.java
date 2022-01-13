package hasanhakan.gameseum;

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
import android.widget.ImageButton;

import java.util.ArrayList;

public class GameListFragment extends Fragment {

    private GameListAdapter gameListAdapter;
    private RecyclerView recyclerView;
    private CreateListFragment createListFragment;
    private Toolbar toolbar;
    private ImageButton backButton, createButton;

    private ProfileFragment profileFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        profileFragment = new ProfileFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);
        createListFragment = new CreateListFragment();
        toolbar = getActivity().findViewById(R.id.toolbar);
        backButton = getActivity().findViewById(R.id.bar_layout_gameList_backButton);
        createButton = getActivity().findViewById(R.id.bar_layout_gameList_createButton);
        recyclerView = view.findViewById(R.id.recyclerView_allGameLists);

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
        toolbar.findViewById(R.id.gameList_bar).setVisibility(View.VISIBLE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, profileFragment).commit();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createListFragment.show(getParentFragmentManager(), "create");
            }
        });

        gameListAdapter = new GameListAdapter(GameLists.getData(), getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(gameListAdapter);
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        gameListAdapter.setOnItemClickListener(new GameListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<Game> games, GameLists listName) {
                SelectedGameListFragment selectedGameListFragment = new SelectedGameListFragment(games, listName.getListName());
                getParentFragmentManager().beginTransaction().replace(R.id.page_activity_frameLayout, selectedGameListFragment).commit();
            }
        });

    }
}