package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.BottomNavigationViewHelper;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Main;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,CustomDialog.OnCompleteListener {

    Intent primary;
    Intent album;
    Intent notify;
    Intent detail;

    AdapterViewFlipper avf;
    ImageView mainImage;
    TextView hitTv;
    TextView themeTv;
    TextView nameKorean;
    TextView nameEnglish;
    ArrayList<ImageView> indexes = new ArrayList<ImageView>();
    ArrayList<Model_Main> tempList = new ArrayList<Model_Main>();
    RelativeLayout mainRelativeLayout;

    CustomDialog customDialog = CustomDialog.getInstance();
    float xAtDown;
    float xAtUp;

    Menu items;

    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_search:
                    setIntentFlag(primary);
                    startActivity(primary);
                    return true;
                case R.id.navigation_album:
                    if(BaseActivity.isLogined == 0){
                        if(customDialog.isAdded()){
                            return false;
                        }
                        String message = "로그인 후 이용하세요.";
                        if(!BaseActivity.isKorean) {
                            message = "Please try after sign in";
                        }
                        new AlertDialog.Builder(MainActivity.this).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
                    }else {
                        boolean flag = false;
                        for(int i=0; i<BaseActivity.imageList.size(); i++){
                            if(BaseActivity.imageList.get(i).getId().equals(BaseActivity.email))
                                flag = true;
                        }
                        if(flag) {
                            setIntentFlag(album);
                            startActivity(album);
                        }else{
                            String msg = "앨범에 사진이 없습니다.";
                            if(!BaseActivity.isKorean) msg = "No picture in your album.";
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                case R.id.navigation_notifications:
                    setIntentFlag(notify);
                    startActivity(notify);
                    return true;
                case R.id.navigation_login:

                    if(customDialog.isAdded()){
                        return false;
                    }
                    FragmentManager fm = getSupportFragmentManager();
                    customDialog.show(fm, "Login Dialog");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        SharedPreferences pref = getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        boolean isFirst = pref.getBoolean("isFirst", true);

        if(isFirst){
            pref.edit().putBoolean("isFirst", false).apply();
            startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
        }

        primary = new Intent(getApplicationContext(), PrimaryActivity.class);
        album = new Intent(getApplicationContext(), AlbumActivity.class);
        notify = new Intent(getApplicationContext(), NotifyActivity.class);
        detail = new Intent(getApplicationContext(),DetailActivity.class);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        items=navigation.getMenu();

        indexes.add((ImageView) findViewById(R.id.indexLight1));
        indexes.add((ImageView) findViewById(R.id.indexLight2));
        indexes.add((ImageView) findViewById(R.id.indexLight3));
        indexes.add((ImageView) findViewById(R.id.indexLight4));
        indexes.add((ImageView) findViewById(R.id.indexLight5));
        indexes.add((ImageView) findViewById(R.id.indexLight6));
        indexes.add((ImageView) findViewById(R.id.indexLight7));


        nameKorean = (TextView) findViewById(R.id.main_areaName);
        nameEnglish=(TextView)findViewById(R.id.main_areaName2);
        hitTv = (TextView) findViewById(R.id.main_hitText);
        themeTv = (TextView) findViewById(R.id.main_theme1);
        mainRelativeLayout=(RelativeLayout)findViewById(R.id.mainRL);

        for (int i = 0; i < BaseActivity.mainList.size(); i++) {
            tempList.add(BaseActivity.mainList.get(i));
            if (i == 6) {
                i = BaseActivity.mainList.size();
            }
        }

        avf = (AdapterViewFlipper) findViewById(R.id.main_AVF);
        avf.setAdapter(new galleryAdapter(this, tempList));
        avf.setOnTouchListener(this);

    }
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
    public boolean onTouch(View v, MotionEvent event) {
        if (v != avf) return false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xAtDown = event.getX();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            xAtUp = event.getX();
            if (xAtDown > xAtUp) {
                avf.setInAnimation(v.getContext(), R.animator.right_in);
                avf.setOutAnimation(v.getContext(), R.animator.left_out);
                avf.showNext();
            } else if (xAtDown < xAtUp) {
                avf.setInAnimation(v.getContext(), R.animator.left_in);
                avf.setOutAnimation(v.getContext(), R.animator.right_out);
                avf.showPrevious();
                avf.setInAnimation(v.getContext(), R.animator.right_in);
                avf.setOutAnimation(v.getContext(), R.animator.left_out);
            }
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(BaseActivity.isLogined==0) {
            items.findItem(R.id.navigation_login).setIcon(R.drawable.login);
        }else{
            items.findItem(R.id.navigation_login).setIcon(R.drawable.logout);
        }

        navigation.setSelectedItemId(R.id.navigation_home);
    }

    public void setIntentFlag(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    public class galleryAdapter extends BaseAdapter {
        private final Context mContext;
        LayoutInflater inflater;
        ArrayList<Model_Main> tempArr;


        public galleryAdapter(Context mContext, ArrayList<Model_Main> tempArr) {
            this.mContext = mContext;
            this.tempArr = tempArr;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return tempList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View converView, ViewGroup parent) {

            if (converView == null) {
                converView = inflater.inflate(R.layout.item_main, parent, false);
            }

            mainRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    detail.putExtra("area", tempArr.get(position).getArea());
                    detail.putExtra("initials", tempArr.get(position).getInit());
                    detail.putExtra("name", tempArr.get(position).getName());
                    detail.putExtra("ename", tempArr.get(position).getEname());
                    detail.putExtra("lat", tempArr.get(position).getLat());
                    detail.putExtra("lng", tempArr.get(position).getLng());
                    detail.putExtra("subway", tempArr.get(position).getSubway());
                    detail.putExtra("bus", tempArr.get(position).getBus());
                    detail.putExtra("bicycle", tempArr.get(position).getBicycle());
                    detail.putExtra("url", tempArr.get(position).getUrl());
                    detail.putExtra("smartPhone", tempArr.get(position).getSmartPhone());
                    detail.putExtra("filter", tempArr.get(position).getFilter());
                    detail.putExtra("theme1", tempArr.get(position).getTheme1());
                    detail.putExtra("theme2", tempArr.get(position).getTheme2());
                    detail.putExtra("info", tempArr.get(position).getInfo());
                    detail.putExtra("tip", tempArr.get(position).getTip());
                    setIntentFlag(detail);
                    mContext.startActivity(detail);
                }
            });



            ImageView mimageView = (ImageView) converView.findViewById(R.id.mainImage);
            Picasso.with(mContext).load(tempArr.get(position).getUrl()).into(mimageView);

            for (int i = 0; i < tempArr.size(); i++) {
                String areaName = tempArr.get(i).getName();
                String areaEname = tempArr.get(i).getEname();
                String hits=tempArr.get(i).getHit();
                String theme=tempArr.get(i).getTheme1();
                if (i == position) {
                    nameEnglish.setText(areaEname);
                    nameKorean.setText(areaName);
                    hitTv.setText(hits);
                    themeTv.setText("# "+theme);
                }
            }
            for (int i = 0; i < indexes.size(); i++) {
                ImageView index = indexes.get(i);
                if (i == position) {
                    index.setImageResource(R.drawable.indexafter);
                } else {
                    index.setImageResource(R.drawable.indexbefore);
                }
            }

            return converView;
        }

    }

}
