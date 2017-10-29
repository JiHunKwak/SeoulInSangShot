package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_theme;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.SubjectVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.ThemeVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment.Fragment_Show_Theme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MG_PARK on 2017-09-17.
 */

public class Theme_DataManager {

    private ThemeVO themeVO;
    public static List<SubjectVO> subjectVOs;

    public void loadData(){

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<ThemeVO> call = retrofitService.getThemeData();
        call.enqueue(new Callback<ThemeVO>() {
            @Override
            public void onResponse(Call<ThemeVO> call, Response<ThemeVO> response) {

                themeVO = response.body();
                subjectVOs = themeVO.getList();

                Adapter_theme themeAdapter=new Adapter_theme(subjectVOs);

                Fragment_Show_Theme.recyclerView.setAdapter(themeAdapter);


            }

            @Override
            public void onFailure(Call<ThemeVO> call, Throwable t) {

            }
        });

    }


}
