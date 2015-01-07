package com.phy0312.shopassistant.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.config.Global;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends BaseFragment {


    private RelativeLayout login_area_layout;
    private TextView tv_myprofile_login;
    private TextView tv_myprofile_desc;
    private Button btn_myprofile_register;
    private RelativeLayout login_data_layout;
    private TextView tv_myprofile_name;
    private ImageView iv_myprofile_grade;
    private ImageView iv_right_arrow;
    private TextView tv_myprofile_point;


    private TextView tv_myprofile_ticket_package;
    private TextView tv_myprofile_ticket;
    private TextView tv_myprofile_order_manager;
    private TextView tv_myprofile_points_detail;

    private TextView tv_myprofile_password_manager;
    private TextView tv_myprofile_wx_bind;

    private TextView tv_myprofile_logout;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        login_area_layout = (RelativeLayout) view.findViewById(R.id.login_area_layout);
        login_data_layout = (RelativeLayout) view.findViewById(R.id.login_data_layout);
        tv_myprofile_login = (TextView) view.findViewById(R.id.tv_myprofile_login);
        tv_myprofile_desc = (TextView) view.findViewById(R.id.tv_myprofile_desc);
        tv_myprofile_name = (TextView) view.findViewById(R.id.tv_myprofile_name);
        tv_myprofile_point = (TextView) view.findViewById(R.id.tv_myprofile_point);
        tv_myprofile_ticket_package = (TextView) view.findViewById(R.id.tv_myprofile_ticket_package);
        tv_myprofile_ticket = (TextView) view.findViewById(R.id.tv_myprofile_ticket);
        tv_myprofile_order_manager = (TextView) view.findViewById(R.id.tv_myprofile_order_manager);
        tv_myprofile_points_detail = (TextView) view.findViewById(R.id.tv_myprofile_points_detail);
        tv_myprofile_password_manager = (TextView) view.findViewById(R.id.tv_myprofile_password_manager);
        tv_myprofile_wx_bind = (TextView) view.findViewById(R.id.tv_myprofile_wx_bind);
        tv_myprofile_logout = (TextView) view.findViewById(R.id.tv_myprofile_logout);

        btn_myprofile_register = (Button) view.findViewById(R.id.btn_myprofile_register);
        iv_myprofile_grade = (ImageView) view.findViewById(R.id.iv_myprofile_grade);
        iv_right_arrow = (ImageView) view.findViewById(R.id.iv_right_arrow);

        tv_myprofile_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getActivity(), LoginActivity.class.getName());
                AndroidUtil.startActivity(getActivity(), intent);
            }
        });

        btn_myprofile_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getActivity(), RegisterActivity.class.getName());
                AndroidUtil.startActivity(getActivity(), intent);
            }
        });

        updateView();

        tv_myprofile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.userInfo = null;
                MainSp.getInstance(getActivity()).setCookie("");
                updateView();
            }
        });
        return view;
    }


    private void updateView() {
        if(Global.hasLogin()) {
            login_area_layout.setVisibility(View.GONE);
            login_data_layout.setVisibility(View.VISIBLE);
            tv_myprofile_name.setText(Global.userInfo.getUserName());
            tv_myprofile_logout.setVisibility(View.VISIBLE);
        }else{
            login_area_layout.setVisibility(View.VISIBLE);
            login_data_layout.setVisibility(View.GONE);
            tv_myprofile_logout.setVisibility(View.GONE);
        }
    }

}
