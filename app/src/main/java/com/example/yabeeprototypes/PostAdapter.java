package com.example.yabeeprototypes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    private Post[] listData;


    public PostAdapter(Post[] listData)
    {
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post myListData = listData[position];
        if (listData[position] != null) {
            String bid = "";
            DecimalFormat df = new DecimalFormat("#.##");
            holder.textView.setText(listData[position].getTitle());
            holder.imageView.setImageBitmap(listData[position].decodeImage());
            holder.timeLeft.setText(listData[position].getAuctionTimer());
            holder.condition.setText(listData[position].getCondition());

            if (listData[position].getLowestBid().price == listData[position].INITIAL_BID_PRICE)
                bid = "No bids placed yet!";
            else
                bid = "$" + df.format(listData[position].getLowestBid().price);

            holder.bid.setText(bid);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "click on item: " + myListData.getTitle(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView condition;
        public TextView bid;
        public TextView timeLeft;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.condition = (TextView) itemView.findViewById(R.id.condition);
            this.bid = (TextView) itemView.findViewById(R.id.bidPrice);
            this.timeLeft = (TextView) itemView.findViewById(R.id.timeLeft);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }



}
