package com.example.yabeeprototypes;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.ViewHolder>
{
    private List<Bid> listData;
    private int containerId;
    private String postID;

    public BidAdapter(List<Bid> listData, int containerId, String postID)
    {
        this.listData = listData;
        this.containerId = containerId;
        this.postID = postID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pendingbid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Bid myListData = listData.get(position);
        if (myListData != null)
        {
            String price = "$" + listData.get(position).getPrice();
            String description = listData.get(position).getDescription();
            System.out.println(description);
            holder.bidPrice.setText(price);
            holder.bidDescription.setText(description);
            holder.bidPhoto.setImageBitmap(listData.get(position).decodeImage());
        }
        else
        {
            String invalid = "null";
            holder.bidPrice.setText(invalid);
            holder.bidDescription.setText(invalid);
        }

        holder.relativeLayoutForPendingBids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListData != null) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Intent intense = new Intent(activity, BidAccept.class);
                    Bundle b = new Bundle();

                    b.putDouble("BID PRICE", myListData.getPrice());
                    b.putString("BID DESC", myListData.getDescription());
                    b.putString("IMAGE STRING", myListData.getImageEncoding());
                    b.putString("USER ID", myListData.getSeller().getUid());
                    b.putString("USER EMAIL", myListData.getSeller().getEmail());
                    b.putString("POST ID", postID);

                    intense.putExtras(b);
                    activity.startActivity(intense);
                    activity.finish();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bidPrice;
        public TextView bidDescription;
        public ImageView bidPhoto;
        public RelativeLayout relativeLayoutForPendingBids;
        public ViewHolder(View itemView) {
            super(itemView);
            this.bidPrice = (TextView) itemView.findViewById(R.id.pendingBidPrice);
            this.bidDescription = (TextView) itemView.findViewById(R.id.pendingBidDescription);
            this.relativeLayoutForPendingBids = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutForPendingBids);
            this.bidPhoto = (ImageView) itemView.findViewById(R.id.pendingBidImage);
        }
    }



}
