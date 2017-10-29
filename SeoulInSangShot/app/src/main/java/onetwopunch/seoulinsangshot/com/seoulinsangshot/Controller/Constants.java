package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

/**
 * Created by kwakgee on 2017. 9. 24..
 */

public class Constants {

    public static final String TEST_BASE_URL = "http://13.124.87.34:3000/";
    public static final String reqURL_Weather = "http://newsky2.kma.go.kr/";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;
    public static final String req_URL ="http://13.124.87.34:3000/";

    public static String switchNX(String area)
    {
        if(area.equals("양천구")||area.equals("강서구")||area.equals("구로구")||area.equals("영등포구"))
        {
            return "58";
        }
        else if(area.equals("은평구")||area.equals("서대문구")||area.equals("마포구")||area.equals("금천구") ||
                area.equals("동작구")||area.equals("관악구"))
        {
            return "59";
        }
        else if(area.equals("종로구")||area.equals("중구")||area.equals("용산구"))
        {
            return "60";
        }
        else if(area.equals("성동구")||area.equals("동대문구")||area.equals("성북구")||area.equals("도봉구")||
                area.equals("강북구")||area.equals("노원구")||area.equals("서초구")||area.equals("강남구"))
        {
            return "61";
        }
        else if(area.equals("광진구")||area.equals("중랑구")||area.equals("송파구")||area.equals("강동구"))
        {
            return "62";
        }
        else
            return "60";
    }


    public static String switchNY(String area)
    {
        if(area.equals("구로구")||area.equals("동작구")||area.equals("관악구")||area.equals("서초구")||area.equals("강남구"))
        {
            return "125";
        }
        else if(area.equals("용산구")||area.equals("광진구")||area.equals("양천구")||area.equals("강서구") ||
                area.equals("영등포구")||area.equals("송파구")||area.equals("강동구"))
        {
            return "126";
        }
        else if(area.equals("종로구")||area.equals("중구")||area.equals("성동구")||area.equals("동대문구")||
                area.equals("성북구")||area.equals("서대문구")||area.equals("마포구"))
        {
            return "127";
        }
        else if(area.equals("중랑구")||area.equals("강북구")||area.equals("은평구"))
        {
            return "128";
        }
        else if(area.equals("도봉구")||area.equals("노원구"))
        {
            return "129";
        }
        else if(area.equals("금천구"))
        {
            return "124";
        }
        else
            return "126";
    }

}
