package com.deshion.kol_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.LinearLayoutWarp;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

public class KOLDetailPageActivity extends Activity implements View.OnClickListener {
    private AppContext app;

    private LinearLayoutWarp kol_detail_tag_container;
    private RecyclerView kol_post_container;

    private RecyclerView.Adapter PostAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<KOLPostItem> item_list;
    private ArrayList<LinkItem> link_list;

    private ImageView icon;
    private ImageView more;
    private ImageView icon_close;
    private ImageView follow_btn;
    private ImageView follow_close_btn;

    private TextView id;
    private TextView introduce;
    private TextView fans;
    private TextView blog;
    private TextView follow;
    private TextView id_close;
    private TextView follow_close;

    private ArrayList<String> tag_list;
    private Drawable icon_pic;
    private String id_string;
    private String introduce_string;
    private String fans_string;
    private String blog_string;
    private boolean isFollowed;
    private boolean has_link;

    private AppBarLayout detail_card;
    private Toolbar detail_tool_bar;
    private View detail_close_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kol_detail_page);
        initController();
        initData();
        initResource();
        initListener();
        // ?????????????????????
        addResource();
        initPost();
    }

    private void initController() {
        app = (AppContext) getApplication();
        item_list = new ArrayList<>();
        link_list = new ArrayList<>();

        icon = findViewById(R.id.kol_detail_icon);
        icon_close = findViewById(R.id.kol_detail_close_icon);
        id = findViewById(R.id.kol_detail_id);
        id_close = findViewById(R.id.kol_detail_close_id);
        introduce = findViewById(R.id.kol_detail_introduce);
        fans = findViewById(R.id.kol_detail_fans);
        blog = findViewById(R.id.kol_detail_blog);
        more = findViewById(R.id.kol_detail_more_pic);
        follow = findViewById(R.id.kol_detail_follow_text);
        follow_close = findViewById(R.id.kol_detail_close_follow_text);
        follow_btn = findViewById(R.id.kol_detail_follow_btn);
        follow_close_btn = findViewById(R.id.kol_detail_close_follow_btn);

        kol_detail_tag_container = findViewById(R.id.kol_detail_tag_container);
        kol_post_container = findViewById(R.id.kol_detail_post_container);
        layoutManager = new LinearLayoutManager(this);
        kol_post_container.setLayoutManager(layoutManager);

        detail_card = findViewById(R.id.kol_detail_top_bar);
        detail_close_bar = findViewById(R.id.kol_detail_close_bar);
        detail_tool_bar = findViewById(R.id.kol_detail_tool_bar);
    }

    //??????????????????
    private void initData() {
        icon_pic = app.getIcon_pic();
        id_string = app.getId_string();
        introduce_string = app.getIntroduce_string();
        fans_string = app.getFans_string();
        blog_string = app.getBlog_string();
        tag_list = app.getTag_list();
        isFollowed = app.isFollowed();

        has_link = true; //?????????????????????????????????????????????????????????????????????

        // ????????????
        if (tag_list.size() == 0){
            introduce_string += "\n???????????????????????????????????????tag????????????,?????????tag??????????????????";
        }else {
            for (int i = 0; i < tag_list.size(); i++) {
                View view = View.inflate(app.getContext(), R.layout.tag_item, null);
                TextView tag = (TextView) view.findViewById(R.id.tag_view);
                //???????????????
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(LinearLayout.MarginLayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMarginEnd(30);
                tag.setLayoutParams(lp);

                //????????????????????????tag??????
                tag.setText(tag_list.get(i));
                final int finalI = i;
                tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * ??????????????????????????????tag???????????????????????????
                         */
                        app.setId_string(tag_list.get(finalI));
                        app.setBlog_string("???????????????");
                        app.setKol_string("???????????????");
                        app.setIntroduce_string("???????????????,?????????????????????KOLDetailPageActivity,line 130");
                        app.setIcon_pic(getResources().getDrawable(R.drawable.tag_pink));

                        startActivity(new Intent(app.getContext(), TagDetailPageActivity.class));
                    }
                });
                kol_detail_tag_container.addView(tag);
            }
        }
    }

    //?????????????????????
    private void initResource() {
        icon.setImageDrawable(icon_pic);
        icon_close.setImageDrawable(icon_pic);
        id.setText(id_string);
        id_close.setText(id_string);
        introduce.setText(introduce_string);
        fans.setText(fans_string);
        blog.setText(blog_string);

        follow_close_btn.setOnClickListener(this);
        follow_btn.setOnClickListener(this);

        if (isFollowed) {
            follow.setText(R.string.followed);
            follow_close.setText(R.string.followed);
        }
    }

    private void initListener() {
        // ?????????????????????
        final View.OnClickListener mListener = new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                introduce.clearAnimation();
                final float deltaHeight;
                final float startHeight = introduce.getHeight();
                int duration = 350;
                if (isExpand) {
                    // ????????????
                    deltaHeight = (introduce.getLineHeight()) * introduce.getLineCount() - startHeight;
                    RotateAnimation animation = new RotateAnimation(0, 180,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    more.startAnimation(animation);
                } else {
                    // ????????????
                    deltaHeight = (introduce.getLineHeight()) * 3 - startHeight;
                    RotateAnimation animation = new RotateAnimation(180, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    more.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        //??????ImageView?????????????????????????????????TextView???????????????????????????
                        introduce.setHeight((int) (startHeight + deltaHeight * interpolatedTime));
                    }
                };
                animation.setDuration(duration);
                introduce.startAnimation(animation);
            }
        };

        introduce.post(new Runnable() {
            @Override
            public void run() {
                // ??????????????????TextView??????
                if (introduce.getLineCount() > 3) {
                    introduce.setOnClickListener(mListener);
                    more.setOnClickListener(mListener);
                }
                // ????????????????????? ????????????
                more.setVisibility(introduce.getLineCount() > 3 ? View.VISIBLE : View.GONE);
            }
        });

        // ??????????????????????????????
        detail_card.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int offset = Math.abs(i);
                int total = appBarLayout.getTotalScrollRange();

                if (offset <= total / 2) {
                    detail_tool_bar.setVisibility(View.GONE);
                    detail_close_bar.setVisibility(View.GONE);
                } else {
                    detail_tool_bar.setVisibility(View.VISIBLE);
                    detail_close_bar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // ??????item
    private void crateItem(Drawable pic, String title, String introduce, String like_num, String favorite_num, String date) {
        KOLPostItem item = new KOLPostItem();
        item.setPic(pic);
        item.setTitle(title);
        item.setDescribe(introduce);
        item.setLike_num(like_num);
        item.setFavorite_num(favorite_num);
        item.setDate(date);
        item_list.add(item);
    }

    private void crateLink(Drawable pic, String title, String description, String price){
        LinkItem item = new LinkItem(pic, title, description, price);
        link_list.add(item);
    }

    private void initPost() {
        PostAdapter = new KOLPostAdapter(item_list, this, app, link_list);
        kol_post_container.setAdapter(PostAdapter);
    }

    private void addResource() {
        for (int i = 0; i < 10; i++) {
            crateItem(getResources().getDrawable(R.drawable.jk_girl), "waaaaaaaaa",
                    "??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????\n??????????????????"
                    , "1k", "1282", "7???24???");
        }
        crateLink(getResources().getDrawable(R.drawable.loading), "????????????", "????????????????????????????????????", "239");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.kol_detail_follow_btn:
            case R.id.kol_detail_close_follow_btn:{
                Log.e("TAG", "onClick: in");
                if (follow.getText().toString().equals(getResources().getString(R.string.followed))){
                    follow.setText(R.string.follow);
                    follow_close.setText(R.string.follow);
                }else {
                    follow.setText(R.string.followed);
                    follow_close.setText(R.string.followed);
                }
                break;
            }
        }
    }
}
