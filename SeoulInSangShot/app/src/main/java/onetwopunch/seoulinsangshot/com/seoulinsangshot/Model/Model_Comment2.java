package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by 301 on 2017-10-21.
 */

public class Model_Comment2 {
    String id;
    String date;
    String url;
    String text;

    public Model_Comment2(String id, String url, String text, String date) {
        this.id = id;
        this.date = date;
        this.url = url;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
