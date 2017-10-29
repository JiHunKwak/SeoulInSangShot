package onetwopunch.seoulinsangshot.com.seoulinsangshot.Model;

/**
 * Created by user on 2017-10-03.
 */

public class Model_Image {
    String url;
    String id;
    String tip;
    String likecount;
    String view;
    String nowDate;
    String area;
    String phoneType;
    String phoneApp;
    String time;

    public Model_Image(){

    }

    public Model_Image(String url, String id, String tip,  String nowDate,String area, String phoneType, String phoneApp, String time)
    {
        this.url=url;
        this.id=id;
        this.tip=tip;
        this.nowDate=nowDate;
        this.area=area;
        this.phoneApp=phoneApp;
        this.phoneType=phoneType;
        this.time=time;
    }

    public Model_Image(String url, String id, String tip, String likecount, String nowDate,String area, String phoneType, String phoneApp, String time)
    {
        this.url=url;
        this.id=id;
        this.tip=tip;
        this.likecount=likecount;
        this.nowDate=nowDate;
        this.area=area;
        this.phoneApp=phoneApp;
        this.phoneType=phoneType;
        this.time=time;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getId() {
        return id;
    }

    public String getUrl(){
        return url;

    }
    public String getTip(){
        return tip;
    }
    public String getLikecount(){
        return likecount;
    }
    public String getView(){
        return view;
    }
    public String getNowdate(){ return  nowDate;}
    public String getArea(){return  area;}
    public String getPhoneApp(){return  phoneApp;}
    public String getPhoneType(){return  phoneType;}
    public String getTime(){return  time;}


}
