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

import java.util.ArrayList;
import java.util.List;

public class TagRecycleViewAdapter extends RecyclerView.Adapter<TagRecycleViewAdapter.MyViewHolder> {

    private ArrayList<TagItem> itemList;
    private Context context;
    private AppContext app;

    public TagRecycleViewAdapter(List<TagItem> itemList, Context context, AppContext app) {
        this.itemList = (ArrayList<TagItem>) itemList;
        this.context = context;
        this.app = app;
    }

    @NonNull
    @Override
    public TagRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_search_item,
                parent, false);
        return new TagRecycleViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final TagItem tagItem = this.itemList.get(position);

        holder.blog.setText(R.string.post_num);
        holder.blog.append(tagItem.getBlog_num());
        holder.kol.setText(R.string.kol_num);
        holder.kol.append(tagItem.getKol_num());

        holder.introduce.setText(tagItem.getDescription());
        holder.icon.setImageDrawable(tagItem.getIcon());
        holder.name.setText(tagItem.getName());

        // 伸缩TextView
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

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.setIcon_pic(holder.icon.getDrawable());
                app.setBlog_string(holder.blog.getText().toString());
                app.setKol_string(holder.kol.getText().toString());
                app.setIntroduce_string(holder.introduce.getText().toString());
                app.setId_string(holder.name.getText().toString());
                app.getContext().startActivity(new Intent(app.getContext(), TagDetailPageActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private ImageView more;

        private TextView name;
        private TextView introduce;
        private TextView kol;
        private TextView blog;

        private Button detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.tag_icon);
            name = itemView.findViewById(R.id.tag_name);
            introduce = itemView.findViewById(R.id.tag_introduce);
            kol = itemView.findViewById(R.id.tag_kol_num);
            blog = itemView.findViewById(R.id.tag_post_num);
            more = itemView.findViewById(R.id.tag_more_pic);
            detail = itemView.findViewById(R.id.tag_detail_btn);
        }
    }
}
