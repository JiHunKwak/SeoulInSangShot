package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Test_DataManager;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

public class LoadingActivity extends AppCompatActivity {

    public static boolean loadingFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        Test_DataManager test_dataManager = new Test_DataManager();
        test_dataManager.loadData(this);
    }
}
