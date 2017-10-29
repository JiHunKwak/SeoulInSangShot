package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MG_PARK on 2017-09-30.
 */

public class AreaVO {

    @SerializedName("list")
    @Expose
    private List<DistrictVO> list;

    public List<DistrictVO> getList() {
        return list;
    }
}
