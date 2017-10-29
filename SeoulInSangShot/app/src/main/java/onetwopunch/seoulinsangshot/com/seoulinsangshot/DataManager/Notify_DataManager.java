package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager;

import android.app.Activity;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Constants;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.NotifyVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Notify;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 301-29 on 2017-10-22.
 */

public class Notify_DataManager {
    public ArrayList<Model_Notify> notifyArrayList;
    public NotifyVO notifyVO;

    public void loadData(final Activity activity){
        Retrofit client = new Retrofit.Builder().baseUrl(Constants.req_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = client.create(RetrofitService.class);
        Call<NotifyVO> call = service.getNotifyData();
        if(!BaseActivity.isKorean) call = service.getNotifyDataE();
        call.enqueue(new Callback<NotifyVO>() {
            @Override
            public void onResponse(Call<NotifyVO> call, Response<NotifyVO> response) {
                if(response.isSuccessful()){
                    notifyVO=response.body();
                    notifyArrayList=notifyVO.getList();
                    for(int i=0; i< notifyArrayList.size();i++)
                    {
                        String topic =notifyArrayList.get(i).getTopic();
                        String text=notifyArrayList.get(i).getText();
                        String date=notifyArrayList.get(i).getDate();
                        BaseActivity.notifyList.add(new Model_Notify(topic, text, date));
                    }

                    Main_DataManager main_dataManager = new Main_DataManager();
                    main_dataManager.loadData(activity);

                }
            }

            @Override
            public void onFailure(Call<NotifyVO> call, Throwable t) {

            }
        });

    }
}
