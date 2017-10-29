package onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_Comment;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Constants;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.CommentVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Comment;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kwakgee on 2017. 9. 17..
 */

public class Fragment_Detail_Comment extends Fragment implements View.OnClickListener {

    String initials;

    RecyclerView rv_comment;
    LinearLayoutManager manager;
    Adapter_Comment adapter_comment;

    EditText commentET;
    FloatingActionButton commentFAB;
    Animation fab_open,fab_close,fab_turn;
    ImageView commentCheck;

    public CommentVO repoList;
    public ArrayList<Model_Comment> tempList;
    public ArrayList<Model_Comment> commentList;

    boolean isFabOpen=false;

    public Fragment_Detail_Comment() {

    }

    public static Fragment_Detail_Comment newInstance() {
        Fragment_Detail_Comment fragment = new Fragment_Detail_Comment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comment, container, false);

        rv_comment=(RecyclerView)rootView.findViewById(R.id.comment_RecyclerView);
        commentET =(EditText) rootView.findViewById(R.id.testEditText);
        commentFAB=(FloatingActionButton)rootView.findViewById(R.id.commentFAB);
        commentCheck=(ImageView)rootView.findViewById(R.id.checkImageView);


        AndroidNetworking.initialize(rootView.getContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initials = getArguments().getString("initials");

        manager = new LinearLayoutManager(rootView.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_close);
        commentFAB.setOnClickListener(this);
        commentCheck.setOnClickListener(this);

        Retrofit client = new Retrofit.Builder().baseUrl(Constants.TEST_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = client.create(RetrofitService.class);

        Call<CommentVO> call = service.getCommentData();
        call.enqueue(new Callback<CommentVO>() {
            @Override
            public void onResponse(Call<CommentVO> call, Response<CommentVO> response) {
                if (response.isSuccessful()) {
                    repoList = response.body();
                    tempList = repoList.getList();
                    commentList=new ArrayList<Model_Comment>();
                    for (int i = 0; i < tempList.size(); i++) {
                        if (tempList.get(i).getInit().equals(initials)) {
                            String init = tempList.get(i).getInit();
                            String id = tempList.get(i).getId();
                            String text = tempList.get(i).getText();
                            String date = tempList.get(i).getDate();
                            commentList.add(new Model_Comment(init, id, text,date));
                        }
                    }
                    adapter_comment = new Adapter_Comment(getContext(), commentList);
                    rv_comment.setLayoutManager(manager);
                    rv_comment.setAdapter(adapter_comment);
                }
            }

            @Override
            public void onFailure(Call<CommentVO> call, Throwable t) {
            }
        });
        return rootView;
    }

    private void postMessage(String message,String getArea,String getDate)
    {
        final String text= message;
        final String init =getArea;
        final String date=getDate;
        //id 부분은 넘겨온 값을 이용할 예정.
        final String id=BaseActivity.email;

        AndroidNetworking.post("http://13.124.87.34:3000/preply")
                .addBodyParameter("id",id)
                .addBodyParameter("init",init)
                .addBodyParameter("text",text)
                .addBodyParameter("date",date)
                .addHeaders("Content-Type", "multipart/form-data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        commentList.add(new Model_Comment(init,id,text,date));
                        adapter_comment = new Adapter_Comment(getContext(), commentList);
                        rv_comment.setLayoutManager(manager);
                        rv_comment.setAdapter(adapter_comment);
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void DialogSimple(final String message , final String initials){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
        String messageTemp = "댓글을 등록 할까요?";
        if(!BaseActivity.isKorean) messageTemp = "Send comment?";
        alt_bld.setMessage(messageTemp).setCancelable(
                true).setPositiveButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                }).setNegativeButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String getMessage=message;
                        String getInit=initials;

                        long now = System.currentTimeMillis();
                        java.util.Date date = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                        String nowTime=sdfNow.format(date);
                        postMessage(getMessage,getInit,nowTime);

                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("Message");
        alert.setIcon(R.drawable.ic_logo);
        alert.show();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.commentFAB:
                if(BaseActivity.isLogined == 0){
                    String message = "로그인 후 이용하세요.";
                    if(!BaseActivity.isKorean) {
                        message = "Please try after sign in";
                    }
                    new AlertDialog.Builder(getContext()).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
                }else{
                    animateFAB();
                }
                break;
            case R.id.checkImageView:
                if(commentET.getText().toString().isEmpty()){
                    String msg = "댓글을 입력하세요";
                    if(!BaseActivity.isKorean) msg = "Please enter message";
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }else {
                    DialogSimple(commentET.getText().toString(), initials);
                    animateFAB();
                }
                break;

        }

    }

    public void animateFAB(){
        if(isFabOpen){

            commentCheck.startAnimation(fab_close);
            commentCheck.setClickable(false);

            commentFAB.startAnimation(fab_open);
            commentFAB.setClickable(true);

            commentET.setVisibility(View.INVISIBLE);
            commentET.setText(null);
            InputMethodManager immhide = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            immhide.hideSoftInputFromWindow(commentET.getWindowToken(),0);
            isFabOpen = false;

        } else {

            commentFAB.startAnimation(fab_close);

            commentCheck.setVisibility(View.VISIBLE);
            commentCheck.startAnimation(fab_open);
            commentCheck.setClickable(true);

            commentFAB.setClickable(false);

            isFabOpen = true;

            commentET.setVisibility(View.VISIBLE);
            commentET.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

}
