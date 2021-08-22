package com.deshion.kol_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.deshion.kol_demo.unuse.HomePageFragment;
import com.deshion.kol_demo.unuse.MyPageFragment;
import com.deshion.kol_demo.unuse.ShoppingPageFragment;
import com.deshion.kol_demo.utils.AppContext;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private AppContext app;

    private ViewPager viewPager;
    private BottomNavigationView bar;
    private ArrayList<Fragment> pageList;
    private View currentFocus;
    private InputMethodManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (AppContext)getApplication();
        if (pageList == null) {
            initPage();
        }
        initController();
    }

    private void initController(){
        manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        bar = findViewById(R.id.bottom_navigation_bar);
        bar.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                viewPager.setCurrentItem(menuItem.getOrder());
                return false;
            }
        });

        viewPager = findViewById(R.id.viewpager_content);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return pageList.get(position);
            }

            @Override
            public int getCount() {
                return pageList.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bar.getMenu().getItem(position).setChecked(true);
                currentFocus = getCurrentFocus();
                if (currentFocus != null) {
                    //检查输入法是否打开
                    if (manager.isActive()) {
                        //隐藏软键盘
                        try {
                            manager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bar.getMenu().getItem(1).setChecked(true);
        viewPager.setCurrentItem(1);
    }

    private void initPage(){
        pageList = new ArrayList<>();
        pageList.add(new HomePageFragment());
        pageList.add(new KOLPageFragment());
        pageList.add(new ShoppingPageFragment());
        pageList.add(new MyPageFragment());
    }
}