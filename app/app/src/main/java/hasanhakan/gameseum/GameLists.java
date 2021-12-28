package hasanhakan.gameseum;

import java.util.ArrayList;

public class GameLists {
    private String listName;

    public GameLists() {
    }

    public GameLists(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    static public ArrayList<GameLists> getData() {
        ArrayList<GameLists> titleLists = new ArrayList<>();
        String[] titles = {"Horror", "Action", "Adventure", "Sport", "RPG", "Pixel", "JRPG", "Strategy", "Simulation"};

        for (int i = 0; i < titles.length; i++) {
            GameLists gamelist = new GameLists();
            gamelist.setListName(titles[i]);

            titleLists.add(gamelist);
        }

        return titleLists;
    }
}
