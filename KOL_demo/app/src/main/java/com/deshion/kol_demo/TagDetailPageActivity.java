package com.deshion.kol_demo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.ImageViewPlus;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class TagDetailPageActivity extends Activity {

    private AppContext app;

    private ArrayList<KOLItem> item_list;

    private ImageViewPlus tag_expand_icon, tag_close_icon;

    private TextView tag_expand_name, tag_close_name;
    private TextView tag_expand_kol_num, tag_close_kol_num;
    private TextView tag_expand_post_num, tag_close_post_num;

    private TextView tag_expand_description;
    private ImageView tag_expand_more;

    private RecyclerView tag_container;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    private AppBarLayout tag_detail_card;
    private Toolbar tag_detail_tool_bar;
    private View tag_detail_close_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_detail_page);

        initController();
        initValue();
        initListener();
        //创建item↓
        testCrate();
        initAdapter();
    }

    private void initController(){
        app = (AppContext) getApplication();

        tag_expand_icon = findViewById(R.id.tag_expand_icon);
        tag_close_icon = findViewById(R.id.tag_close_icon);
        tag_expand_name = findViewById(R.id.tag_expand_name);
        tag_close_name = findViewById(R.id.tag_close_name);
        tag_expand_kol_num = findViewById(R.id.tag_expand_kol_num);
        tag_close_kol_num = findViewById(R.id.tag_close_kol_num);
        tag_expand_post_num = findViewById(R.id.tag_expand_post_num);
        tag_close_post_num = findViewById(R.id.tag_close_post_num);
        tag_expand_description = findViewById(R.id.tag_expand_introduce);
        tag_expand_more = findViewById(R.id.tag_expand_more_pic);

        tag_container = findViewById(R.id.tag_detail_container);
        layoutManager = new LinearLayoutManager(this);
        tag_container.setLayoutManager(layoutManager);

        item_list = new ArrayList<>();

        tag_detail_card = findViewById(R.id.tag_detail_top_bar);
        tag_detail_tool_bar = findViewById(R.id.tag_detail_tool_bar);
        tag_detail_close_bar = findViewById(R.id.tag_detail_close_bar);
    }

    private void initValue(){
        tag_expand_icon.setImageDrawable(app.getIcon_pic());
        tag_close_icon.setImageDrawable(app.getIcon_pic());
        tag_expand_name.setText(app.getId_string());
        tag_close_name.setText(app.getId_string());
        tag_expand_kol_num.setText(app.getKol_string());
        tag_close_kol_num.setText(app.getKol_string());
        tag_expand_post_num.setText(app.getBlog_string());
        tag_close_post_num.setText(app.getBlog_string());
        tag_expand_description.setText(app.getIntroduce_string());

    }

    private void initListener(){
        final View.OnClickListener mListener = new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                tag_expand_description.clearAnimation();
                final float deltaHeight;
                final float startHeight = tag_expand_description.getHeight();
                int duration = 350;
                if (isExpand) {
                    // 折叠动画
                    deltaHeight = (tag_expand_description.getLineHeight()) * tag_expand_description.getLineCount() - startHeight;
                    RotateAnimation animation = new RotateAnimation(0, 180,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    tag_expand_more.startAnimation(animation);
                } else {
                    // 展开动画
                    deltaHeight = (tag_expand_description.getLineHeight()) * 3 - startHeight;
                    RotateAnimation animation = new RotateAnimation(180, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    tag_expand_more.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        //根据ImageView旋转动画的百分比来显示TextView高度，达到动画效果
                        tag_expand_description.setHeight((int) (startHeight + deltaHeight * interpolatedTime));
                    }
                };
                animation.setDuration(duration);
                tag_expand_description.startAnimation(animation);
            }
        };
        tag_expand_description.post(new Runnable() {
            @Override
            public void run() {
                // 监听点击折叠TextView事件
                if (tag_expand_description.getLineCount() > 3) {
                    tag_expand_description.setOnClickListener(mListener);
                    tag_expand_more.setOnClickListener(mListener);
                }
                // 判断要不要显示 更多箭头
                tag_expand_more.setVisibility(tag_expand_description.getLineCount() > 3 ? View.VISIBLE : View.GONE);
            }
        });

        // 切换顶部工具栏监听器
        tag_detail_card.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int offset = Math.abs(i);
                int total = appBarLayout.getTotalScrollRange();

                if (offset <= total / 2) {
                    tag_detail_tool_bar.setVisibility(View.GONE);
                    tag_detail_close_bar.setVisibility(View.GONE);
                } else {
                    tag_detail_tool_bar.setVisibility(View.VISIBLE);
                    tag_detail_close_bar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void crateItem(Drawable icon, String id, String introduce, String fans,
                           String blog) {
        KOLItem kolItem = new KOLItem(icon, id, introduce, fans, blog, null, true);
        item_list.add(kolItem);
    }

    private void initAdapter(){
        mAdapter = new KOLRecycleViewAdapter(item_list, getApplicationContext(), app);
        tag_container.setAdapter(mAdapter);
    }

    private void testCrate() {
        for (int i = 0; i < 10; i++) {
            crateItem(getResources().getDrawable(R.drawable.icon_natsu), "BitterLemon",
                    "树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬",
                    "1w", "25");
        }
    }
}
