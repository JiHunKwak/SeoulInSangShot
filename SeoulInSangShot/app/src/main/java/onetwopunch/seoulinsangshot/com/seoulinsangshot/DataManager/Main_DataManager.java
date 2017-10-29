package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import android.app.Activity;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.MainVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Main;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 301 on 2017-10-21.
 */

public class Main_DataManager {

    MainVO repo;
    ArrayList<Model_Main>tempList;

    public void loadData(final Activity activity)
    {
        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<MainVO> call = retrofitService.getMainData();
        if(!BaseActivity.isKorean) call = retrofitService.getMainDataE();
        call.enqueue(new Callback<MainVO>() {
            @Override
            public void onResponse(Call<MainVO> call, Response<MainVO> response) {
                repo=response.body();
                tempList=repo.getList();

                for(int i=0; i<tempList.size();i++)
                {
                     String area=tempList.get(i).getArea();
                     String init=tempList.get(i).getInit();
                     String name=tempList.get(i).getName();
                     String lat=tempList.get(i).getLat();
                     String lng=tempList.get(i).getLng();
                     String subway=tempList.get(i).getSubway();
                     String bus=tempList.get(i).getBus();
                     String bicycle=tempList.get(i).getBicycle();
                     String url=tempList.get(i).getUrl();
                     String smartPhone=tempList.get(i).getSmartPhone();
                     String filter=tempList.get(i).getFilter();
                     String theme1=tempList.get(i).getTheme1();
                     String theme2=tempList.get(i).getTheme2();
                    String info=tempList.get(i).getInfo();
                     String tip=tempList.get(i).getTip();
                     String ename=tempList.get(i).getEname();
                     String hit=tempList.get(i).getHit();

                    BaseActivity.mainList.add(new Model_Main(area,init,name,lat,lng,subway,bus,bicycle,url,smartPhone,filter,theme1,theme2,info,tip,ename,hit));

                }

                Area_DataManager manager=new Area_DataManager();
                manager.loadData(activity);

            }

            @Override
            public void onFailure(Call<MainVO> call, Throwable t) {

            }
        });
    }
}
