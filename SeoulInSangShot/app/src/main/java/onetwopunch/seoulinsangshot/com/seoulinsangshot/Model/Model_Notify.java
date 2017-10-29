package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by 301-29 on 2017-10-22.
 */

public class Model_Notify {
    String topic;
    String text;
    String date;
    public Model_Notify()
    {

    }
    public Model_Notify(String topic, String text, String date){
        this.topic=topic;
        this.text=text;
        this.date=date;
    }
    public String getTopic(){
        return  topic;
    }
    public String getText(){
        return  text;
    }
    public String getDate(){
        return date;
    }
}
