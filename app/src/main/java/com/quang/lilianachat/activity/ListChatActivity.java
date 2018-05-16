package com.quang.lilianachat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.ListChatAdapter;
import com.quang.lilianachat.model.ItemChat;

import java.util.ArrayList;

public class ListChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseUser curUser;
    private ArrayList<ItemChat> listChat;
    private ListChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("chat_list");

        curUser = FirebaseAuth.getInstance().getCurrentUser();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView rvListChat = findViewById(R.id.rvListChat);
        rvListChat.setLayoutManager(new LinearLayoutManager(this));
        listChat = new ArrayList<>();
        listChat.add(new ItemChat("", "", "", "Chat nhóm", 1525919731, "Nhấn để vào phòng"));
        listChat.add(new ItemChat("", "", "", "Chat với người lạ", 1525919731, "Nhấn để vào phòng"));
        adapter = new ListChatAdapter(listChat);
        rvListChat.setAdapter(adapter);
        adapter.setOnItemClickListener(new ListChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(getApplicationContext(), ChatStrangerActivity.class));
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChatFriendActivity.class);
                    intent.putExtra("idFirebase", listChat.get(position).getIdFirebase());
                    intent.putExtra("idFacebook", listChat.get(position).getIdFacebook());
                    intent.putExtra("name", listChat.get(position).getName());
                    startActivity(intent);
                }
            }
        });
        listChat.add(new ItemChat("PfV7P4t1XYfhK7EmKGdXkAVlMzx1", "100009817420838", "https://graph.facebook.com/100009817420838/picture?type=large", "Nhai Vu", 152648996, "Hi"));
        listChat.add(new ItemChat("ndkBdCEcfHcj9BFwhfy0dn3alGw1", "2087542758202038", "https://graph.facebook.com/2087542758202038/picture?type=large", "Quảng Đặng Ngọc", 1526489962, "Okay"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
