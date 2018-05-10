package com.quang.lilianachat.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.ViewPagerAdapter;
import com.quang.lilianachat.model.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {
    private TextView tvHeart, tvAngry, tvStranger;


    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReactFragment reactFragment = new ReactFragment();
        StrangerFragment strangerFragment = new StrangerFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        adapter.addFragment(reactFragment, "Top react");
        adapter.addFragment(strangerFragment, "Top stranger");
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        TextView tvName = view.findViewById(R.id.tvName);
        tvName.setText(Profile.getCurrentProfile().getName());
        tvHeart = view.findViewById(R.id.tvHeart);
        tvAngry = view.findViewById(R.id.tvAngry);
        tvStranger = view.findViewById(R.id.tvStranger);
        ImageView imvHeart = view.findViewById(R.id.imvHeart);
        ImageView imvAngry = view.findViewById(R.id.imvAngry);
        Glide.with(view).load(R.drawable.heart).into(imvHeart);
        Glide.with(view).load(R.drawable.angry).into(imvAngry);

        FirebaseDatabase.getInstance().getReference("list_user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                tvHeart.setText(userInfo.getHeart() + "");
                tvAngry.setText(userInfo.getAngry() + "");
                tvStranger.setText(userInfo.getCountStranger() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
