package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by Beom2 on 2017-09-24.
 */

public class Model_Weather2 {

    String fcstDate;
    String fcstTime;
    String category;
    String fcstValue;

    public Model_Weather2(String fcstDate, String fcstTime, String category, String fcstValue) {
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.category = category;
        this.fcstValue = fcstValue;
    }

    public String getFcstDate() {
        return fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public String getCategory() {
        return category;
    }

    public String getFcstValue() {
        return fcstValue;
    }
}