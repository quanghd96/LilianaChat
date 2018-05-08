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
import com.quang.lilianachat.model.UserOnline;

import java.util.ArrayList;

public class UserOnlineAdapter extends RecyclerView.Adapter<UserOnlineAdapter.ViewHolder> {

    private ArrayList<UserOnline> listUserOnline;

    public UserOnlineAdapter(ArrayList<UserOnline> listUserOnline) {
        this.listUserOnline = listUserOnline;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_online, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(listUserOnline.get(position).getName());
        Glide.with(holder.itemView).load("https://graph.facebook.com/" +
                listUserOnline.get(position).getFacebookId() + "/picture?width=300").into(holder.imvAvatar);
    }

    @Override
    public int getItemCount() {
        return listUserOnline.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvAvatar;
        TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            imvAvatar = itemView.findViewById(R.id.imvAvatar);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
