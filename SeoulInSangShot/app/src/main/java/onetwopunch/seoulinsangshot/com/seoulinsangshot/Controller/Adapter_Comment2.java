package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Comment2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

/**
 * Created by 301 on 2017-10-21.
 */

public class Adapter_Comment2 extends RecyclerView.Adapter<Adapter_Comment2.ViewHolder> {

    List<Model_Comment2> tempArr;
    Context adapterContext;

    public Adapter_Comment2(Context context, List<Model_Comment2> tempArr) {
        this.adapterContext = context;
        this.tempArr = tempArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.commentId.setText("#"+tempArr.get(position).getId());
        holder.comment.setText(tempArr.get(position).getText());
        holder.commentDate.setText(tempArr.get(position).getDate());

    }
    @Override
    public int getItemCount() {
        return tempArr.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView commentId;
        public TextView comment;
        public TextView commentDate;


        public ViewHolder(View itemView){
            super(itemView);

            commentId= (TextView) itemView.findViewById(R.id.commenId);
            comment=(TextView)itemView.findViewById(R.id.comment);
            commentDate=(TextView)itemView.findViewById(R.id.commentDate);


        }

    }

}