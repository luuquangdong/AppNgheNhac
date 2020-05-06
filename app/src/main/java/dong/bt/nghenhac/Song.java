package dong.bt.nghenhac;

public class Song {
    private int id;
    private String title;

    public Song(String tenBaiHat, int id) {
        this.id = id;
        this.title = tenBaiHat;
    }

    public int getId() {
        return id;
    }

    public String getTenBaiHat() {
        return title;
    }
}
