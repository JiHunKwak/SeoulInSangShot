package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

public class TutorialActivity extends AppCompatActivity {

    private Button btn_tuto_next;
    private Button btn_tuto_pre;

    private ImageView iv_tuto_main;
    private ImageView iv_tuto_area;
    private ImageView iv_tuto_theme;
    private ImageView iv_tuto_detail;
    private ImageView iv_tuto_album;
    private ImageView iv_tuto_eng;

    private Animation translateLeft;
    private Animation translateRight;
    private Animation translateLeft2;
    private Animation translateRight2;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().hide();

        iv_tuto_main = (ImageView) findViewById(R.id.iv_tuto_main);
        iv_tuto_area = (ImageView) findViewById(R.id.iv_tuto_area);
        iv_tuto_theme = (ImageView) findViewById(R.id.iv_tuto_theme);
        iv_tuto_detail = (ImageView) findViewById(R.id.iv_tuto_detail);
        iv_tuto_album = (ImageView) findViewById(R.id.iv_tuto_album);
        iv_tuto_eng = (ImageView) findViewById(R.id.iv_tuto_eng);

        translateLeft = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRight = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        translateLeft2 = AnimationUtils.loadAnimation(this, R.anim.translate_left2);
        translateRight2 = AnimationUtils.loadAnimation(this, R.anim.translate_right2);

        count = 0;

        btn_tuto_next = (Button) findViewById(R.id.btn_tuto_next);
        btn_tuto_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count){
                    case 0:
                        count++;
                        iv_tuto_main.setVisibility(View.INVISIBLE);
                        iv_tuto_main.startAnimation(translateLeft2);
                        iv_tuto_area.startAnimation(translateLeft);
                        iv_tuto_area.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        count++;
                        iv_tuto_area.setVisibility(View.INVISIBLE);
                        iv_tuto_area.startAnimation(translateLeft2);
                        iv_tuto_theme.startAnimation(translateLeft);
                        iv_tuto_theme.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        count++;
                        iv_tuto_theme.setVisibility(View.INVISIBLE);
                        iv_tuto_theme.startAnimation(translateLeft2);
                        iv_tuto_detail.startAnimation(translateLeft);
                        iv_tuto_detail.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        count++;
                        iv_tuto_detail.setVisibility(View.INVISIBLE);
                        iv_tuto_detail.startAnimation(translateLeft2);
                        iv_tuto_album.startAnimation(translateLeft);
                        iv_tuto_album.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        count++;
                        String msg = "한번 더 터치하면 튜토리얼이 종료됩니다.";
                        if(!BaseActivity.isKorean) msg = "If you touch more, the tutorial terminate.";
                        Toast.makeText(TutorialActivity.this, msg, Toast.LENGTH_SHORT).show();
                        iv_tuto_album.setVisibility(View.INVISIBLE);
                        iv_tuto_album.startAnimation(translateLeft2);
                        iv_tuto_eng.startAnimation(translateLeft);
                        iv_tuto_eng.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        finish();
                        break;
                }
            }
        });

        btn_tuto_pre = (Button) findViewById(R.id.btn_tuto_pre);
        btn_tuto_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count){
                    case 0:
                        break;
                    case 1:
                        count--;
                        iv_tuto_area.setVisibility(View.INVISIBLE);
                        iv_tuto_area.startAnimation(translateRight);
                        iv_tuto_main.startAnimation(translateRight2);
                        iv_tuto_main.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        count--;
                        iv_tuto_theme.setVisibility(View.INVISIBLE);
                        iv_tuto_theme.startAnimation(translateRight);
                        iv_tuto_area.startAnimation(translateRight2);
                        iv_tuto_area.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        count--;
                        iv_tuto_detail.setVisibility(View.INVISIBLE);
                        iv_tuto_detail.startAnimation(translateRight);
                        iv_tuto_theme.startAnimation(translateRight2);
                        iv_tuto_theme.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        count--;
                        iv_tuto_album.setVisibility(View.INVISIBLE);
                        iv_tuto_album.startAnimation(translateRight);
                        iv_tuto_detail.startAnimation(translateRight2);
                        iv_tuto_detail.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        count--;
                        iv_tuto_eng.setVisibility(View.INVISIBLE);
                        iv_tuto_eng.startAnimation(translateRight);
                        iv_tuto_album.startAnimation(translateRight2);
                        iv_tuto_album.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
