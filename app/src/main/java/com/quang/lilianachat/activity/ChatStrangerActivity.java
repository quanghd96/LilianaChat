package com.quang.lilianachat.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.MessageStrangerAdapter;
import com.quang.lilianachat.model.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatStrangerActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseUser curUser;
    private DatabaseReference mRef;
    private ProgressBar progressBar;
    private EditText edtMessage;
    private Button btnSend;
    private FloatingActionButton btnBottom;
    private RecyclerView rvMessage;
    private ArrayList<Message> listMessage;
    private ArrayList<String> listKey;
    private LinearLayoutManager layoutManager;
    private MessageStrangerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_stranger);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById();

        database = FirebaseDatabase.getInstance();

        database = FirebaseDatabase.getInstance();
        curUser = FirebaseAuth.getInstance().getCurrentUser();

        mRef = database.getReference("chat_stranger").child(curUser.getUid());

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

        mRef.addChildEventListener(new ChildEventListener() {
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
                    mRef.push().setValue(message);
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

    private void findViewById() {
        progressBar = findViewById(R.id.progressBar);
        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);
        rvMessage = findViewById(R.id.rvMessage);
        layoutManager = new LinearLayoutManager(this);
        rvMessage.setLayoutManager(layoutManager);
        listMessage = new ArrayList<>();
        btnBottom = findViewById(R.id.btnBottom);
        adapter = new MessageStrangerAdapter(listMessage);
        rvMessage.setAdapter(adapter);
        listKey = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_stranger, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        else if (item.getItemId() == R.id.btnExit) {
            mRef.setValue(null);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
