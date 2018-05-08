package com.quang.lilianachat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quang.lilianachat.R;
import com.quang.lilianachat.model.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Message> listMessage;
    private FirebaseUser curUser;

    public MessageAdapter(ArrayList<Message> listMessage) {
        this.listMessage = listMessage;
        curUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == 1)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMessage.setText(listMessage.get(position).getMessage());
        if (getItemViewType(position) == 0) {
            TextView tvName = holder.itemView.findViewById(R.id.tvName);
            ImageView imvAvatar = holder.itemView.findViewById(R.id.imvAvatar);
            tvName.setText(listMessage.get(position).getName());
            Glide.with(holder.itemView).load(listMessage.get(position).getLinkAvatar()).into(imvAvatar);
            RelativeTimeTextView tvTimeSend = holder.itemView.findViewById(R.id.tvTimeSend); //Or just use Butterknife!
            tvTimeSend.setReferenceTime(listMessage.get(position).getTimeSend());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (curUser.getUid().equals(listMessage.get(position).getIdFirebase())) return 1;
        return 0;
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage;

        ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
