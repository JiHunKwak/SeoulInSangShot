package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import android.app.Activity;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.FrameVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.InnerVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kwakgee on 2017. 9. 23..
 */

public class Test_DataManager {

    private FrameVO frameVO;
    private List<InnerVO> innerVOs;

    public void loadData(final Activity activity){

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<FrameVO> call = retrofitService.getTestData();
        if(!BaseActivity.isKorean) call = retrofitService.getTestDataE();
        call.enqueue(new Callback<FrameVO>() {
            @Override
            public void onResponse(Call<FrameVO> call, Response<FrameVO> response) {

                frameVO = response.body();
                innerVOs = frameVO.getList();

                for(int i=0; i< innerVOs.size(); i++) {

                    int number = innerVOs.get(i).getNumber();
                    String area = innerVOs.get(i).getArea();
                    String initials = innerVOs.get(i).getInitials();
                    String name = innerVOs.get(i).getName();
                    String ename = innerVOs.get(i).getEname();
                    String lat = innerVOs.get(i).getLat();
                    String lng = innerVOs.get(i).getLng();
                    String subway = innerVOs.get(i).getSubway();
                    String bus = innerVOs.get(i).getBus();
                    String bicycle = innerVOs.get(i).getBicycle();
                    String url = innerVOs.get(i).getUrl();
                    String smartPhone = innerVOs.get(i).getSmartPhone();
                    String filter = innerVOs.get(i).getFilter();
                    String theme1 = innerVOs.get(i).getTheme1();
                    String theme2 = innerVOs.get(i).getTheme2();
                    String time = innerVOs.get(i).getTime();
                    String tip = innerVOs.get(i).getTip();
                    String info= innerVOs.get(i).getInfo();
                    int likeimage= innerVOs.get(i).getLikeiamge();
                    int viewimage= innerVOs.get(i).getViewimage();
                    String hit= innerVOs.get(i).getHit();

                    BaseActivity.testArr.add(new Model_Test(number, area, initials, name, ename, lat, lng, subway, bus, bicycle, url, smartPhone, filter, theme1, theme2, time, tip, info, likeimage, viewimage,hit));

                }

                Album_DataManager album_dataManager = new Album_DataManager();
                album_dataManager.loadData(activity);

            }

            @Override
            public void onFailure(Call<FrameVO> call, Throwable t) {

            }
        });

    }

}