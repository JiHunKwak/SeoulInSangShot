package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MG_PARK on 2017-09-17.
 */

public class SubjectVO {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("themename")
    @Expose
    private String themename;
    @SerializedName("ethemename")
    @Expose
    private String ethemename;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getThemename() {
        return themename;
    }

    public void setThemename(String themename) {
        this.themename = themename;
    }

    public String getEthemename() {
        return ethemename;
    }

    public void setEthemename(String ethemename) {
        this.ethemename = ethemename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
