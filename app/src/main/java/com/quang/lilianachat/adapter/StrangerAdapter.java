package com.quang.lilianachat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quang.lilianachat.R;
import com.quang.lilianachat.model.UserInfo;

import java.util.ArrayList;

public class StrangerAdapter extends RecyclerView.Adapter<StrangerAdapter.ViewHolder> {

    private ArrayList<UserInfo> listUser;

    public StrangerAdapter(ArrayList<UserInfo> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank_stranger, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(listUser.get(position).getName());
        holder.tvStranger.setText(listUser.get(position).getCountStranger() + "");
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStranger;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStranger = itemView.findViewById(R.id.tvStranger);
        }
    }
}
