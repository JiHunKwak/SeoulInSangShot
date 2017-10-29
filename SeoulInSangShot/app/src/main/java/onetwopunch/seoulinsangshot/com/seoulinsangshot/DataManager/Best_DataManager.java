package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import java.util.ArrayList;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.BestVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.DetailActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment.Fragment_Detail_Best;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Beom2 on 2017-10-06.
 */

public class Best_DataManager {

    private BestVO repo;
    private ArrayList<Model_Best> tempList;
    public void loadData() {

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<BestVO> call = retrofitService.getBestData();
        call.enqueue(new Callback<BestVO>() {
            @Override
            public void onResponse(Call<BestVO> call, Response<BestVO> response) {

                repo=response.body();
                tempList=repo.getList();
                for(int i=0;i<tempList.size();i++)
                {
                    String id=tempList.get(i).getId();
                    String area=tempList.get(i).getArea();
                    String url=tempList.get(i).getUrl();
                    String phoneType=tempList.get(i).getPhoneType();
                    String phoneApp=tempList.get(i).getPhoneApp();
                    String season=tempList.get(i).getSeason();
                    String time=tempList.get(i).getTime();
                    String tip=tempList.get(i).getTip();
                    String nowDate=tempList.get(i).getNowDate();
                    DetailActivity.bestList.add(new Model_Best(id,area,url,phoneType,phoneApp,season,time,tip,nowDate));
                }

            }

            @Override
            public void onFailure(Call<BestVO> call, Throwable t) {

            }
        });
    }


}
