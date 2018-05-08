package com.quang.lilianachat.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quang.lilianachat.R;
import com.quang.lilianachat.activity.ChatFriendActivity;
import com.quang.lilianachat.model.UserInfo;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<UserInfo> listUser;

    public SearchAdapter(ArrayList<UserInfo> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvName.setText(listUser.get(position).getName());
        holder.tvStatus.setText(listUser.get(position).getStatus());
        Glide
                .with(holder.itemView)
                .load("https://graph.facebook.com/" + listUser.get(position).getIdFacebook() + "/picture")
                .into(holder.imvAvatar);
        holder.btnChatNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ChatFriendActivity.class);
                intent.putExtra("idFirebase", listUser.get(holder.getAdapterPosition()).getIdFirebase());
                intent.putExtra("idFacebook", listUser.get(holder.getAdapterPosition()).getIdFacebook());
                intent.putExtra("name", listUser.get(holder.getAdapterPosition()).getName());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStatus;
        ImageView imvAvatar;
        Button btnChatNow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imvAvatar = itemView.findViewById(R.id.imvAvatar);
            btnChatNow = itemView.findViewById(R.id.btnChatNow);
        }
    }
}
