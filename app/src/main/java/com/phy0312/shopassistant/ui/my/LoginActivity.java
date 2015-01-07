package com.phy0312.shopassistant.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.AndroidUtil;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.login_title));
        findViewById(R.id.iv_share).setVisibility(View.GONE);
        findViewById(R.id.tv_login_free_register).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, RegisterActivity.class);
                        AndroidUtil.startActivity(LoginActivity.this, intent);
                    }
                }
        );

        //后退按钮
        findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }
}
