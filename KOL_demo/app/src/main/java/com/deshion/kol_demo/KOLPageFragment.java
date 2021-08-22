package com.deshion.kol_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class KOLPageFragment extends Fragment {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private TabLayout kol_switcher;
    private ViewPager kol_container;

    private EditText kol_search_field;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kol_studio_page_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initAdapter();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initController(){
        fragments = new ArrayList<>();
        fragments.add(new FollowedKOLFragment());
        fragments.add(new AllTagFragment());

        kol_switcher = getActivity().findViewById(R.id.kol_tab_layout);
        kol_container = getActivity().findViewById(R.id.kol_contain);
        kol_search_field = getActivity().findViewById(R.id.kol_search_field);
        kol_search_field.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.kol_search_field && event.getAction() == MotionEvent.ACTION_DOWN){
                    startActivity(new Intent(getContext(), SearchActivity.class));
                    return true;
                }
                return false;
            }
        });

        titles = new ArrayList<>();
        titles.add(getString(R.string.follow));
        titles.add(getString(R.string.all));
    }

    private void initAdapter(){
        kol_container.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        kol_switcher.setupWithViewPager(kol_container);
        kol_switcher.getTabAt(0).select();
    }
}
