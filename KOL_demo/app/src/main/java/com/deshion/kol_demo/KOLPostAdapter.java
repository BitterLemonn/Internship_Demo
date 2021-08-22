package com.deshion.kol_demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;

import java.util.List;

public class KOLPostAdapter extends RecyclerView.Adapter<KOLPostAdapter.MyViewHolder> {
    private List<KOLPostItem> itemList;
    private List<LinkItem> linkList;
    private Context context;
    private AppContext app;
    private boolean has_link;

    public KOLPostAdapter(List<KOLPostItem> itemList, Context context, AppContext app, @Nullable List<LinkItem> linkList) {
        this.itemList = itemList;
        this.context = context;
        this.app = app;
        this.linkList = linkList;
        has_link = linkList != null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kol_post_item,
                parent, false);
        return new KOLPostAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.link_container.setVisibility(View.GONE);
        if(has_link){
            holder.link_container.setLayoutManager(new LinearLayoutManager(context));
            holder.link_container.setAdapter(new LinkAdapter(linkList, context, app));
        }

        final KOLPostItem kolPostItem = this.itemList.get(position);

        holder.title.setText(kolPostItem.getTitle());
        holder.introduce.setText(kolPostItem.getDescribe());
        holder.like_num.setText(kolPostItem.getLike_num());
        holder.favorite_num.setText(kolPostItem.getFavorite_num());
        holder.date.setText(kolPostItem.getDate());

        holder.pic.setImageDrawable(kolPostItem.getPic());

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
                        //显示隐藏商品连接
                        if (has_link && interpolatedTime >= 0.95 && isExpand){
                            holder.link_container.setVisibility(View.VISIBLE);
                        }
                        else if (has_link && interpolatedTime >= 0.1 && !isExpand){
                            holder.link_container.setVisibility(View.GONE);
                        }
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
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private ImageView more;

        private TextView title;
        private TextView introduce;
        private TextView like_num;
        private TextView favorite_num;
        private TextView date;

        private RecyclerView link_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.kol_post_img);
            title = itemView.findViewById(R.id.kol_post_title);
            introduce = itemView.findViewById(R.id.kol_post_introduce);
            like_num = itemView.findViewById(R.id.kol_post_like_num);
            favorite_num = itemView.findViewById(R.id.kol_post_favorite_num);
            date = itemView.findViewById(R.id.kol_post_date);
            link_container = itemView.findViewById(R.id.kol_post_link_container);
            more = itemView.findViewById(R.id.kol_post_more_pic);

        }
    }
}
