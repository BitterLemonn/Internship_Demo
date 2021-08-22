package com.example.deshion;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.deshion.Utils.AppContent;
import com.example.deshion.Utils.ToastUtils;

public class MainPageActivity extends AppCompatActivity {
    private AppContent app;
    private AlertDialog dialog;

    private TextView message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_layout);
        initController();
        // 判断是否需要跳转打赏界面
        if (app.getInvite_key() != null) {
            initDonate();
        }else {
            initRecommend();
        }
    }

    private void initController() {
        app = (AppContent) getApplication();
    }

    private void initDonate() {
        // 设置消息样式
        message = new TextView(this);
        message.setPadding(10,30,10,10);
        message.setText("\n您由XX工作室邀请\n给您喜爱的工作室打赏1元即可成为平台会员\n并获得平台优惠券一张");
        message.setGravity(Gravity.CENTER);
        message.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        message.setTextSize(16);

        dialog = new AlertDialog.Builder(this)
                .setView(message)
                .setTitle("")
                .setPositiveButton("去打赏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.getInstance(getApplicationContext())
                                .showShortToast("跳转打赏界面");
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dialog.show();
    }

    private void initRecommend(){
        // 设置消息样式
        message = new TextView(this);
        message.setPadding(10,30,10,10);
        message.setText("\n根据您的个人标签\n寻找了10个您可能感兴趣的工作室\n快来看看吧");
        message.setGravity(Gravity.CENTER);
        message.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        message.setTextSize(16);

        dialog = new AlertDialog.Builder(this)
                .setView(message)
                .setTitle("")
                .setPositiveButton("去看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.getInstance(getApplicationContext())
                                .showShortToast("跳转工作室界面");
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
        dialog.show();
    }

}
