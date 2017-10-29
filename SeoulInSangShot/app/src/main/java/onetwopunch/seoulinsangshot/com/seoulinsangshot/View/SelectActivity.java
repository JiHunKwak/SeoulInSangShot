package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.ViewCountVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_ViewCount;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectActivity extends AppCompatActivity {

    Intent home;

    RecyclerView rv_select;
    LinearLayoutManager manager;
    Adapter_Test adapter_test;
    List<Model_Test> selectedImg;
    ArrayList<Model_ViewCount> beomArr;

    ViewCountVO repo;
    ArrayList<Model_ViewCount> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        home = new Intent(this, MainActivity.class);

        rv_select = (RecyclerView) findViewById(R.id.rv_select);
        manager = new LinearLayoutManager(getApplicationContext());





    }

    public void setAdapter()
    {
        selectedImg=(ArrayList<Model_Test>)getIntent().getSerializableExtra("imgList");

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<ViewCountVO> call = retrofitService.getViewData();
        call.enqueue(new Callback<ViewCountVO>() {
            @Override
            public void onResponse(Call<ViewCountVO> call, Response<ViewCountVO> response) {
                repo = response.body();
                tempList = repo.getList();

                for (int i = 0; i < tempList.size(); i++) {
                    for(int j=0; j<selectedImg.size();j++) {
                        if (tempList.get(i).getInit().equals(selectedImg.get(j).getInitials())) {
                            String init = tempList.get(i).getInit();
                            String hit = tempList.get(i).getHit();
                            selectedImg.get(j).setHit(hit);
                        }
                    }
                }

                if (selectedImg != null) {
                    adapter_test = new Adapter_Test(selectedImg, getApplicationContext());
                }

                rv_select.setLayoutManager(manager);
                rv_select.setAdapter(adapter_test);

            }

            @Override
            public void onFailure(Call<ViewCountVO> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_primary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setIntentFlag(home);
        startActivity(home);

        return super.onOptionsItemSelected(item);
    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

}
