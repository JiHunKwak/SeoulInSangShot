package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kwakgee on 2017. 9. 24..
 */

public class RetrofitClient {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
