package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by Beom2 on 2017-09-24.
 */

public class Model_Weather1 {
    String baseDate;
    String baseTime;
    String category;
    String nx;
    String ny;
    String obsrValue;

    public Model_Weather1(String baseDate, String baseTime, String category, String nx, String ny, String obsrValue) {
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.category = category;
        this.nx = nx;
        this.ny = ny;
        this.obsrValue = obsrValue;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public String getCategory() {
        return category;
    }

    public String getNx() {
        return nx;
    }

    public String getNy() {
        return ny;
    }

    public String getObsrValue() {
        return obsrValue;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNx(String nx) {
        this.nx = nx;
    }

    public void setNy(String ny) {
        this.ny = ny;
    }

    public void setObsrValue(String obsrValue) {
        this.obsrValue = obsrValue;
    }
}
