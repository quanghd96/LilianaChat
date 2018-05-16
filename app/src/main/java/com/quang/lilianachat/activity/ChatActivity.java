package com.quang.lilianachat.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.MessageAdapter;
import com.quang.lilianachat.adapter.UserOnlineAdapter;
import com.quang.lilianachat.model.Message;
import com.quang.lilianachat.model.UserOnline;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1000;
    private ProgressBar progressBar;
    private EditText edtMessage;
    private Button btnSend, btnImage;
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
    private FirebaseStorage storage;
    private StorageReference storageReference;


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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, PICK_IMAGE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                newProfilePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                final StorageReference ref = storageReference.child("a_image_" + new Random().nextInt(10000));
                UploadTask uploadTask = ref.putBytes(byteArray);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(), "Upload fail!", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...

                    }
                });
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            HashMap<String, Object> message = new HashMap<>();
                            message.put("name", Profile.getCurrentProfile().getName());
                            message.put("linkAvatar", "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture");
                            message.put("timeSend", ServerValue.TIMESTAMP);
                            message.put("image", downloadUri.toString());
                            message.put("idFirebase", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            reference.push().setValue(message);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }

        }
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
        btnImage = findViewById(R.id.btnImage);
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
