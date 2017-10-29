package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.SubjectVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.SelectActivity;

/**
 * Created by MG_PARK on 2017-09-17.
 */

public class Adapter_theme extends RecyclerView.Adapter<Adapter_theme.ViewHolder> {


    List<SubjectVO> theme;
    Context context;

    List<Model_Test> selectedTheme;

    public Adapter_theme(List<SubjectVO> list){
        theme=list;
        context=null;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_theme;
        public TextView txt_theme;
        public CardView card;


        public ViewHolder(View view) {
            super(view);

            img_theme=(ImageView)view.findViewById(R.id.img_theme);
            txt_theme=(TextView)view.findViewById(R.id.txt_theme);
            card=(CardView)view.findViewById(R.id.cardView);

        }
    }

    @Override
    public Adapter_theme.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme,parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(Adapter_theme.ViewHolder holder, final int position) {

        Picasso.with(context)
                .load(theme.get(position).getUrl())
                .into(holder.img_theme);


        holder.txt_theme.setText("#"+theme.get(position).getThemename());
        if(!BaseActivity.isKorean) holder.txt_theme.setText("#"+theme.get(position).getEthemename());

        holder.card.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                selectedTheme=new ArrayList<>();

                String strTheme=theme.get(position).getThemename();
                if(!BaseActivity.isKorean) strTheme=theme.get(position).getEthemename();

                for(int j = 0; j< BaseActivity.testArr.size(); j++){
                    Model_Test imgInfo;

                    if(j==0) {
                        if (strTheme.equals(BaseActivity.testArr.get(j).getTheme1()) || strTheme.equals(BaseActivity.testArr.get(j).getTheme2())) {
                            imgInfo = BaseActivity.testArr.get(j);
                            selectedTheme.add(imgInfo);
                        }
                    }else {
                        if (!BaseActivity.testArr.get(j).getInitials().equals(BaseActivity.testArr.get(j - 1).getInitials())) {
                            if (strTheme.equals(BaseActivity.testArr.get(j).getTheme1()) || strTheme.equals(BaseActivity.testArr.get(j).getTheme2())) {
                                imgInfo = BaseActivity.testArr.get(j);
                                selectedTheme.add(imgInfo);
                            }
                        }
                    }

                }

                Intent in=new Intent(context, SelectActivity.class);
                in.putExtra("imgList", (Serializable) selectedTheme);
                context.startActivity(in);

            }
        });

    }

    @Override
    public int getItemCount() {
        return theme.size();
    }
}
