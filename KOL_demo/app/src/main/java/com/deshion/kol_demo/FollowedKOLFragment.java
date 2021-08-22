package com.deshion.kol_demo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.LinearLayoutWarp;

import java.util.ArrayList;
import java.util.List;

public class FollowedKOLFragment extends Fragment {
    private AppContext app;

    private RecyclerView kol_container;
    private LinearLayoutWarp kol_tag_container;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;

    private List<KOLItem> item_list;
    private ArrayList<String> tagList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kol_liked_contain_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        // 这里添加item
        testCrate();
    }

    private void initController() {
        app = (AppContext)getActivity().getApplication();

        item_list = new ArrayList<>();
        tagList = new ArrayList<>();

        kol_container = getActivity().findViewById(R.id.kol_liked_contain);
        kol_container.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        kol_container.setLayoutManager(layoutManager);

        kol_tag_container = getActivity().findViewById(R.id.kol_tag_container);
    }

    private void crateItem(Drawable icon, String id, String introduce, String fans,
                           String blog, final ArrayList<String> tag_list) {
        ArrayList<TextView> tag_view_list = new ArrayList<>();
        for (int i = 0; i < tag_list.size(); i++) {
            app.setContext(getContext());

            final TextView tag = (TextView) View.inflate(getContext(), R.layout.tag_item, kol_tag_container);

            // 标签点击搜索
            final int finalI = i;
            tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 这里需要连接后台获取tag的具体信息！！！！
                     */
                    app.setId_string(tag_list.get(finalI));
                    app.setBlog_string("未连接后台");
                    app.setKol_string("未连接后台");
                    app.setIntroduce_string("未连接后台,解决方法请查询FollowedKOLFragment,line 76");
                    app.setIcon_pic(getResources().getDrawable(R.drawable.tag_pink));

                    startActivity(new Intent(app.getContext(), TagDetailPageActivity.class));
                }
            });
            tag.setText("#" + tag_list.get(i));
            tag_view_list.add(tag);
        }

        KOLItem kolItem = new KOLItem(icon, id, introduce, fans, blog, tag_view_list, false);
        item_list.add(kolItem);
        addIntoContainer();
    }

    private void addIntoContainer() {
        mAdapter = new KOLRecycleViewAdapter(item_list, getActivity(), app);
        kol_container.setAdapter(mAdapter);
    }

    private void testCrate() {
        tagList.add("夏季穿搭");
        tagList.add("上衣");
        for (int i = 0; i < 10; i++) {
            crateItem(getResources().getDrawable(R.drawable.icon_natsu), "BitterLemon",
                    "树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬", "1w", "25", tagList);
        }
    }
}
