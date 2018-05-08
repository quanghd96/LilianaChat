package com.quang.lilianachat.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.SearchAdapter;
import com.quang.lilianachat.model.UserInfo;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rvSearch;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private ArrayList<UserInfo> listUser;
    private SearchAdapter adapter;
    private FirebaseUser curUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById();

        curUser = FirebaseAuth.getInstance().getCurrentUser();

        listUser = new ArrayList<>();
        adapter = new SearchAdapter(listUser);
        rvSearch.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("list_user");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                if (!userInfo.getIdFirebase().equals(curUser.getUid())) {
                    listUser.add(dataSnapshot.getValue(UserInfo.class));
                    adapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
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

    private void findViewById() {
        rvSearch = findViewById(R.id.rvSearch);
        rvSearch.setLayoutManager(new GridLayoutManager(this, 2));
        progressBar = findViewById(R.id.progressBar);
    }
}
