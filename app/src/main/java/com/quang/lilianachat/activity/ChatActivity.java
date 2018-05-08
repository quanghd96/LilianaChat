package com.quang.lilianachat.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.MessageAdapter;
import com.quang.lilianachat.adapter.UserOnlineAdapter;
import com.quang.lilianachat.model.Message;
import com.quang.lilianachat.model.UserOnline;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText edtMessage;
    private Button btnSend;
    private FloatingActionButton btnBottom;
    private TextView tvNumberOnline;
    private RecyclerView rvMessage, rvOnline;
    private ArrayList<Message> listMessage;
    private ArrayList<UserOnline> listUserOnline;
    private MessageAdapter adapter;
    private UserOnlineAdapter userOnlineAdapter;
    private FirebaseDatabase database;
    private DatabaseReference reference, myConnectionsRef, connectedRef;
    private LinearLayoutManager layoutManager;
    private String childName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViewById();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference("chat").child("message");
        myConnectionsRef = database.getReference("chat").child("online");
        adapter = new MessageAdapter(listMessage);
        rvMessage.setAdapter(adapter);

        userOnlineAdapter = new UserOnlineAdapter(listUserOnline);
        rvOnline.setAdapter(userOnlineAdapter);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listMessage.add(dataSnapshot.getValue(Message.class));
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                if (layoutManager.findLastVisibleItemPosition() == -1 ||
                        layoutManager.findLastVisibleItemPosition() >= listMessage.size() - 3) {
                    rvMessage.scrollToPosition(listMessage.size() - 1);
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

        myConnectionsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserOnline userOnline = dataSnapshot.getValue(UserOnline.class);
                if (!listUserOnline.contains(userOnline)) {
                    listUserOnline.add(userOnline);
                    userOnlineAdapter.notifyDataSetChanged();
                }
                tvNumberOnline.setText("Trực tuyến: " + listUserOnline.size());
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

        rvMessage.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findLastVisibleItemPosition() >= listMessage.size() - 1) {
                    // Scroll Down
                    if (btnBottom.isShown()) {
                        btnBottom.hide();
                    }
                } else {
                    // Scroll Up
                    if (!btnBottom.isShown()) {
                        btnBottom.show();
                    }
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = edtMessage.getText().toString().trim();
                if (!mess.isEmpty()) {
                    HashMap<String, Object> message = new HashMap<>();
                    message.put("name", Profile.getCurrentProfile().getName());
                    message.put("linkAvatar", "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture");
                    message.put("timeSend", ServerValue.TIMESTAMP);
                    message.put("message", mess);
                    message.put("idFirebase", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.push().setValue(message);
                }
                edtMessage.setText("");
                try {
                    rvMessage.scrollToPosition(listMessage.size() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    rvMessage.scrollToPosition(listMessage.size() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class)) {
                    DatabaseReference con = myConnectionsRef.push();
                    childName = con.getKey();
                    try {
                        UserOnline userOnline = new UserOnline(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                Profile.getCurrentProfile().getId(), Profile.getCurrentProfile().getName());
                        con.setValue(userOnline);
                        con.onDisconnect().removeValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase.getInstance().getReference("chat/online").child(childName).removeValue();
    }

    private void findViewById() {
        progressBar = findViewById(R.id.progressBar);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        rvMessage = findViewById(R.id.rvMessage);
        rvOnline = findViewById(R.id.rvOnline);
        layoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(layoutManager);
        rvOnline.setLayoutManager(new LinearLayoutManager(this));
        listMessage = new ArrayList<>();
        listUserOnline = new ArrayList<>();
        tvNumberOnline = findViewById(R.id.tvNumberOnline);
        btnBottom = findViewById(R.id.btnBottom);
    }
}
