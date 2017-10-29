package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by Beom2 on 2017-10-22.
 */

public class Model_Best2 {
    String url;
    String likes;
    String area;

    public String getUrl() {
        return url;
    }

    public String getLikes() {
        return likes;
    }

    public String getArea() {
        return area;
    }

    public Model_Best2(String url, String likes) {

        this.url = url;
        this.likes = likes;
    }
}
