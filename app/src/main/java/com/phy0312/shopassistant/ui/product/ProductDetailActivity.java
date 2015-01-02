package com.phy0312.shopassistant.ui.product;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.model.Product;
import com.phy0312.shopassistant.ui.share.ShareActivity;

/**
 * description: 商品详情<br/>
 * author: dingdj<br/>
 * date: 2014/12/5<br/>
 */
public class ProductDetailActivity extends Activity {

    public static final  String EXTRA_KEY_PRODUCT_ID = "product_id";

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detail_product);
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.product_detail));

        //后退按钮
        findViewById(R.id.iv_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailActivity.this.finish();
            }
        });

        //分享按钮
        findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(ShareActivity.buildIntent(ProductDetailActivity.this, 4, "", "商品", "商品描述", "http://pic25.nipic.com/20121119/11328459_121121530346_2.jpg"));
            }
        });
    }
}
