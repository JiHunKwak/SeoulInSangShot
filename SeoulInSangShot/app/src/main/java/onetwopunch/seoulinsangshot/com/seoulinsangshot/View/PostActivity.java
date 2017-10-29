package onetwopunch.seoulinsangshot.com.seoulinsangshot.View;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller.Constants;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Image;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

import static onetwopunch.seoulinsangshot.com.seoulinsangshot.R.array.season;

public class PostActivity extends AppCompatActivity {

    Intent home;
    Intent externalAlbum;

    ImageView iv_select_pic;
    EditText edt_content;
    EditText edt_device;
    Spinner spn_device;
    EditText edt_filter;
    Spinner spn_season;
    Spinner spn_theme;
    Button btn_upload;
    LinearLayout ll_posting;

    String path;

    static String seasonTemp;
    static String simpleTimeTemp;

    List<Integer> idList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Drawable d = getResources().getDrawable(R.drawable.actionbar);
        getSupportActionBar().setBackgroundDrawable(d);
        getSupportActionBar().setTitle("");

        home = new Intent(this, MainActivity.class);

        idList = new ArrayList<>();

        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setEnabled(false);

        ll_posting = (LinearLayout) findViewById(R.id.ll_posting);

        iv_select_pic = (ImageView) findViewById(R.id.iv_select_pic);

        if(!BaseActivity.isKorean) iv_select_pic.setImageResource(R.drawable.sel_pic_e);

        iv_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                externalAlbum = new Intent(Intent.ACTION_PICK);
                externalAlbum.setType("image/jpeg");
                try{
                    startActivityForResult(externalAlbum, 100);
                }catch (Exception e){

                }
                btn_upload.setEnabled(true);
            }
        });

        edt_content = (EditText) findViewById(R.id.edt_content);

        edt_device = (EditText) findViewById(R.id.edt_device);

        spn_device = (Spinner) findViewById(R.id.spn_device);
        edt_device.setText(String.valueOf(spn_device.getItemAtPosition(0)));
        edt_device.setClickable(false);
        edt_device.setEnabled(false);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        String[] strArr1 = getResources().getStringArray(R.array.device_type);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strArr1);
        spn_device.setAdapter(adapter1);

        final String composeTemp;
        if(BaseActivity.isKorean) composeTemp = "기타";
        else composeTemp = "Etc";

        spn_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(18);
                if(!String.valueOf(spn_device.getItemAtPosition(i)).equals(composeTemp)){
                    edt_device.setText(String.valueOf(spn_device.getItemAtPosition(i)));
                    edt_device.setClickable(false);
                    edt_device.setEnabled(false);
                } else {
                    edt_device.setText("");
                    edt_device.setClickable(true);
                    edt_device.setEnabled(true);
                    edt_device.requestFocus();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edt_filter = (EditText) findViewById(R.id.edt_filter);

        spn_season = (Spinner) findViewById(R.id.spn_season);
        String[] strArr2 = getResources().getStringArray(season);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strArr2);
        spn_season.setAdapter(adapter2);
        spn_season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(18);
                seasonTemp = String.valueOf(spn_season.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_theme = (Spinner) findViewById(R.id.spn_theme);
        String[] strArr3 = getResources().getStringArray(R.array.theme);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strArr3);
        spn_theme.setAdapter(adapter3);
        spn_theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(18);
                simpleTimeTemp = String.valueOf(spn_theme.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();
                java.util.Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                final String formatDate = sdfNow.format(cal.getTime());
                final String id =  BaseActivity.email;
                final String area = getIntent().getStringExtra("initials");
                final String content = edt_content.getText().toString();
                final String photoName = String.valueOf(createRandomId());
                final String deviceType = edt_device.getText().toString();
                final String filter = edt_filter.getText().toString();
                final String season = seasonTemp;
                final String simpleTime = simpleTimeTemp;

                AlertDialog.Builder ad = new AlertDialog.Builder(PostActivity.this);
                final AlertDialog dialog = ad.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.YELLOW);
                    }
                });

                String msgTemp = "내용 : " + content + "\n" + "기종 : " + deviceType + "\n" + "필터 : " + filter + "\n"
                        + "계절 : " + season + "\n" + "테마 : " + simpleTime + "\n" + "확실합니까?";
                if(!BaseActivity.isKorean) msgTemp = "Content : " + content + "\n" + "Smartphone : " + deviceType + "\n" + "Filter : " + filter + "\n"
                        + "Season : " + season + "\n" + "Theme : " + simpleTime + "\n" + "Correct?";

                ad.setMessage(msgTemp);
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ll_posting.setVisibility(View.VISIBLE);
                        iv_select_pic.buildDrawingCache();
                        Bitmap bitmap = iv_select_pic.getDrawingCache();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,50,out);
                        byte[] b = out.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        AndroidNetworking.post(Constants.TEST_BASE_URL + "tupload")
                                .addBodyParameter("base64", encodedImage)
                                .addBodyParameter("pic_id", id)
                                .addBodyParameter("area", area)
                                .addBodyParameter("photoName", photoName)
                                .addBodyParameter("phoneType", deviceType)
                                .addBodyParameter("phoneApp", filter)
                                .addBodyParameter("season", season)
                                .addBodyParameter("time", simpleTime)
                                .addBodyParameter("tip", content)
                                .addBodyParameter("nowdate", formatDate)
                                .addHeaders("Content-Type", "multipart/form-data")
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String msg = "게시글 등록이 완료되었습니다.";
                                        if(!BaseActivity.isKorean) msg = "The posting was completed.";
                                        Toast.makeText(PostActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        DetailActivity.bestList.add(new Model_Best(id,area,Constants.req_URL+"user/"+photoName+".png",deviceType,filter,season,simpleTime,content,formatDate));
                                        BaseActivity.imageList.add(new Model_Image(Constants.req_URL+"user/"+photoName+".png", id, content, formatDate,area,filter,deviceType, simpleTime));
                                        ll_posting.setVisibility(View.INVISIBLE);
                                        finish();
                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        String msg = "서버와 연결에 실패했습니다.";
                                        if(!BaseActivity.isKorean) msg = "Connecting with server fail.";
                                        Toast.makeText(PostActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        Log.v("Error", error.toString());
                                        ll_posting.setVisibility(View.INVISIBLE);

                                    }
                                });
                    }
                });
                ad.setNegativeButton("No", null);
                ad.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(ll_posting.getVisibility() == View.INVISIBLE){
            finish();
        }else{
            String msg = "업로드 중입니다. 잠시만 기다리세요.";
            if(!BaseActivity.isKorean) msg = "Please wait for uploading.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public int createRandomId(){

        boolean overlap = false;
        int randNumber = 0;

        while(true){
            overlap = false;
            randNumber = (int)((Math.random() * (99999999 - 10000000 + 1)) + 10000000);
            for(int i=0; i<idList.size(); i++){
                if(randNumber == idList.get(i)){
                    overlap = true;
                }
            }
            if(!overlap){
                idList.add(randNumber);
                break;
            }
        }
        return randNumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)
            return;
        switch (requestCode){
            case 100:
                if(resultCode == RESULT_OK){
                    if(String.valueOf(data.getData()).contains("video")){
                        String msg = "동영상은 선택할 수 없습니다.";
                        if(!BaseActivity.isKorean) msg = "You can't choose a movie file.";
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        btn_upload.setEnabled(false);
                    }else {
                        path = getPathFromURI(data.getData());
                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(path);
                        } catch (IOException e) {
                            Log.v("this", e.toString());
                        }

                        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        int exifDegree = exifOrientationToDegrees(exifOrientation);

                        Bitmap bitmap = BitmapFactory.decodeFile(path);

                        iv_select_pic.setImageBitmap(rotate(bitmap, exifDegree));
                    }
                }
        }
    }

    private String getPathFromURI(Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }return 0;
    }

    public Bitmap rotate(Bitmap bitmap, float degrees){

        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

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
