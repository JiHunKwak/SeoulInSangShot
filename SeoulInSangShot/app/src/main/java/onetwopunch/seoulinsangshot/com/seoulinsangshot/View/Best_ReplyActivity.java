package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.oliveiradev.image_zoom.lib.widget.ImageZoom;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Adapter_Comment2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Constants;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.Comment2VO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.LikeCountVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitClient;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Remote.RetrofitService;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Comment2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_LikeCount;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Best_ReplyActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView likeIv;
    ImageView dislikeIv;
    TextView likeTv;
    Animation open, close;
    Animation fab_open,fab_close;

    RecyclerView rv_comment;
    LinearLayoutManager manager;
    Adapter_Comment2 adapter_comment;
    EditText commentET;
    FloatingActionButton commentFAB;
    ImageView commentCheck;

    Comment2VO repoList;
    LikeCountVO likerepo;

    ArrayList<Model_Comment2> tempList;
    ArrayList<Model_Comment2> commentList;
    ArrayList<Model_LikeCount> likeTempList;
    public static ArrayList<Model_LikeCount> likeList;

    Intent home;

    String url;
    boolean isFabOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best__reply);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();

        home = new Intent(this, MainActivity.class);

        likeIv = (ImageView)findViewById(R.id.like);
        dislikeIv = (ImageView)findViewById(R.id.dislike);
        likeTv=(TextView)findViewById(R.id.best_reply_likeText);
        open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        TextView best_email=(TextView)findViewById(R.id.txt_best_name);
        TextView best_tip=(TextView)findViewById(R.id.txt_best_tip);
        TextView best_phone=(TextView)findViewById(R.id.txt_best_theme);
        TextView best_app=(TextView)findViewById(R.id.txt_best_theme2);
        ImageZoom best_img=(ImageZoom)findViewById(R.id.img_best_cover);

        rv_comment=(RecyclerView)findViewById(R.id.rv_reply);
        commentET =(EditText) findViewById(R.id.best_reply_commentET);
        commentFAB=(FloatingActionButton)findViewById(R.id.best_reply_commentFAB);
        commentCheck=(ImageView)findViewById(R.id.best_reply_commentBT);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        AndroidNetworking.initialize(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        commentFAB.setOnClickListener(this);
        commentCheck.setOnClickListener(this);


        url=intent.getStringExtra("url");  //이 값으로 댓글 확인
        Picasso.with(this).load(intent.getStringExtra("image")).into(best_img);
        best_email.setText(intent.getStringExtra("email"));
        best_tip.setText(intent.getStringExtra("tip"));
        best_phone.setText(intent.getStringExtra("phoneType"));
        best_app.setText(intent.getStringExtra("phoneApp"));

        dislikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseActivity.isLogined == 0){
                    String message = "로그인 후 이용하세요.";
                    if(!BaseActivity.isKorean) {
                        message = "Please try after sign in";
                    }
                    new AlertDialog.Builder(Best_ReplyActivity.this).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
                }else {
                    dislikeIv.startAnimation(close);
                    dislikeIv.setClickable(false);
                    likeIv.setClickable(true);
                    likeIv.startAnimation(open);
                    likeIv.setVisibility(View.VISIBLE);
                    setLikeData(url, BaseActivity.email);
                }
            }
        });
        likeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeIv.startAnimation(close);
                likeIv.setClickable(false);
                dislikeIv.setClickable(true);
                dislikeIv.startAnimation(open);
                dislikeIv.setVisibility(View.VISIBLE);
                setDislikeData(url, BaseActivity.email);

            }
        });
        loadLikeData(url);  ///좋아요 로드.

        Retrofit client = new Retrofit.Builder().baseUrl(Constants.TEST_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService service = client.create(RetrofitService.class);

        Call<Comment2VO> call = service.getComment2Data();
        call.enqueue(new Callback<Comment2VO>() {
            @Override
            public void onResponse(Call<Comment2VO> call, Response<Comment2VO> response) {
                if (response.isSuccessful()) {
                    repoList = response.body();
                    tempList = repoList.getList();
                    commentList=new ArrayList<Model_Comment2>();
                    for (int i = 0; i < tempList.size(); i++) {
                        if (tempList.get(i).getUrl().equals(url)) {
                            String thisUrl = tempList.get(i).getUrl();
                            String id = tempList.get(i).getId();
                            String text = tempList.get(i).getText();
                            String date = tempList.get(i).getDate();
                            commentList.add(new Model_Comment2(id,thisUrl,text,date));
                        }
                    }
                    adapter_comment = new Adapter_Comment2(getApplicationContext(), commentList);
                    rv_comment.setLayoutManager(manager);
                    rv_comment.setAdapter(adapter_comment);
                }
            }

            @Override
            public void onFailure(Call<Comment2VO> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public void setDislikeData(String getUrl,String getId)
    {
        final String url=getUrl;
        String id=getId;

        AndroidNetworking.post("http://13.124.87.34:3000/dellike")
                .addBodyParameter("url", url)
                .addBodyParameter("id", id)
                .addHeaders("Content-Type", "multipart/form-data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadLikeData(url);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    public void setLikeData(String getUrl,String getId)
    {
        final String url=getUrl;
        String id=getId;

        AndroidNetworking.post("http://13.124.87.34:3000/plike")
                .addBodyParameter("url", url)
                .addBodyParameter("id", id)
                .addHeaders("Content-Type", "multipart/form-data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadLikeData(url);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    public void loadLikeData(final String getUrl) {
        likeList=new ArrayList<Model_LikeCount>();
        RetrofitService retrofitService = RetrofitClient.retrofit.create(RetrofitService.class);
        Call<LikeCountVO> call = retrofitService.getLikeData();
        call.enqueue(new Callback<LikeCountVO>() {
            @Override
            public void onResponse(Call<LikeCountVO> call, Response<LikeCountVO> response) {
                likerepo = response.body();
                likeTempList = likerepo.getList();
                for (int i = 0; i < likeTempList.size(); i++) {
                    if (likeTempList.get(i).getUrl().equals(getUrl)) {
                        String url = likeTempList.get(i).getUrl();
                        String id = likeTempList.get(i).getId();
                        if(likeTempList.get(i).getId().equals(BaseActivity.email))
                        {
                            dislikeIv.setVisibility(View.INVISIBLE);
                            dislikeIv.setClickable(false);
                            likeIv.setVisibility(View.VISIBLE);
                            likeIv.setClickable(true);
                        }
                        likeList.add(new Model_LikeCount(url, id));
                    }
                }

                if(likeList.size()==0) {
                    likeTv.setText(likeList.size()+" Likes");
                }
                for(int i=0; i<likeList.size();i++)
                {
                    if(likeList.get(i).getUrl().equals(getUrl))
                    {
                        likeTv.setText(likeList.size()+" Likes");
                    }
                }

            }

            @Override
            public void onFailure(Call<LikeCountVO> call, Throwable t) {

            }
        });
    }

    private void postMessage(String message,String getUrl,String getDate)
    {
        final String text= message;
        final String url =getUrl;
        final String date=getDate;
        final String id=BaseActivity.email;

        AndroidNetworking.post("http://13.124.87.34:3000/pviewreply")
                .addBodyParameter("id",id)
                .addBodyParameter("url",url)
                .addBodyParameter("text",text)
                .addBodyParameter("date",date)
                .addHeaders("Content-Type", "multipart/form-data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        commentList.add(new Model_Comment2(id,url,text,date));
                        adapter_comment = new Adapter_Comment2(getApplicationContext(), commentList);
                        rv_comment.setLayoutManager(manager);
                        rv_comment.setAdapter(adapter_comment);
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void DialogSimple(final String message , final String url){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
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
                        String geturl=url;

                        long now = System.currentTimeMillis();
                        java.util.Date date = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                        String nowTime=sdfNow.format(date);

                        postMessage(getMessage,geturl,nowTime);

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
            case R.id.best_reply_commentFAB:
                if(BaseActivity.isLogined == 0){
                    String message = "로그인 후 이용하세요.";
                    if(!BaseActivity.isKorean) {
                        message = "Please try after sign in";
                    }
                    new AlertDialog.Builder(Best_ReplyActivity.this).setTitle("Login").setMessage(message).setIcon(R.drawable.ic_logo).show();
                }else {
                    animateFAB();
                }
                break;
            case R.id.best_reply_commentBT:
                if (commentET.getText().toString().isEmpty()) {
                    String msg = "댓글을 입력하세요";
                    if (!BaseActivity.isKorean) msg = "Please enter message";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    DialogSimple(commentET.getText().toString(), url);
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
            InputMethodManager immhide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
