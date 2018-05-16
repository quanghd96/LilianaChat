package com.quang.lilianachat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.quang.lilianachat.R;
import com.quang.lilianachat.model.ItemChat;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {

    private ArrayList<ItemChat> listChat;
    private OnItemClickListener mItemClickListener;

    public ListChatAdapter(ArrayList<ItemChat> listChat) {
        this.listChat = listChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0)
            Glide.with(holder.itemView).load(R.drawable.ic_chat_room).into(holder.imvIcon);
        else if (position == 1)
            Glide.with(holder.itemView).load(R.drawable.ic_chat_with_person).into(holder.imvIcon);
        else
            Glide.with(holder.itemView).load( listChat.get(position).getIcon()).into(holder.imvIcon);

        holder.tvName.setText(listChat.get(position).getName());
        holder.tvLastMessage.setText(listChat.get(position).getLastMessage());
        holder.tvTime.setReferenceTime(listChat.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView imvIcon;
        TextView tvName, tvLastMessage;
        RelativeTimeTextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            imvIcon = itemView.findViewById(R.id.imvIcon);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
