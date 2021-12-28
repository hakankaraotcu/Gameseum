package hasanhakan.gameseum;

import java.util.ArrayList;

public class Game {
    //add other attributes
    private int image;

    public Game(){

    }

    public Game(int image){
        this.image = image;
    }

    public int getImage(){
        return image;
    }

    public void setImage(int image){
        this.image = image;
    }

    static public ArrayList<Game> getData(String tag){
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
        } else if(tag == "Create"){
            for (int i = 0; i < 10; i++) {
                Game game = new Game();
                game.setImage(pictureListCreate[i]);
                games.add(game);
            }
        }else {
            for(int i = 0; i < images.length;i++){
                Game game = new Game();
                game.setImage(images[i]);
                games.add(game);
            }
        }
        return games;
    }
}
