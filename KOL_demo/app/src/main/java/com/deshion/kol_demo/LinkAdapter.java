package com.deshion.kol_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.ImageViewPlus;
import com.deshion.kol_demo.utils.ToastUtils;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {
    private List<LinkItem> itemList;
    private Context context;

    private AppContext app;

    public LinkAdapter(List<LinkItem> itemList, Context context, AppContext app) {
        this.itemList = itemList;
        this.context = context;
        this.app = app;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kol_link_item,
                parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LinkItem linkItem = itemList.get(position);

        holder.pic.setImageDrawable(linkItem.getPic());
        holder.title.setText(linkItem.getTitle());
        holder.description.setText(linkItem.getDescription());
        holder.price.setText("￥" + linkItem.getPrice());

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.getInstance(context).showShortToast("打开商品详情页");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageViewPlus pic;
        private TextView title, description, price;
        private Button detail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.link_pic);
            title = itemView.findViewById(R.id.link_title);
            description = itemView.findViewById(R.id.link_description);
            price = itemView.findViewById(R.id.link_price);
            detail = itemView.findViewById(R.id.link_detail_btn);
        }
    }


}
