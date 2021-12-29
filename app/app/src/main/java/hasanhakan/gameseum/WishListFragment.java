package hasanhakan.gameseum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class WishListFragment extends Fragment {

    private Toolbar toolbar;
    private GridView gridView;
    private GamesAdapter gamesAdapter;
    private ImageButton backButton, searchButton, sliderButton;
    private TextView pageName;
    private SliderFragment sliderFragment;
    private ProfileFragment profileFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        profileFragment = new ProfileFragment();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        sliderFragment = new SliderFragment();
        toolbar = getActivity().findViewById(R.id.toolbar);
        backButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_backButton);
        searchButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_searchButton);
        sliderButton = getActivity().findViewById(R.id.bar_layout_playedListAndWishList_sliderButton);
        pageName = getActivity().findViewById(R.id.pageName);
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
                sliderFragment.show(getParentFragmentManager(), "slider");
            }
        });

        gridView = view.findViewById(R.id.wishList_gridView);
        gamesAdapter = new GamesAdapter(Game.getData(""), getContext());
        gridView.setAdapter(gamesAdapter);
        gridView.setOverScrollMode(GridView.OVER_SCROLL_NEVER);
    }
}