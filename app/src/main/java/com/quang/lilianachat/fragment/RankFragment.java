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
import com.quang.lilianachat.R;
import com.quang.lilianachat.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {


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
        TextView tvHeart = view.findViewById(R.id.tvHeart);
        TextView tvAngry = view.findViewById(R.id.tvAngry);
        ImageView imvHeart = view.findViewById(R.id.imvHeart);
        ImageView imvAngry = view.findViewById(R.id.imvAngry);
        Glide.with(view).load(R.drawable.heart).into(imvHeart);
        Glide.with(view).load(R.drawable.angry).into(imvAngry);

    }
}
