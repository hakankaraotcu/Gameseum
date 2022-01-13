package hasanhakan.gameseum;

import android.app.Dialog;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SliderFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior behavior;
    private LinearLayout linearLayout;
    private TextView gameNameTxt;
    private ImageButton closeBtn;
    private SliderAdapter sliderAdapter;
    private ViewPager2 viewPager2;
    private WishListFragment wishListFragment;
    private PlayedListFragment playedListFragment;

    public SliderFragment(WishListFragment wishListFragment){
        this.wishListFragment = wishListFragment;
    }

    public SliderFragment(PlayedListFragment playedListFragment){
        this.playedListFragment = playedListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheet);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.fragment_slider, null);
        linearLayout = view.findViewById(R.id.slider_main);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        linearLayout.setLayoutParams(params);

        bottomSheetDialog.setContentView(view);
        behavior = BottomSheetBehavior.from((View) view.getParent());

        return bottomSheetDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);

        gameNameTxt = view.findViewById(R.id.slider_gameName);

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        if(getTag() == "WishlistSlider"){
            sliderAdapter = new SliderAdapter(wishListFragment.getGames(), getContext());
        }else if(getTag() == "PlayedListSlider"){
            sliderAdapter = new SliderAdapter(playedListFragment.getGames(), getContext());
        }

        viewPager2.setAdapter(sliderAdapter);

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(50));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if(getTag() == "WishlistSlider"){
                    gameNameTxt.setText(wishListFragment.getGames().get(viewPager2.getCurrentItem()).getName());
                }else if(getTag() == "PlayedListSlider"){
                    gameNameTxt.setText(playedListFragment.getGames().get(viewPager2.getCurrentItem()).getName());

                }
                Log.d("position", String.valueOf(position));
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeBtn = view.findViewById(R.id.slider_close);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}