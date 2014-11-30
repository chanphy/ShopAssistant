package com.phy0312.shopassistant.activity;


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

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MyProfileFragment extends Fragment {

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
    private TextView tv_myprofile_member_card;

    private TextView tv_myprofile_password_manager;
    private TextView tv_myprofile_pay_password_manager;

    private TextView tv_myprofile_wx_bind;
    private TextView tv_myprofile_logout;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }


}
