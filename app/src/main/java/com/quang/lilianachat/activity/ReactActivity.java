package com.quang.lilianachat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quang.lilianachat.R;

public class ReactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react);

        ImageView imvHeart = findViewById(R.id.imvHeart);
        ImageView imvAngry = findViewById(R.id.imvAngry);
        Glide.with(this).load(R.drawable.heart).into(imvHeart);
        Glide.with(this).load(R.drawable.angry).into(imvAngry);
        imvHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imvAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
