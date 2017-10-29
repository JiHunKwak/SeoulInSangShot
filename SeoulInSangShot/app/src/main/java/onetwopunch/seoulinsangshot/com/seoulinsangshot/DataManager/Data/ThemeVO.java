package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MG_PARK on 2017-09-17.
 */

public class ThemeVO {

    @SerializedName("list")
    @Expose
    private List<SubjectVO> list = null;

    public List<SubjectVO> getList() {
        return list;
    }

    public void setList(List<SubjectVO> list) {
        this.list = list;
    }
}
