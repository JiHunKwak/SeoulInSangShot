package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.oliveiradev.image_zoom.lib.widget.ImageZoom;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Detail;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

/**
 * Created by kwakgee on 2017. 9. 17..
 */

public class Adapter_Detail extends RecyclerView.Adapter<Adapter_Detail.ViewHolder>{

    List<Model_Detail> tempArr;
    Context adapterContext;
    public Adapter_Detail(Context context , List<Model_Detail> tempArr) {
        this.adapterContext=context;
        this.tempArr = tempArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(adapterContext).load(tempArr.get(position).getUrl()).into(holder.iv_detail_main);

        if(tempArr.size()>1) {
            for (int i = 0; i < tempArr.size(); i++) {
                ImageView index = holder.detail_indexes.get(i);
                if (i == position) {
                    index.setVisibility(View.VISIBLE);
                    index.setImageResource(R.drawable.indexafter);
                } else {
                    index.setVisibility(View.VISIBLE);
                    index.setImageResource(R.drawable.indexbefore);
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return tempArr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageZoom iv_detail_main;
        public ArrayList<ImageView> detail_indexes=new ArrayList<>();
        public ViewHolder(View itemView){
            super(itemView);

            iv_detail_main = (ImageZoom) itemView.findViewById(R.id.iv_detail_main);

            detail_indexes.add((ImageView)itemView.findViewById(R.id.detail_index1));
            detail_indexes.add((ImageView)itemView.findViewById(R.id.detail_index2));
            detail_indexes.add((ImageView)itemView.findViewById(R.id.detail_index3));
            detail_indexes.add((ImageView)itemView.findViewById(R.id.detail_index4));
            detail_indexes.add((ImageView)itemView.findViewById(R.id.detail_index5));


        }


    }

}