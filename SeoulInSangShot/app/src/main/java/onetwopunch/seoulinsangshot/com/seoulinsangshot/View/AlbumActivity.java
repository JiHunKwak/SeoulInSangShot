package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.BottomNavigationViewHelper;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.CoverFlowAdapter;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Best2VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Image;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity.imageList;

public class AlbumActivity extends AppCompatActivity implements CustomDialog.OnCompleteListener {

    Intent home;
    Intent primary;
    Intent notify;

    Intent bestReply;

    Menu items;

    CustomDialog customDialog = CustomDialog.getInstance();

    int BestCount =0 ;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setIntentFlag(home);
                    startActivity(home);
                    return true;
                case R.id.navigation_search:
                    setIntentFlag(primary);
                    startActivity(primary);
                    return true;
                case R.id.navigation_album:
                    return true;
                case R.id.navigation_notifications:
                    setIntentFlag(notify);
                    startActivity(notify);
                    return true;
                case R.id.navigation_login:
                    FragmentManager fm = getSupportFragmentManager();
                    customDialog.show(fm, "Login Dialog");
                    return true;
            }
            return false;
        }

    };

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    TextView idView;
    TextView countView;
    ArrayList<Model_Image> mData;
    String myAlbumID;

    Best2VO repo;
    ArrayList<Model_Best2> tempList;
    ArrayList<Model_Best2> mData2;


    private TextSwitcher switcher;

    @Override
    public void onInputedData(String name, String email) {

        BaseActivity.name=name;
        BaseActivity.email=email;

        if(BaseActivity.name==null && BaseActivity.email==null) {
            items.findItem(R.id.navigation_login).setIcon(R.drawable.login);
        }else{
            items.findItem(R.id.navigation_login).setIcon(R.drawable.logout);
        }

        System.out.println("name, email"+BaseActivity.name+" , "+BaseActivity.email);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album);

        myAlbumID=BaseActivity.email;

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        home = new Intent(getApplicationContext(), MainActivity.class);
        primary = new Intent(getApplicationContext(), PrimaryActivity.class);
        notify = new Intent(getApplicationContext(), NotifyActivity.class);

        mData=new ArrayList<>();


        for(int i=0; i< imageList.size();i++){
            String id= imageList.get(i).getId();
            String url= imageList.get(i).getUrl();
            String tip= imageList.get(i).getTip();
            String likecount= imageList.get(i).getLikecount();
            String nowdate= imageList.get(i).getNowdate();
            String area= imageList.get(i).getArea();
            String phoneApp= imageList.get(i).getPhoneApp();
            String phoneType= imageList.get(i).getPhoneType();
            String time= imageList.get(i).getTime();

            if(id.equals(myAlbumID)) {

                mData.add(new Model_Image(url,id,tip,likecount, nowdate,area, phoneApp,phoneType,time));

            }
        }



        if(mData.size()!=0)
        {

        for(int i=0 ; i <mData.size();i++){
            for(int j=0 ; j< BaseActivity.testArr.size();j++)
            {
                if(mData.get(i).getUrl().equals(BaseActivity.testArr.get(j).getUrl())){
                    BestCount=BestCount+1;
                }
            }
        }

        String count=String.valueOf(mData.size());


        countView=(TextView)findViewById(R.id.count);
        countView.setText("Total  : " + count + "   Best  : " + BestCount );

        switcher=(TextSwitcher)findViewById(R.id.text);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(AlbumActivity.this);
                TextView textView=(TextView) inflater.inflate(R.layout.item_text, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);

            bestReply = new Intent(getApplicationContext(), Best_ReplyActivity.class);

            mAdapter = new CoverFlowAdapter(getApplicationContext(), mData);
            mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
            mCoverFlow.setAdapter(mAdapter);

            mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(),Best_ReplyActivity.class);

                    if(mData.size()==1)
                    {
                        intent.putExtra("url",mData.get(0).getUrl());
                        intent.putExtra("image",mData.get(0).getUrl());
                        intent.putExtra("email",mData.get(0).getId());
                        intent.putExtra("tip",mData.get(0).getTip());
                        intent.putExtra("phoneApp",mData.get(0).getPhoneApp());
                        intent.putExtra("phoneType",mData.get(0).getPhoneType());
                        setIntentFlag(intent);
                        startActivity(intent);
                    }
                    else if(mData.size()==2)
                    {
                        intent.putExtra("url",mData.get(0).getUrl());
                        intent.putExtra("image",mData.get(i%mData.size()).getUrl());
                        intent.putExtra("email",mData.get(i%mData.size()).getId());
                        intent.putExtra("tip",mData.get(i%mData.size()).getTip());
                        intent.putExtra("phoneApp",mData.get(i%mData.size()).getPhoneApp());
                        intent.putExtra("phoneType",mData.get(i%mData.size()).getPhoneType());
                        setIntentFlag(intent);
                        startActivity(intent);
                    }
                    else{
                        intent.putExtra("url",mData.get(0).getUrl());
                        intent.putExtra("image",mData.get(i%mData.size()).getUrl());
                        intent.putExtra("email",mData.get(i%mData.size()).getId());
                        intent.putExtra("tip",mData.get(i%mData.size()).getTip());
                        intent.putExtra("phoneApp",mData.get(i%mData.size()).getPhoneApp());
                        intent.putExtra("phoneType",mData.get(i%mData.size()).getPhoneType());
                        setIntentFlag(intent);
                        startActivity(intent);

                    }

                }
            });


            mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
                @Override
                public void onScrolledToPosition(int position) {
                    switcher.setText(mData.get(position).getNowdate()+"    "+ mData.get(position).getLikecount()+" likes"  );
                }

                @Override
                public void onScrolling() {
                    switcher.setText("");
                }
            });

            navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setSelectedItemId(R.id.navigation_album);
            BottomNavigationViewHelper.disableShiftMode(navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            items=navigation.getMenu();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(BaseActivity.isLogined==0) {
            items.findItem(R.id.navigation_login).setIcon(R.drawable.login);
        }else{
            items.findItem(R.id.navigation_login).setIcon(R.drawable.logout);
        }

        navigation.setSelectedItemId(R.id.navigation_album);
        loadData();


    }



    public void loadData()
    {
        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<Best2VO> call = retrofitService.getBest2Data();
        call.enqueue(new Callback<Best2VO>() {
            @Override
            public void onResponse(Call<Best2VO> call, Response<Best2VO> response) {
                repo = response.body();
                tempList = repo.getList();

                for (int i = 0; i < tempList.size(); i++) {
                    for(int j=0;j<mData.size();j++) {
                        if (tempList.get(i).getUrl().equals(mData.get(j).getUrl())) {
                            String url = tempList.get(i).getUrl();
                            String likes = tempList.get(i).getLikes();
                            mData.get(j).setLikecount(likes);
                            mAdapter = new CoverFlowAdapter(getApplicationContext(), mData);
                            mCoverFlow.setAdapter(mAdapter);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<Best2VO> call, Throwable t) {

            }
        });



    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

}
