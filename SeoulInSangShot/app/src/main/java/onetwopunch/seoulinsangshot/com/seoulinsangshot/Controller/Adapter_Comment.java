package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Comment;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

/**
 * Created by 301 on 2017-09-28.
 */

public class Adapter_Comment extends RecyclerView.Adapter<Adapter_Comment.ViewHolder> {

    List<Model_Comment> tempArr;
    Context adapterContext;

    public Adapter_Comment(Context context, List<Model_Comment> tempArr) {
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
