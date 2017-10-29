package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kwakgee on 2017. 9. 23..
 */

public class FrameVO {

    @SerializedName("list")
    @Expose
    private List<InnerVO> list;

    public List<InnerVO> getList() {
        return list;
    }
}
