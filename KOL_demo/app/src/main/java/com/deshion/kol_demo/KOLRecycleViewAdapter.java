package com.deshion.kol_demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.LinearLayoutWarp;

import java.util.ArrayList;
import java.util.List;

public class KOLRecycleViewAdapter extends RecyclerView.Adapter<KOLRecycleViewAdapter.MyViewHolder> {
    private List<KOLItem> itemList;
    private Context context;

    private AppContext app;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private ImageView more;

        private TextView id;
        private TextView introduce;
        private TextView fans;
        private TextView blog;

        private Button detail;

        private LinearLayoutWarp tag_contain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.kol_icon);
            id = itemView.findViewById(R.id.kol_id);
            introduce = itemView.findViewById(R.id.kol_introduce);
            fans = itemView.findViewById(R.id.kol_fans);
            blog = itemView.findViewById(R.id.kol_blog);
            tag_contain = itemView.findViewById(R.id.kol_tag_container);
            more = itemView.findViewById(R.id.kol_more_pic);
            detail = itemView.findViewById(R.id.kol_detail_btn);
        }
    }

    public KOLRecycleViewAdapter(List<KOLItem> itemList, Context context, AppContext app) {
        this.itemList = itemList;
        this.context = context;
        this.app = app;
    }

    @NonNull
    @Override
    public KOLRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kol_item,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final KOLRecycleViewAdapter.MyViewHolder holder, int position) {
        final KOLItem kolItem = this.itemList.get(position);

        if (kolItem.isNullTag()){
            holder.tag_contain.setVisibility(View.GONE);
        }else {
            // 添加tag
            for (int i = 0; i < kolItem.getTag_list().size(); i++) {
                ViewGroup parent = (ViewGroup) kolItem.getTag_list().get(i).getParent();
                if (kolItem.getTag_list().get(i).getParent() != null) {
                    parent.removeView(kolItem.getTag_list().get(i));
                }
                holder.tag_contain.addView(kolItem.getTag_list().get(i));
            }
        }
        holder.blog.setText(R.string.post_num);
        holder.blog.append(kolItem.getBlog());
        holder.fans.setText(R.string.fans_num);
        holder.fans.append(kolItem.getFans());

        holder.introduce.setText(kolItem.getIntroduce());
        holder.icon.setImageDrawable(kolItem.getIcon());
        holder.id.setText(kolItem.getId());

        // 点击展开监听器
        final View.OnClickListener mListener = new View.OnClickListener() {
            boolean isExpand;

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                holder.introduce.clearAnimation();
                final float deltaHeight;
                final float startHeight = holder.introduce.getHeight();
                int duration = 350;
                if (isExpand) {
                    // 折叠动画
                    deltaHeight = (holder.introduce.getLineHeight()) * holder.introduce.getLineCount() - startHeight;
                    RotateAnimation animation = new RotateAnimation(0, 180,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    holder.more.startAnimation(animation);
                } else {
                    // 展开动画
                    deltaHeight = (holder.introduce.getLineHeight()) * 3 - startHeight;
                    RotateAnimation animation = new RotateAnimation(180, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    animation.setDuration(duration);
                    animation.setFillAfter(true);
                    holder.more.startAnimation(animation);
                }
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        //根据ImageView旋转动画的百分比来显示TextView高度，达到动画效果
                        holder.introduce.setHeight((int) (startHeight + deltaHeight * interpolatedTime));
                    }
                };
                animation.setDuration(duration);
                holder.introduce.startAnimation(animation);
            }
        };

        holder.introduce.post(new Runnable() {
            @Override
            public void run() {
                // 监听点击折叠TextView事件
                if (holder.introduce.getLineCount() > 3) {
                    holder.introduce.setOnClickListener(mListener);
                    holder.more.setOnClickListener(mListener);
                }
                // 判断要不要显示 更多箭头
                holder.more.setVisibility(holder.introduce.getLineCount() > 3 ? View.VISIBLE : View.GONE);
            }
        });

        // 跳转详情页
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TextView> tag_list = kolItem.getTag_list();
                ArrayList<String> tag = new ArrayList<>();

                app.setIcon_pic(holder.icon.getDrawable());
                app.setId_string(holder.id.getText().toString());
                app.setIntroduce_string(holder.introduce.getText().toString());
                app.setFans_string(holder.fans.getText().toString());
                app.setBlog_string(holder.blog.getText().toString());
                if (tag_list != null) {
                    for (int i = 0; i < tag_list.size(); i++) {
                        tag.add(tag_list.get(i).getText().toString());
                    }
                }
                app.setTag_list(tag);
                app.setFollowed(true);
                Intent intent = new Intent(context, KOLDetailPageActivity.class);
                //在activity外创建activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
