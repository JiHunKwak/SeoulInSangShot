package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

import java.io.Serializable;

/**
 * Created by kwakgee on 2017. 9. 30..
 */

public class Model_Test implements Serializable{

    private int number;
    private String area;
    private String initials;
    private String name;
    private String ename;
    private String lat;
    private String lng;
    private String subway;
    private String bus;
    private String bicycle;
    private String url;
    private String smartPhone;
    private String filter;
    private String theme1;
    private String theme2;
    private String time;
    private String tip;
    private String info;
    private int likeimage;
    private int viewimage;
    private String hit;

    public Model_Test(int number, String area, String initials, String name, String ename, String lat, String lng, String subway, String bus, String bicycle, String url, String smartPhone, String filter, String theme1, String theme2, String time, String tip, String info, int likeimage, int viewimage,String hit) {
        this.number = number;
        this.area = area;
        this.initials = initials;
        this.name = name;
        this.ename = ename;
        this.lat = lat;
        this.lng = lng;
        this.subway = subway;
        this.bus = bus;
        this.bicycle = bicycle;
        this.url = url;
        this.smartPhone = smartPhone;
        this.filter = filter;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.time = time;
        this.tip = tip;
        this.info = info;
        this.likeimage = likeimage;
        this.viewimage = viewimage;
        this.hit=hit;
    }

    public int getNumber() {
        return number;
    }

    public String getArea() {
        return area;
    }

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public String getEname() {
        return ename;
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
        return smartPhone;
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

    public String getTime() {
        return time;
    }

    public String getTip() {
        return tip;
    }

    public String getInfo() { return info;}

    public int getLikeimage() {
        return likeimage;
    }

    public void setLikeimage(int likeimage) {
        this.likeimage = likeimage;
    }

    public int getViewimage() {
        return viewimage;
    }

    public void setViewimage(int viewimage) {
        this.viewimage = viewimage;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }
}