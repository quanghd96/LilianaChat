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

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private ArrayList<GameItem> listGame;

    public GameAdapter() {
        listGame = new ArrayList<>();
        listGame.add(new GameItem(R.drawable.candy, "Candy Crush Saga", "King"));
        listGame.add(new GameItem(R.drawable.leps, "Lep's World 2 \uD83C\uDF40\uD83C\uDF40", "nerByte GmbH"));
        listGame.add(new GameItem(R.drawable.slither, "slither.io", "Lowtech Studios"));
        listGame.add(new GameItem(R.drawable.tom, "My Talking Tom", "Outfit7"));
        listGame.add(new GameItem(R.drawable.zombie, "Zombie Tsunami", "Mobigame S.A.R.L."));
        listGame.add(new GameItem(R.drawable.temple, "Temple Run 2", "Imangi Studios"));
        listGame.add(new GameItem(R.drawable.subway, "Subway Surfers", "Killo"));listGame.add(new GameItem(R.drawable.candy, "Candy Crush Saga", "King"));
        listGame.add(new GameItem(R.drawable.leps, "Lep's World 2 \uD83C\uDF40\uD83C\uDF40", "nerByte GmbH"));
        listGame.add(new GameItem(R.drawable.slither, "slither.io", "Lowtech Studios"));
        listGame.add(new GameItem(R.drawable.tom, "My Talking Tom", "Outfit7"));
        listGame.add(new GameItem(R.drawable.zombie, "Zombie Tsunami", "Mobigame S.A.R.L."));
        listGame.add(new GameItem(R.drawable.temple, "Temple Run 2", "Imangi Studios"));
        listGame.add(new GameItem(R.drawable.subway, "Subway Surfers", "Killo"));listGame.add(new GameItem(R.drawable.candy, "Candy Crush Saga", "King"));
        listGame.add(new GameItem(R.drawable.leps, "Lep's World 2 \uD83C\uDF40\uD83C\uDF40", "nerByte GmbH"));
        listGame.add(new GameItem(R.drawable.slither, "slither.io", "Lowtech Studios"));
        listGame.add(new GameItem(R.drawable.tom, "My Talking Tom", "Outfit7"));
        listGame.add(new GameItem(R.drawable.zombie, "Zombie Tsunami", "Mobigame S.A.R.L."));
        listGame.add(new GameItem(R.drawable.temple, "Temple Run 2", "Imangi Studios"));
        listGame.add(new GameItem(R.drawable.subway, "Subway Surfers", "Killo"));
        listGame.add(new GameItem(R.drawable.candy, "Candy Crush Saga", "King"));
        listGame.add(new GameItem(R.drawable.leps, "Lep's World 2 \uD83C\uDF40\uD83C\uDF40", "nerByte GmbH"));
        listGame.add(new GameItem(R.drawable.slither, "slither.io", "Lowtech Studios"));
        listGame.add(new GameItem(R.drawable.tom, "My Talking Tom", "Outfit7"));
        listGame.add(new GameItem(R.drawable.zombie, "Zombie Tsunami", "Mobigame S.A.R.L."));
        listGame.add(new GameItem(R.drawable.temple, "Temple Run 2", "Imangi Studios"));
        listGame.add(new GameItem(R.drawable.subway, "Subway Surfers", "Killo"));
    }

    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(listGame.get(position).getName());
        holder.tvAuthor.setText(listGame.get(position).getAuthor());
        Glide.with(holder.itemView).load(listGame.get(position).getIcon()).into(holder.imvIcon);
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAuthor;
        ImageView imvIcon;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            imvIcon = itemView.findViewById(R.id.imvIcon);
        }
    }

    class GameItem {
        private int icon;
        private String name;
        private String author;

        public GameItem() {
        }

        GameItem(int icon, String name, String author) {
            this.icon = icon;
            this.name = name;
            this.author = author;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
