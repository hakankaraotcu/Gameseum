package hasanhakan.gameseum;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ThreadExampleFragment extends Fragment {
    TextView text1;
    TextView text3;
    ProgressBar pb1;
    ProgressBar pb3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_example, container, false);

        text1 = view.findViewById(R.id.text1);
        text3 = view.findViewById(R.id.text3);
        pb1 = view.findViewById(R.id.progressBar1);
        pb3 = view.findViewById(R.id.progressBar3);
        final Activity[] activity1 = {new Activity()};
        final Activity[] activity2 = {new Activity()};

        Drawable draw1= getResources().getDrawable(R.drawable.progress1);
        pb1.setProgressDrawable(draw1);

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                activity1[0] = getActivity();
                for (int j = 0; j <= 100; j++){
                    int finalJ = j;
                    activity1[0].runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text1.setText("%" + finalJ);
                            pb1.setProgress(finalJ);
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();


        Drawable draw3 = getResources().getDrawable(R.drawable.progress3);
        pb3.setProgressDrawable(draw3);

        Thread thread3 = new Thread(){
            @Override
            public void run() {
                activity2[0] = getActivity();
                for (int i = 0; i <= 100; i++){
                    int finalI = i;
                    activity2[0].runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text3.setText("%" + finalI);
                            pb3.setProgress(finalI);
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread3.start();
        return view;
    }
}