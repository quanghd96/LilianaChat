package com.quang.lilianachat.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;

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
    }
}
