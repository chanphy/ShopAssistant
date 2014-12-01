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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.net.JsonCookieSupportRequest;
import com.phy0312.shopassistant.net.RequestResponseDataParseUtil;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.StringUtils;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
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

    private TextView tv_myprofile_password_manager;
    private TextView tv_myprofile_wx_bind;

    private TextView tv_myprofile_logout;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainSp mainSp = new MainSp(this.getActivity());
        if (!StringUtils.isEmpty(mainSp.getCookie())) {
            //自动登录
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        login_area_layout = (RelativeLayout) view.findViewById(R.id.login_area_layout);
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

        return view;
    }


    /**
     * 请求登录
     */
    private void requestLogin(String phoneNum, String pwd) {
        JsonCookieSupportRequest request = new JsonCookieSupportRequest(Request.Method.POST, URLManager.USER_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        new RequestResponseDataParseUtil.ResponseParse() {
                            @Override
                            public void parseResponseDataSection(JSONObject dataJsonObject) {//登录成功处理

                            }
                        }.onResponse(jsonObject);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        MainApplication.appContext.getRequestQueue().add(request);
    }

}
