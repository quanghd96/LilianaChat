package com.quang.lilianachat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.quang.lilianachat.R;
import com.quang.lilianachat.activity.ChatActivity;
import com.quang.lilianachat.activity.ChatStrangerActivity;
import com.quang.lilianachat.activity.GameActivity;
import com.quang.lilianachat.activity.ListChatActivity;
import com.quang.lilianachat.activity.LuckyWheelActivity;
import com.quang.lilianachat.activity.SearchActivity;
import com.quang.lilianachat.adapter.MenuAdapter;
import com.quang.lilianachat.model.MenuItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private RecyclerView rvMenu;
    private MenuAdapter adapter;
    private ArrayList<MenuItem> listMenuItem;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(view).load(R.drawable.heart).into((ImageView) view.findViewById(R.id.imvHeart));
        Glide.with(view).load(R.drawable.angry).into((ImageView) view.findViewById(R.id.imvAngry));

        ImageView imvAvatar = view.findViewById(R.id.imvAvatar);
        Glide.with(view).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imvAvatar);
        TextView tvName = view.findViewById(R.id.tvName);
        tvName.setText(Profile.getCurrentProfile().getName());

        rvMenu = view.findViewById(R.id.rvMenu);
        listMenuItem = new ArrayList<>();
        listMenuItem.add(new MenuItem("Search", R.drawable.ic_search_friend));
        listMenuItem.add(new MenuItem("Chat Room", R.drawable.ic_chat_room));
        listMenuItem.add(new MenuItem("Chat with stranger", R.drawable.ic_chat_with_person));
        listMenuItem.add(new MenuItem("All chat", R.drawable.ic_list_chat));
        listMenuItem.add(new MenuItem("Lucky wheel", R.drawable.ic_lucky_wheel));
        listMenuItem.add(new MenuItem("More game", R.drawable.ic_game));
        adapter = new MenuAdapter(listMenuItem);
        rvMenu.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        rvMenu.setAdapter(adapter);

        adapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), ChatActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), ChatStrangerActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), ListChatActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), LuckyWheelActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), GameActivity.class));
                        break;
                }
            }
        });
    }
}
