package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by Beom2 on 2017-10-07.
 */

public class Model_LikeCount {

    String url;
    String id;

    public Model_LikeCount() {
    }

    public Model_LikeCount(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

}
