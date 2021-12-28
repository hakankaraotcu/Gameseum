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

public class HomeFragment extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerViewPopular, recyclerViewNewReleases;
    private ImageAdapter imageAdapter;
    private Toolbar toolbar;

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

        recyclerViewPopular = view.findViewById(R.id.recyclerView_popularGames);
        imageAdapter = new ImageAdapter(Game.getData("Popular"), getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(linearLayoutManager);
        recyclerViewPopular.setAdapter(imageAdapter);
        recyclerViewPopular.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        recyclerViewNewReleases = view.findViewById(R.id.recyclerView_newReleasesGames);
        imageAdapter = new ImageAdapter(Game.getData("New Releases"), getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewNewReleases.setLayoutManager(linearLayoutManager);
        recyclerViewNewReleases.setAdapter(imageAdapter);
        recyclerViewNewReleases.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }
}