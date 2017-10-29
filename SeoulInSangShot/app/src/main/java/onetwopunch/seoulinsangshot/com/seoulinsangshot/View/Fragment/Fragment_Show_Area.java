package onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Area_DataManager;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.SelectActivity;

/**
 * Created by kwakgee on 2017. 9. 16..
 */

public class Fragment_Show_Area extends Fragment implements View.OnClickListener{


    private ImageButton[] imgButton=new ImageButton[25];

    private TextView txt_selected_name;
    private TextView txt_selected_name_en;
    private TextView txt_count_selected_photo;
    private TextView txt_count_selected_look;

    private LinearLayout ll_select_area;

    private ImageButton btn_goinside;
    List<Model_Test> selectedArea;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_area, container, false);


        imgButton[0]=(ImageButton)rootView.findViewById(R.id.db);
        imgButton[1]=(ImageButton)rootView.findViewById(R.id.ddm);
        imgButton[2]=(ImageButton)rootView.findViewById(R.id.dj);
        imgButton[3]=(ImageButton)rootView.findViewById(R.id.ep);
        imgButton[4]=(ImageButton)rootView.findViewById(R.id.ga);
        imgButton[5]=(ImageButton)rootView.findViewById(R.id.gb);
        imgButton[6]=(ImageButton)rootView.findViewById(R.id.gc);
        imgButton[7]=(ImageButton)rootView.findViewById(R.id.gd);
        imgButton[8]=(ImageButton)rootView.findViewById(R.id.gj);
        imgButton[9]=(ImageButton)rootView.findViewById(R.id.gn);
        imgButton[10]=(ImageButton)rootView.findViewById(R.id.gr);
        imgButton[11]=(ImageButton)rootView.findViewById(R.id.gs);
        imgButton[12]=(ImageButton)rootView.findViewById(R.id.jg);
        imgButton[13]=(ImageButton)rootView.findViewById(R.id.jr);
        imgButton[14]=(ImageButton)rootView.findViewById(R.id.jl);
        imgButton[15]=(ImageButton)rootView.findViewById(R.id.mp);
        imgButton[16]=(ImageButton)rootView.findViewById(R.id.nw);
        imgButton[17]=(ImageButton)rootView.findViewById(R.id.sb);
        imgButton[18]=(ImageButton)rootView.findViewById(R.id.sc);
        imgButton[19]=(ImageButton)rootView.findViewById(R.id.sd);
        imgButton[20]=(ImageButton)rootView.findViewById(R.id.sdm);
        imgButton[21]=(ImageButton)rootView.findViewById(R.id.sp);
        imgButton[22]=(ImageButton)rootView.findViewById(R.id.yc);
        imgButton[23]=(ImageButton)rootView.findViewById(R.id.ydp);
        imgButton[24]=(ImageButton)rootView.findViewById(R.id.ys);

        txt_selected_name=(TextView)rootView.findViewById(R.id.txt_selected_name);
        txt_selected_name_en=(TextView)rootView.findViewById(R.id.txt_selected_name_en);

        if(!BaseActivity.isKorean){
            txt_selected_name.setText("Yongsan");
            txt_selected_name_en.setText("용산구");
        }

        txt_count_selected_photo=(TextView)rootView.findViewById(R.id.txt_count_selected_photo);
        txt_count_selected_look=(TextView)rootView.findViewById(R.id.txt_count_selected_look);

        txt_count_selected_photo.setText(Area_DataManager.districtVOs.get(0).getImagecount() + "");
        txt_count_selected_look.setText(Area_DataManager.districtVOs.get(0).getHit() + "");

        for(int i=0;i<25;i++){
            imgButton[i].setTag(i);
            imgButton[i].setOnClickListener(this);
        }

        selectedArea=new ArrayList<>();
        for(int i=0; i<BaseActivity.testArr.size(); i++){
            if(BaseActivity.testArr.get(i).getArea().equals("ys")){
                selectedArea.add(BaseActivity.testArr.get(i));
            }
        }
        ll_select_area = (LinearLayout) rootView.findViewById(R.id.ll_select_area);
        ll_select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getContext(), SelectActivity.class);
                in.putExtra("imgList", (Serializable) selectedArea);
                startActivity(in);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {

        ImageButton newButton = (ImageButton) v;

        for (ImageButton tempButton : imgButton) {
            if (tempButton == newButton) {
                int position = v.getId();

                selectedArea=new ArrayList<>();

                String temp = getResources().getResourceName(position);

                String[] splitStr = temp.split("/");

                for(int i = 0; i< Area_DataManager.districtVOs.size(); i++){

                    if(Area_DataManager.districtVOs.get(i).getArea().equals(splitStr[1])) {

                        txt_selected_name.setText(Area_DataManager.districtVOs.get(i).getKarea());
                        txt_selected_name_en.setText(Area_DataManager.districtVOs.get(i).getEarea());

                        if(!BaseActivity.isKorean){
                            txt_selected_name.setText(Area_DataManager.districtVOs.get(i).getEarea());
                            txt_selected_name_en.setText(Area_DataManager.districtVOs.get(i).getKarea());
                            if(txt_selected_name.getText().toString().equals("Yeongdeungpo")){
                                txt_selected_name.setTextSize(20);
                            }else{
                                txt_selected_name.setTextSize(25);
                            }
                        }

                        txt_count_selected_photo.setText(Area_DataManager.districtVOs.get(i).getImagecount() + "");
                        txt_count_selected_look.setText(Area_DataManager.districtVOs.get(i).getHit() + "");

                    }
                }
                for(int j = 0; j< BaseActivity.testArr.size(); j++){
                    Model_Test imgInfo;
                    if(j==0){
                        if(splitStr[1].equals(BaseActivity.testArr.get(j).getArea())){

                            imgInfo=BaseActivity.testArr.get(j);
                            selectedArea.add(imgInfo);
                        }
                    }else{
                        if(!BaseActivity.testArr.get(j).getInitials().equals(BaseActivity.testArr.get(j-1).getInitials())){
                            if(splitStr[1].equals(BaseActivity.testArr.get(j).getArea())){
                                imgInfo=BaseActivity.testArr.get(j);
                                selectedArea.add(imgInfo);
                            }
                        }
                    }


                }
                ll_select_area.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){


                        Intent in=new Intent(getContext(), SelectActivity.class);
                        in.putExtra("imgList", (Serializable) selectedArea);
                        startActivity(in);
                    }
                });

            }
        }
    }
}
