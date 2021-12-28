/*package hasanhakan.gameseum;

import java.util.ArrayList;

public class Images {
    private int image;

    public Images() {
    }

    public Images(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    static public ArrayList<Images> getData(String tag) {
        ArrayList<Images> pictures = new ArrayList<>();
        int[] pictureListPopular = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
        int[] pictureListNewReleases = {R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image1, R.drawable.image5, R.drawable.image2, R.drawable.image3};
        int[] pictureListCreate = {R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4, R.drawable.image5, R.drawable.image3, R.drawable.image1, R.drawable.image2, R.drawable.image4};

        if (tag == "Popular") {
            for (int i = 0; i < 10; i++) {
                Images image = new Images();
                image.setImage(pictureListPopular[i]);
                pictures.add(image);
            }
        } else if (tag == "New Releases") {
            for (int i = 0; i < 10; i++) {
                Images image = new Images();
                image.setImage(pictureListNewReleases[i]);
                pictures.add(image);
            }
        } else if(tag == "Create"){
            for (int i = 0; i < 10; i++) {
                Images image = new Images();
                image.setImage(pictureListCreate[i]);
                pictures.add(image);
            }
        }
        return pictures;
    }
}*/
