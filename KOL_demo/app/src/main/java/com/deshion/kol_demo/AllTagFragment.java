package com.deshion.kol_demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;

import java.util.ArrayList;

public class AllTagFragment extends Fragment {
    private AppContext app;

    private RecyclerView tag_container;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<TagItem> itemList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tag_all_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        //添加item↓
        testCreateItem();
        initAdapter();
    }

    private void initController(){
        app = (AppContext) getActivity().getApplication();

        tag_container = getActivity().findViewById(R.id.tag_search_contain);
        tag_container.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        tag_container.setLayoutManager(layoutManager);

        itemList = new ArrayList<>();
    }

    private void create_item(Drawable icon, String name, String description,
                             String blog_num, String kol_num){
        itemList.add(new TagItem(icon, name, kol_num, blog_num, description));
    }

    private void initAdapter(){
        mAdapter = new TagRecycleViewAdapter(itemList, getContext(), app);
        tag_container.setAdapter(mAdapter);
    }

    private void testCreateItem(){
        for (int i = 0; i < 10; i++){
            create_item(getResources().getDrawable(R.drawable.tag_blue), "夏季穿搭",
                    "炎热的夏天也要做一个精致的小孩!\n炎热的夏天也要做一个精致的小孩!\n炎热的夏天也要做一个精致的小孩!\n炎热的夏天也要做一个精致的小孩!\n炎热的夏天也要做一个精致的小孩!",
                    "272", "17");
            create_item(getResources().getDrawable(R.drawable.tag_pink), "上衣",
                    "第一眼的着装一定要慎重", "8212", "291");
        }
    }
}
