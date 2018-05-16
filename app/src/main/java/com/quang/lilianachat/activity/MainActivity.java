package com.quang.lilianachat.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.ViewPagerAdapter;
import com.quang.lilianachat.fragment.ProfileFragment;
import com.quang.lilianachat.fragment.RankFragment;
import com.tmall.ultraviewpager.UltraViewPager;

public class MainActivity extends AppCompatActivity {

    private UltraViewPager ultraViewPager;
    private ProfileFragment profileFragment;
    private RankFragment rankFragment;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();

    }

    private void findViewById() {
        ultraViewPager = findViewById(R.id.ultraViewPager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        profileFragment = new ProfileFragment();
        rankFragment = new RankFragment();

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(profileFragment, "Profile");
        adapter.addFragment(rankFragment, "Rank");

        ultraViewPager.setAdapter(adapter);

        ultraViewPager.initIndicator();
        ultraViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.parseColor("#008446"))
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics()));
        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        ultraViewPager.getIndicator().build();

        ultraViewPager.setInfiniteLoop(false);

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

        if (curUser.getUid().equals("JqQye7MZ6RMgkLoj5JozCg13rup1")) {
            FirebaseDatabase.getInstance().getReference("list_user").child(curUser.getUid()).child("pendingStranger").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean pending = dataSnapshot.getValue(Boolean.class);
                    if (pending != null && pending) {
                        // Create an explicit intent for an Activity in your app
                        sendNotification("Người lạ", "Có người lạ muốn chat với bạn. Nhấn để chat ngay...");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, ChatStrangerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = NotificationCompat.CATEGORY_SOCIAL;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
