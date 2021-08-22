package com.deshion.kol_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshion.kol_demo.utils.AppContext;
import com.deshion.kol_demo.utils.KeyboardUtils;
import com.deshion.kol_demo.utils.LinearLayoutWarp;

import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends Activity {

    private AppContext app;

    private EditText search_field;
    private TextView search_text;

    private LinearLayoutWarp hash_tag_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        initController();
        initHashTag();
    }

    private void initController() {
        app = (AppContext)getApplication();

        search_field = findViewById(R.id.search_search_field);
        search_text = findViewById(R.id.search_search_text);

        hash_tag_container = findViewById(R.id.search_search_hash_tag_container);

        // 进入activity自动打开软键盘
        search_field.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                KeyboardUtils.openKeyboard(search_field);
            }
        },600);

        //搜索跳转
        search_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!search_field.getText().toString().isEmpty()) {
                        onSearch();
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
        search_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!search_field.getText().toString().isEmpty()) {
                    onSearch();
                }
            }
        });
    }

    private void createHashTag(final String tag_name){
        TextView tag = (TextView) View.inflate(this, R.layout.tag_item, null);
        tag.setText(tag_name);
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递标签信息
                app.setSearch_key(tag_name);
                //打开结果页
                startActivity(new Intent(getApplicationContext(), SearchResultPageActivity.class));
                finish();
            }
        });
        hash_tag_container.addView(tag);
    }

    private void initHashTag(){
        createHashTag("春季穿搭");
        createHashTag("家常菜");
        createHashTag("文案");
        createHashTag("头像");
        createHashTag("健身");
        createHashTag("学习");
        createHashTag("美甲");
        createHashTag("装修");
        createHashTag("生日礼物");
        createHashTag("简笔画");
        createHashTag("电影");
        createHashTag("舞蹈");
    }

    private void onSearch(){
        app.setSearch_key(search_field.getText().toString());

        //打开结果页
        startActivity(new Intent(this, SearchResultPageActivity.class));
        finish();
    }
}
