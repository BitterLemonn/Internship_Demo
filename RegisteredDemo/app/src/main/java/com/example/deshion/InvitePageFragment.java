package com.example.deshion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.deshion.Utils.AppContent;
import com.example.deshion.Utils.ToastUtils;

public class InvitePageFragment extends Fragment implements View.OnClickListener{
    private EditText invite_key_edit;
    private TextView skip;
    private TextView previous;
    private ImageView next;

    private String invite_key;
    private boolean is_invited;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private AppContent app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.invite_page_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initController();
        initListener();
    }

    private void initController(){
        app = (AppContent) getActivity().getApplication();

        invite_key_edit = getActivity().findViewById(R.id.invite_key_edit);
        skip = getActivity().findViewById(R.id.invite_skip);
        previous = getActivity().findViewById(R.id.invite_last);
        next = getActivity().findViewById(R.id.invite_next);

        skip.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void initListener(){
        invite_key_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(invite_key_edit.length() > 0) {
                    is_invited = true;
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next_can));
                }else {
                    is_invited = false;
                    next.setImageDrawable(getResources().getDrawable(R.drawable.next));
                }
                invite_key = invite_key_edit.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.invite_last:{
                jumpFragment(new ResignPageFragment(), "sign_up_main");
                break;
            }
            case R.id.invite_skip:{
                jumpFragment(new TagPageFragment(), "tag_choose");
                break;
            }
            case R.id.invite_next:{
                if(is_invited){
                    //检测邀请码是否存在
                    if (invite_key.equals("yaoqingma")){
                        app.setInvite_key(invite_key);
                        jumpFragment(new TagPageFragment(), "tag_choose");
                    }else {
                        ToastUtils.getInstance(getContext()).showShortToast("填写的邀请码不正确");
                    }
                }else {
                    ToastUtils.getInstance(getContext()).showShortToast("请填写邀请码或跳过");
                }
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
