package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.AreaVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.DistrictVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MG_PARK on 2017-09-30.
 */

public class Area_DataManager {

    private AreaVO areaVO;
    public static List<DistrictVO> districtVOs;

    public void loadData(final Activity activity){

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<AreaVO> call = retrofitService.getAreaData();
        call.enqueue(new Callback<AreaVO>() {
            @Override
            public void onResponse(Call<AreaVO> call, Response<AreaVO> response) {

                areaVO = response.body();
                districtVOs = areaVO.getList();

                Intent intent = new Intent(activity, MainActivity.class);
                setIntentFlag(intent);
                activity.startActivity(intent);
                activity.finish();

            }

            @Override
            public void onFailure(Call<AreaVO> call, Throwable t) {

            }
        });

    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

}
