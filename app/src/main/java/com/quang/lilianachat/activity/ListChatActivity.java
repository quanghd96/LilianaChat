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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.ListChatAdapter;
import com.quang.lilianachat.adapter.MenuAdapter;
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
        adapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
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
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().contains(curUser.getUid())) {
                    ItemChat itemChat = dataSnapshot.getValue(ItemChat.class);
                    itemChat.setIdFacebook(itemChat.getIcon()
                            .replace("https://graph.facebook.com/", "")
                            .replace("/picture", "")
                    );
                    itemChat.setIdFirebase(dataSnapshot.getKey().replace(curUser.getUid(), ""));
                    listChat.add(itemChat);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
