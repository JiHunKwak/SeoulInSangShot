package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MG_PARK on 2017-09-30.
 */

public class DistrictVO {

    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("karea")
    @Expose
    private String karea;
    @SerializedName("earea")
    @Expose
    private String earea;
    @SerializedName("imagecount")
    @Expose
    private Integer imagecount;
    @SerializedName("hit")
    @Expose
    private Integer hit;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKarea() {
        return karea;
    }

    public void setKarea(String karea) {
        this.karea = karea;
    }

    public String getEarea() {
        return earea;
    }

    public void setEarea(String earea) {
        this.earea = earea;
    }

    public Integer getImagecount() {
        return imagecount;
    }

    public void setImagecount(Integer imagecount) {
        this.imagecount = imagecount;
    }

    public Integer getHit() {

        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }
}
