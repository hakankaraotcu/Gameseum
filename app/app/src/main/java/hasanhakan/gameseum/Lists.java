package hasanhakan.gameseum;

import java.util.ArrayList;

public class Lists {
    private String title;
    private int count;

    public Lists() {
    }

    public Lists(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    static public ArrayList<Lists> getData() {
        ArrayList<Lists> titleLists = new ArrayList<>();
        String[] titles = {"Played List", "Wishlist", "Reviews", "Game List", "Friends"};
        int[] count = {0, 0, 0, 0, 0};

        for (int i = 0; i < titles.length; i++) {
            Lists title = new Lists();
            title.setTitle(titles[i]);
            title.setCount(count[i]);

            titleLists.add(title);
        }

        return titleLists;
    }
}
