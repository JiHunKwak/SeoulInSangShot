package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by 301 on 2017-09-28.
 */

public class Model_Comment {
    String init;
    String id;
    String text;
    String date;

    public Model_Comment() {

    }

    public Model_Comment(String init, String id, String text,String date) {
        this.init = init;
        this.id = id;
        this.text = text;
        this.date=date;
    }

    public String getInit() {
        return init;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
