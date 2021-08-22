package com.deshion.kol_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deshion.kol_demo.utils.AppContext;

import java.util.ArrayList;

public class SearchResultPageActivity extends Activity {

    private AppContext app;

    private EditText search_field;
    private ImageView search_del;

    private RecyclerView kol_container;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<KOLItem> item_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        initController();
        //这里添加item↓
        addResource();
        initAdapter();
    }

    private void initController(){
        app = (AppContext)getApplication();

        search_field = findViewById(R.id.search_result_search_field);
        search_del = findViewById(R.id.search_result_search_del);

        kol_container = findViewById(R.id.search_result_kol_container);
        layoutManager = new LinearLayoutManager(this);
        kol_container.setLayoutManager(layoutManager);

        item_list = new ArrayList<>();

        //初始化输入框
        search_field.setText(app.getSearch_key());
        search_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!search_field.getText().toString().isEmpty()){
                    search_del.setVisibility(View.VISIBLE);
                }
            }
        });
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
        //删除键初始化
        search_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_field.setText("");
                search_del.setVisibility(View.GONE);
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
        kol_container.setAdapter(mAdapter);
    }

    private void addResource(){
        for (int i = 0; i < 10; i++) {
            crateItem(getResources().getDrawable(R.drawable.icon_natsu), "BitterLemon",
                    "树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬\n树上的小柠檬",
                    "1w", "25");
        }
    }

    private void onSearch(){
        app.setSearch_key(search_field.getText().toString());

        //打开结果页
        startActivity(new Intent(this, SearchResultPageActivity.class));
        finish();
    }

}
