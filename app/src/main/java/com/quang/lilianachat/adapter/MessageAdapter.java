package com.quang.lilianachat.adapter;

import android.content.Intent;
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
import com.quang.lilianachat.activity.ReactActivity;
import com.quang.lilianachat.activity.ViewImageActivity;
import com.quang.lilianachat.model.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Message> listMessage;
    private FirebaseUser curUser;
    private OnItemClickListener mItemClickListener;

    public MessageAdapter(ArrayList<Message> listMessage) {
        this.listMessage = listMessage;
        curUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == 0)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right, parent, false);
        else if (viewType == 1)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_image_right, parent, false);
        else if (viewType == 2)
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left, parent, false);
        else
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_image_left, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (listMessage.get(position).getImage() == null) {
            TextView tvMessage = holder.itemView.findViewById(R.id.tvMessage);
            tvMessage.setText(listMessage.get(position).getMessage());
        } else {
            ImageView imvImage = holder.itemView.findViewById(R.id.imvImage);
            imvImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ReactActivity.class));
                    return true;
                }
            });
            imvImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), ViewImageActivity.class);
                    intent.putExtra("urlImage", listMessage.get(holder.getAdapterPosition()).getImage());
                    intent.putExtra("name", listMessage.get(holder.getAdapterPosition()).getName());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
            Glide.with(holder.itemView).load(listMessage.get(position).getImage()).into(imvImage);
        }
        if (getItemViewType(position) >= 2) {
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
        if (curUser.getUid().equals(listMessage.get(position).getIdFirebase())) {
            if (listMessage.get(position).getImage() == null)
                return 0;
            return 1;
        }
        if (listMessage.get(position).getImage() == null)
            return 2;
        return 3;
    }

    @Override
    public int getItemCount() {
        return listMessage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
