package onetwopunch.seoulinsangshot.com.seoulinsangshot.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Best2;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Best_ReplyActivity;

/**
 * Created by Beom2 on 2017-10-06.
 */

public class Adapter_Best extends RecyclerView.Adapter<Adapter_Best.ViewHolder> {

    List<Model_Best> tempArr;
    List<Model_Best2> tempArr2;
    Context adapterContext;

    public Adapter_Best(Context context, List<Model_Best> tempArr, List<Model_Best2> tempArr2) {
        this.adapterContext = context;
        this.tempArr = tempArr;
        this.tempArr2 = tempArr2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.bestID.setText(tempArr.get(position).getId());
        for (int i = 0; i < tempArr2.size(); i++) {
            if (tempArr.get(position).getUrl().equals(tempArr2.get(i).getUrl())) {
                holder.bestLike.setText(tempArr2.get(i).getLikes() + " Likes");
            }
        }

        Picasso
                .with(adapterContext)
                .load(tempArr.get(position).getUrl())
                .into(holder.bestImage);

        holder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent in = new Intent(adapterContext, Best_ReplyActivity.class);

                in.putExtra("email", tempArr.get(position).getId());
                in.putExtra("tip", tempArr.get(position).getTip());
                in.putExtra("phoneType", tempArr.get(position).getPhoneType());
                in.putExtra("phoneApp", tempArr.get(position).getPhoneApp());
                in.putExtra("image", tempArr.get(position).getUrl());
                in.putExtra("url", tempArr.get(position).getUrl());
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) adapterContext,

                        new Pair<View, String>(view.findViewById(R.id.bestImageView),
                                "image"));

                setIntentFlag(in);
                adapterContext.startActivity(in, activityOptions.toBundle());


            }

        });
    }

    public void setIntentFlag(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    @Override
    public int getItemCount() {
        return tempArr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bestID;
        public TextView bestLike;
        public ImageView bestImage;
        public CardView card;


        public ViewHolder(View itemView) {
            super(itemView);

            bestID = (TextView) itemView.findViewById(R.id.bestID_TextView);
            bestLike = (TextView) itemView.findViewById(R.id.bestLIKE_TextView);
            bestImage = (ImageView) itemView.findViewById(R.id.bestImageView);

            card = (CardView) itemView.findViewById(R.id.card_best);

        }

    }
}
