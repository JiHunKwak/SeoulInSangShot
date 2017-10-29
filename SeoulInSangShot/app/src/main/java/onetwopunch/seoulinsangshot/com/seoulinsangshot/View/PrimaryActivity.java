package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.BottomNavigationViewHelper;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment.Fragment_Show_Area;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment.Fragment_Show_Theme;

public class PrimaryActivity extends AppCompatActivity implements CustomDialog.OnCompleteListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Fragment_Show_Area fragmentArea;
    private Fragment_Show_Theme fragmentTheme;

    Intent home;
    Intent album;
    Intent notify;

    Menu items;

    CustomDialog customDialog = CustomDialog.getInstance();

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
                        new AlertDialog.Builder(PrimaryActivity.this).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
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
                            Toast.makeText(PrimaryActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_primary);

        home = new Intent(this, MainActivity.class);
        album = new Intent(getApplicationContext(), AlbumActivity.class);
        notify = new Intent(getApplicationContext(), NotifyActivity.class);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_search);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        items=navigation.getMenu();

        fragmentArea = new Fragment_Show_Area();
        fragmentTheme = new Fragment_Show_Theme();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(BaseActivity.isLogined==0) {
            items.findItem(R.id.navigation_login).setIcon(R.drawable.login);
        }else{
            items.findItem(R.id.navigation_login).setIcon(R.drawable.logout);
        }

        navigation.setSelectedItemId(R.id.navigation_search);

    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){
                case 0:
                    return fragmentArea;
                case 1:
                    return fragmentTheme;
                default:
                    return fragmentArea;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title1 = "지도로 보기";
            String title2 = "테마로 보기";
            if(!BaseActivity.isKorean){
                title1 = "Search with map";
                title2 = "Search with theme";
            }
            switch (position) {
                case 0:
                    return title1;
                case 1:
                    return title2;
            }
            return null;
        }
    }
}
