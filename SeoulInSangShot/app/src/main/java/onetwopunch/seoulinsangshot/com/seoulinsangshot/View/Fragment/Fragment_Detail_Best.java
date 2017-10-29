package onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_Best;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Best2VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.DetailActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.PostActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kwakgee on 2017. 9. 17..
 */

public class Fragment_Detail_Best extends Fragment {


    public ArrayList<Model_Best> adapterBestList;
    public ArrayList<Model_Best2> adapterBestList2;
    public static ArrayList<Model_Best2> bestList;
    private Best2VO repo;
    private ArrayList<Model_Best2> tempList;

    RecyclerView rv_best;
    LinearLayoutManager manager;
    Adapter_Best adapter_best;

    String initials;

    FloatingActionButton bestFAB;

    Intent uploadP;

    public static Fragment_Detail_Best newInstance() {
        Fragment_Detail_Best fragment = new Fragment_Detail_Best();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_best, container, false);

        uploadP = new Intent(getContext(), PostActivity.class);

        initials = getArguments().getString("initials");

        bestFAB = (FloatingActionButton) rootView.findViewById(R.id.bestFAB);
        rv_best = (RecyclerView) rootView.findViewById(R.id.best_RecyclerView);
        bestList = new ArrayList<Model_Best2>();

        bestFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.isLogined == 0){
                    String message = "로그인 후 이용하세요.";
                    if(!BaseActivity.isKorean) {
                        message = "Please try after sign in";
                    }
                    new AlertDialog.Builder(getContext()).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
                }else{
                    uploadP.putExtra("initials", initials);
                    setIntentFlag(uploadP);
                    startActivity(uploadP);
                }

            }
        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        loadBestData();

    }
    public  void loadBestData()
    {
        adapterBestList = new ArrayList<Model_Best>();
        adapterBestList2 = new ArrayList<Model_Best2>();

        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<Best2VO> call = retrofitService.getBest2Data();
        call.enqueue(new Callback<Best2VO>() {
            @Override
            public void onResponse(Call<Best2VO> call, Response<Best2VO> response) {

                repo = response.body();
                tempList = repo.getList();

                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).getArea().equals(initials)) {
                        String url = tempList.get(i).getUrl();
                        String likes = tempList.get(i).getLikes();
                        adapterBestList2.add(new Model_Best2(url, likes));
                    }

                }

                for (int i = 0; i < DetailActivity.bestList.size(); i++) {
                    if (DetailActivity.bestList.get(i).getArea().equals(initials)) {
                        String id = DetailActivity.bestList.get(i).getId();
                        String area = DetailActivity.bestList.get(i).getArea();
                        String url = DetailActivity.bestList.get(i).getUrl();
                        String phoneType = DetailActivity.bestList.get(i).getPhoneType();
                        String phoneApp = DetailActivity.bestList.get(i).getPhoneApp();
                        String season = DetailActivity.bestList.get(i).getSeason();
                        String time = DetailActivity.bestList.get(i).getTime();
                        String tip = DetailActivity.bestList.get(i).getTip();
                        String nowDate=DetailActivity.bestList.get(i).getNowDate();
                        adapterBestList.add(new Model_Best(id, area, url, phoneType, phoneApp, season, time, tip,nowDate));
                    }
                }

                manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                adapter_best = new Adapter_Best(getContext(), adapterBestList, adapterBestList2);
                rv_best.setLayoutManager(manager);
                rv_best.setAdapter(adapter_best);

            }

            @Override
            public void onFailure(Call<Best2VO> call, Throwable t) {

            }
        });
    }

    public void setIntentFlag(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

}
