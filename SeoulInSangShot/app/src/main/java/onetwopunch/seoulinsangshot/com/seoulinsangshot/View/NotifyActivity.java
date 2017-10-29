package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_Notify;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.BottomNavigationViewHelper;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Notify;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

public class NotifyActivity extends AppCompatActivity implements CustomDialog.OnCompleteListener{

    Intent home;
    Intent primary;
    Intent album;
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
                        new AlertDialog.Builder(NotifyActivity.this).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
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
                            Toast.makeText(NotifyActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                case R.id.navigation_login:
                    if(customDialog.isAdded()){
                        return false;
                    }

                    FragmentManager fm = getSupportFragmentManager();
                    customDialog.show(fm, "Login Dialog");
                    return true;
                case R.id.navigation_notifications:
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


    private RecyclerView recyclerView;
    ArrayList<Model_Notify> notifyArrayList;
    Adapter_Notify adapter_notify;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        notifyArrayList= new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.rv_notify);
        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter_notify= new Adapter_Notify(BaseActivity.notifyList,getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter_notify);
        recyclerView.smoothScrollToPosition(0);


        home = new Intent(getApplicationContext(), MainActivity.class);
        primary = new Intent(getApplicationContext(), PrimaryActivity.class);
        album = new Intent(getApplicationContext(), AlbumActivity.class);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_notifications);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        items=navigation.getMenu();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(BaseActivity.isLogined==0) {
            items.findItem(R.id.navigation_login).setIcon(R.drawable.login);
        }else{
            items.findItem(R.id.navigation_login).setIcon(R.drawable.logout);
        }

        navigation.setSelectedItemId(R.id.navigation_notifications);

    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }
}
