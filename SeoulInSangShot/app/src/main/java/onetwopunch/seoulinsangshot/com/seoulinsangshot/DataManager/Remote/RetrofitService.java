package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.AreaVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Best2VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.BestVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Comment2VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.CommentVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.FrameVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.ImageVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.LikeCountVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.MainVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.NotifyVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.ThemeVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.ViewCountVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Weather1VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Weather2VO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kwakgee on 2017. 9. 23..
 */

public interface RetrofitService {

    @GET("/image")
    Call<FrameVO> getTestData();

    @GET("/eimage")
    Call<FrameVO> getTestDataE();

    @GET("/reply")
    Call<CommentVO> getCommentData();

    @GET("/mainview")
    Call<AreaVO> getAreaData();

    @GET("service/SecndSrtpdFrcstInfoService2/ForecastGrib")
    Call<Weather1VO> loadWeather1(@Query("ServiceKey") String serviceKey, @Query("nx") String nx, @Query("ny") String ny, @Query("base_date") String base_date, @Query("base_time") String base_time, @Query("_type") String type);

    @GET("service/SecndSrtpdFrcstInfoService2/ForecastSpaceData")
    Call<Weather2VO> loadWeather2(@Query("ServiceKey") String serviceKey, @Query("nx") String nx, @Query("ny") String ny, @Query("base_date") String base_date, @Query("base_time") String base_time, @Query("numOfRows")String numOfRows, @Query("_type") String type);

    @GET("/upload")
    Call<BestVO> getBestData();
    @GET("/likeimg")
    Call<LikeCountVO>getLikeData();

    @GET("/hit")
    Call<ViewCountVO>getViewData();

    @GET("/upload")
    Call<ImageVO> getImageData();

    @GET("/themeview")
    Call<ThemeVO>getThemeData();

    @GET("/viewreply")
    Call<Comment2VO> getComment2Data();

    @GET("/top7")
    Call<MainVO> getMainData();

    @GET("/etop7")
    Call<MainVO> getMainDataE();

    @GET("/likes")
    Call<Best2VO>getBest2Data();

    @GET("/Notice")
    Call<NotifyVO>getNotifyData();

    @GET("/eNotice")
    Call<NotifyVO>getNotifyDataE();

}
