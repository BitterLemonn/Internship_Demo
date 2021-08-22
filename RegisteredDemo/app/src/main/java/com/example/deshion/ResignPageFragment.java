package com.example.deshion;

import android.annotation.SuppressLint;
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
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class ResignPageFragment extends Fragment implements View.OnClickListener {

    private AppContent app;

    private ImageView male;
    private ImageView female;
    private ImageView province_push;
    private ImageView city_push;
    private ImageView next;
    private TextView province;
    private TextView city;

    private CityPickerView city_picker = new CityPickerView();

    private boolean is_choose_sex;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.sign_up_page_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initListener();
    }

    private void initController() {
        app = (AppContent) getActivity().getApplication();

        city_picker.init(getContext());

        male = getActivity().findViewById(R.id.sign_male_icon);
        female = getActivity().findViewById(R.id.sign_female_icon);
        province_push = getActivity().findViewById(R.id.sign_province_push_down);
        city_push = getActivity().findViewById(R.id.sign_city_push_down);
        province = getActivity().findViewById(R.id.sign_province_choose);
        city = getActivity().findViewById(R.id.sign_city_choose);
        next = getActivity().findViewById(R.id.sign_next);

        male.setOnClickListener(this);
        female.setOnClickListener(this);
        province_push.setOnClickListener(this);
        city_push.setOnClickListener(this);
        province.setOnClickListener(this);
        city.setOnClickListener(this);
        next.setOnClickListener(this);

        //由返回上一步时恢复选择
        if (app.getSex() != null){
            switch (app.getSex()){
                case "male":{
                    male.setImageDrawable(getResources().getDrawable(R.drawable.male_choose));
                    female.setImageDrawable(getResources().getDrawable(R.drawable.female));
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                    is_choose_sex = true;
                    break;
                }
                case "female":{
                    female.setImageDrawable(getResources().getDrawable(R.drawable.female_choose));
                    male.setImageDrawable(getResources().getDrawable(R.drawable.male));
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                    is_choose_sex = true;
                    break;
                }
            }
        }
        if (app.getProvince() != null){
            province.setText(app.getProvince());
            city.setText(app.getCity());
        }
    }

    private void initListener(){
        CityConfig cityConfig = new CityConfig.Builder()
                .visibleItemsCount(7)
                .showBackground(true)
                .setCityWheelType(CityConfig.WheelType.PRO_CITY)
                .province("广东省")
                .city("广州市")
                .build();
        city_picker.setConfig(cityConfig);

        city_picker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean provinceBean, CityBean cityBean, DistrictBean districtBean) {
                province.setText(provinceBean.toString());
                city.setText(cityBean.toString());
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_male_icon: {
                male.setImageDrawable(getResources().getDrawable(R.drawable.male_choose));
                female.setImageDrawable(getResources().getDrawable(R.drawable.female));
                next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                is_choose_sex = true;
                app.setSex("male");
                break;
            }
            case R.id.sign_female_icon: {
                female.setImageDrawable(getResources().getDrawable(R.drawable.female_choose));
                male.setImageDrawable(getResources().getDrawable(R.drawable.male));
                next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                is_choose_sex = true;
                app.setSex("female");
                break;
            }
            case R.id.sign_next:{
                if (is_choose_sex){
                    app.setProvince(province.getText().toString());
                    app.setCity(city.getText().toString());
                    jumpFragment(new InvitePageFragment(), "invite_page");
                }else {
                    ToastUtils.getInstance(getContext()).showShortToast("请选择性别");
                }
                break;
            }
            case R.id.sign_province_push_down:
            case R.id.sign_city_push_down:
            case R.id.sign_province_choose:
            case R.id.sign_city_choose:{
                city_picker.showCityPicker();
                break;
            }
        }
    }

    private void jumpFragment(Fragment fragment, String tag){
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.sign_content, fragment, tag);
        transaction.commit();
    }

}