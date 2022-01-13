package hasanhakan.gameseum;

public class Game {
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

}
