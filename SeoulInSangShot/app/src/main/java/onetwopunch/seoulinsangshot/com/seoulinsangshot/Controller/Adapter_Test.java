package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Test;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.DetailActivity;

/**
 * Created by kwakgee on 2017. 9. 24..
 */

public class Adapter_Test extends RecyclerView.Adapter<Adapter_Test.ViewHolder> {

    List<Model_Test> tempArr;
    Context context;
    Intent detail;

    public Adapter_Test(List<Model_Test> tempArr, Context context) {
        this.tempArr = tempArr;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        detail = new Intent(context, DetailActivity.class);

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context).load(tempArr.get(position).getUrl()).into(holder.iv_select);
        holder.txt_name.setText(tempArr.get(position).getName());
        holder.txt_theme1.setText(tempArr.get(position).getTheme1());
        holder.txt_theme2.setText(tempArr.get(position).getTheme2());
        holder.txt_count_photo.setText(tempArr.get(position).getHit()+"");


        holder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detail.putExtra("area", tempArr.get(position).getArea());
                detail.putExtra("initials", tempArr.get(position).getInitials());
                detail.putExtra("name", tempArr.get(position).getName());
                detail.putExtra("ename", tempArr.get(position).getEname());
                detail.putExtra("lat", tempArr.get(position).getLat());
                detail.putExtra("lng", tempArr.get(position).getLng());
                detail.putExtra("subway", tempArr.get(position).getSubway());
                detail.putExtra("bus", tempArr.get(position).getBus());
                detail.putExtra("bicycle", tempArr.get(position).getBicycle());
                detail.putExtra("url", tempArr.get(position).getUrl());
                detail.putExtra("smartPhone", tempArr.get(position).getSmartPhone());
                detail.putExtra("filter", tempArr.get(position).getFilter());
                detail.putExtra("theme1", tempArr.get(position).getTheme1());
                detail.putExtra("theme2", tempArr.get(position).getTheme2());
                detail.putExtra("time", tempArr.get(position).getTime());
                detail.putExtra("tip", tempArr.get(position).getTip());
                detail.putExtra("info",tempArr.get(position).getInfo());

                setIntentFlag(detail);
                context.startActivity(detail);

            }
        });

    }

    public void setIntentFlag(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    @Override
    public int getItemCount() {
        return tempArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_select;
        public TextView txt_name;
        public TextView txt_theme1;
        public TextView txt_theme2;
        public TextView txt_count_photo;



        public ViewHolder(View itemView) {
            super(itemView);

            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            txt_name=(TextView)itemView.findViewById(R.id.txt_image_name);
            txt_theme1=(TextView)itemView.findViewById(R.id.txt_theme);
            txt_theme2=(TextView)itemView.findViewById(R.id.txt_theme2);
            txt_count_photo=(TextView)itemView.findViewById(R.id.txt_count_photo);

        }
    }

}