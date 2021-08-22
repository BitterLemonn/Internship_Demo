package com.example.deshion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.deshion.Utils.AppContent;
import com.example.deshion.Utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

public class TagPageFragment extends Fragment implements View.OnClickListener {

    private TextView last;
    private ImageView next;
    private TagFlowLayout tag_container;
    private int num_select = 0;

    private ArrayList<String> tag_list;
    private ArrayList<String> choose_list;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private AppContent app;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tag_choose_page_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initTag();
        initListener();
    }

    private void initController() {
        app = (AppContent) getActivity().getApplication();

        tag_list = new ArrayList<>();
        choose_list = new ArrayList<>();

        last = getActivity().findViewById(R.id.tag_last);
        next = getActivity().findViewById(R.id.tag_next);
        tag_container = getActivity().findViewById(R.id.tag_container);

        last.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    private void initListener() {
        //设置TagFlowLayout的Adapter
        tag_container.setAdapter(new TagAdapter<String>(tag_list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                // 设置tag文字样式
                TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tag_item, parent, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public void onSelected(int position, View view) {
                // 更改tag边框
                view.setBackground(getResources().getDrawable(R.drawable.checked_border));
                // 更改tag文字颜色
                ((TextView) view).setTextColor(0xFFFF1493);
                choose_list.add(((TextView) view).getText().toString());
                num_select += 1;
                // 更改下一步图标
                if (num_select >= 2) {
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                }
            }

            @Override
            public void unSelected(int position, View view) {
                // 更改tag边框
                view.setBackground(getResources().getDrawable(R.drawable.unchecked_border));
                // 更改tag文字颜色
                ((TextView) view).setTextColor(0xFF4169E1);
                choose_list.remove(((TextView) view).getText().toString());
                num_select -= 1;
                // 更改下一步图标
                if (num_select < 2) {
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                }
            }
        });
    }

    private void initTag() {
        tag_list.add("球鞋");
        tag_list.add("板鞋");
        tag_list.add("耐克");
        tag_list.add("阿迪达斯");
        tag_list.add("匹克");
        tag_list.add("李宁");
        tag_list.add("T-shirt");
    }

    private void jumpFragment(Fragment fragment, String tag) {
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.sign_content, fragment, tag);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tag_last: {
                jumpFragment(new InvitePageFragment(), "invite_page");
                break;
            }
            case R.id.tag_next: {
                if (num_select >= 2) {
                    app.setTag_list(choose_list);
                    startActivity(new Intent(getContext(), MainPageActivity.class));
                    getActivity().finish();
                } else {
                    ToastUtils.getInstance(getContext()).showShortToast("请至少选择两个你喜欢的标签");
                }
                break;
            }
        }
    }

}
