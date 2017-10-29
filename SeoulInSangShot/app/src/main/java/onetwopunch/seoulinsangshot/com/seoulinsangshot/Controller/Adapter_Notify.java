package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Notify;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.BaseActivity;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.NotifyDetailActivity;

/**
 * Created by 301-29 on 2017-10-22.
 */

public class Adapter_Notify extends RecyclerView.Adapter<Adapter_Notify.ViewHolder> {
    List<Model_Notify> notifyList;
    Context context;
    public Adapter_Notify(List<Model_Notify> notifyList, Context context){
        this.notifyList= notifyList;
        this.context=context;

    }


    @Override
    public Adapter_Notify.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false));
    }

    @Override
    public void onBindViewHolder(Adapter_Notify.ViewHolder holder, final int position) {
        holder.topic.setText(notifyList.get(position).getTopic());
        holder.date.setText(notifyList.get(position).getDate());
        String msg = "관리자";
        if(!BaseActivity.isKorean) msg = "Admin";
        holder.manager.setText(msg);
        holder.card.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, NotifyDetailActivity.class);
                intent.putExtra("topic",notifyList.get(position).getTopic());
                intent.putExtra("text",notifyList.get(position).getText());
                setIntentFlag(intent);
                context.startActivity(intent);
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
        return notifyList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView topic;
        private TextView manager;
        private TextView date;
        public CardView card;
        public ViewHolder(View itemView) {
            super(itemView);
            topic=(TextView)itemView.findViewById(R.id.topic);
            date=(TextView)itemView.findViewById(R.id.date);
            manager=(TextView)itemView.findViewById(R.id.manager);
            card=(CardView)itemView.findViewById(R.id.card_notify);
        }
    }

}
