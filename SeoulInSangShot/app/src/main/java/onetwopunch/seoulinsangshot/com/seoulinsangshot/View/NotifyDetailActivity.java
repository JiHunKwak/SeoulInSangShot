package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

public class NotifyDetailActivity extends AppCompatActivity {
    Intent home;
    Intent primary;
    Intent album;
    Intent notification;

    TextView topicView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_detail);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        topicView=(TextView)findViewById(R.id.notifyTopic);
        textView=(TextView)findViewById(R.id.notfiyText);
        String topic=getIntent().getExtras().getString("topic");
        String text=getIntent().getExtras().getString("text");
        topicView.setText(topic);
        textView.setText(text);

        home = new Intent(getApplicationContext(), MainActivity.class);
        primary = new Intent(getApplicationContext(), PrimaryActivity.class);
        album = new Intent(getApplicationContext(), AlbumActivity.class);

    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }
}
