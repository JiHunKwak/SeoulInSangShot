package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by 301 on 2017-10-20.
 */

public class Model_Main {

    private String area;
    private String init;
    private String name;
    private String lat;
    private String lng;
    private String subway;
    private String bus;
    private String bicycle;
    private String url;
    private String smartphone;
    private String filter;
    private String theme1;
    private String theme2;
    private String info;
    private String tip;
    private String ename;
    private String hit;

    public Model_Main(String area, String init, String name, String lat, String lng, String subway, String bus, String bicycle, String url, String smartphone, String filter, String theme1, String theme2, String info, String tip, String ename, String hit) {
        this.area = area;
        this.init = init;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.subway = subway;
        this.bus = bus;
        this.bicycle = bicycle;
        this.url = url;
        this.smartphone = smartphone;
        this.filter = filter;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.info = info;
        this.tip = tip;
        this.ename = ename;
        this.hit = hit;
    }

    public String getArea() {
        return area;
    }

    public String getInit() {
        return init;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getSubway() {
        return subway;
    }

    public String getBus() {
        return bus;
    }

    public String getBicycle() {
        return bicycle;
    }

    public String getUrl() {
        return url;
    }

    public String getSmartPhone() {
        return smartphone;
    }

    public String getFilter() {
        return filter;
    }

    public String getTheme1() {
        return theme1;
    }

    public String getTheme2() {
        return theme2;
    }

    public String getSmartphone() {
        return smartphone;
    }

    public String getInfo() {
        return info;
    }

    public String getTip() {
        return tip;
    }

    public String getEname() {
        return ename;
    }

    public String getHit() {
        return hit;
    }
}
