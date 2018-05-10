package com.quang.lilianachat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.lilianachat.R;
import com.quang.lilianachat.model.UserInfo;

import java.util.ArrayList;

public class ReactAdapter extends RecyclerView.Adapter<ReactAdapter.ViewHolder> {

    private ArrayList<UserInfo> listUser;

    public ReactAdapter(ArrayList<UserInfo> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank_react, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(listUser.get(position).getName());
        holder.tvHeart.setText(listUser.get(position).getHeart() + "");
        holder.tvAngry.setText(listUser.get(position).getAngry() + "");
        Glide.with(holder.itemView).load(R.drawable.heart).into(holder.imvHeart);
        Glide.with(holder.itemView).load(R.drawable.angry).into(holder.imvAngry);
        Glide.with(holder.itemView).load("https://graph.facebook.com/" + listUser.get(position).getIdFacebook() + "/picture?type=large").into(holder.imvAvatar);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvHeart, tvAngry;
        ImageView imvHeart, imvAngry, imvAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvHeart = itemView.findViewById(R.id.tvHeart);
            tvAngry = itemView.findViewById(R.id.tvAngry);
            imvHeart = itemView.findViewById(R.id.imvHeart);
            imvAngry = itemView.findViewById(R.id.imvAngry);
            imvAvatar = itemView.findViewById(R.id.imvAvatar);
        }
    }
}
