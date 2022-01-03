package hasanhakan.gameseum;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

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

public class Game {
    //add other attributes
    private String name, url, dev, genre;
    private Long metacritic;

    public Game() {

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(Long metacritic) {
        this.metacritic = metacritic;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*static public ArrayList<Game> getData(String tag) {

        ArrayList<Game> games = new ArrayList<>();
        int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5,
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5,
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5,
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5,
                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
        int[] pictureListPopular = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
        int[] pictureListNewReleases = {R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3};
        int[] pictureListCreate = {R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4};


        if (tag == "Popular") {
            for (int i = 0; i < 10; i++) {
                Game game = new Game();
                game.setImage(pictureListPopular[i]);
                games.add(game);
            }
        } else if (tag == "New Releases") {
            for (int i = 0; i < 10; i++) {
                Game game = new Game();
                game.setImage(pictureListNewReleases[i]);
                games.add(game);
            }
        } else if (tag == "Create") {
            for (int i = 0; i < 10; i++) {
                Game game = new Game();
                game.setImage(pictureListCreate[i]);
                games.add(game);
            }
        } else {
            for (int i = 0; i < images.length; i++) {
                Game game = new Game();
                game.setImage(images[i]);
                games.add(game);
            }
        }
        return games;
    }*/
}
