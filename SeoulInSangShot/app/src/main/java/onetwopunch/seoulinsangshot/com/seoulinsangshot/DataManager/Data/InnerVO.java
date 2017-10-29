package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kwakgee on 2017. 9. 23..
 */

public class InnerVO {

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("init")
    @Expose
    private String initials;

    @SerializedName("ename")
    @Expose
    private String ename;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("subway")
    @Expose
    private String subway;

    @SerializedName("bus")
    @Expose
    private String bus;

    @SerializedName("bicycle")
    @Expose
    private String bicycle;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("smartphone")
    @Expose
    private String smartPhone;

    @SerializedName("filter")
    @Expose
    private String filter;

    @SerializedName("theme1")
    @Expose
    private String theme1;

    @SerializedName("theme2")
    @Expose
    private String theme2;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("tip")
    @Expose
    private String tip;

    @SerializedName("info")
    @Expose
    private String info;

    @SerializedName("likeimage")
    @Expose
    private int likeiamge;


    @SerializedName("viewimage")
    @Expose
    private int viewimage;

    @SerializedName("hit")
    @Expose
    private String hit;


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

    public String getInfo() {return info;}

    public int getLikeiamge() {
        return likeiamge;
    }

    public void setLikeiamge(int likeiamge) {
        this.likeiamge = likeiamge;
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