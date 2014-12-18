package com.phy0312.shopassistant.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.phy0312.shopassistant.MainApplication;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.view.WarningInfoTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class UIUtil {


    public static final int NOMAL_ERR_VIEW = 0;

    public static final int LOADING_DATA_INFO_VIEW = 1;

    public static final int NET_SLOWLY_VIEW = 2;

    public static final int NET_BREAK_VIEW = 3;

    public static void initAdsBanner(final ViewPager viewPager) {
        final LayoutInflater mInflater = LayoutInflater.from(MainApplication.appContext);

        final List<String> datas = new ArrayList<String>();
        datas.add("http://jingdongquan.net/sites/default/files/imagecache/512/auto_img/2012/10/31/20121031124351.jpg");
        datas.add("http://www.yihaodianquan.com/sites/yihaodianquan.com/files/imagecache/512/2010/12/jizhongri-youhuiquan.jpg");
        datas.add("http://jingdongquan.net/sites/default/files/imagecache/512/2011/10/360buy-baihuo.jpg");

        viewPager.setTag(datas.size());
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                int realPostion = getRealPostion(position, datas.size());
                ImageView imageView = (ImageView)mInflater.inflate(R.layout.banner_ads, null);
                container.addView(imageView);
                ImageLoader.getInstance().displayImage(datas.get(realPostion), imageView, ImageLoaderUtil.newDisplayImageOptionsInstance());
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if(object instanceof View) {
                    container.removeView((View)object);
                }
            }
        });

        viewPager.setCurrentItem(Integer.MAX_VALUE/2);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(r, 5000);
    }

    public static int getRealPostion(int position, int count) {
        int realPostion = position - Integer.MAX_VALUE/2;
        if(realPostion >= 0) {
            realPostion = realPostion%count;
        }else{
            realPostion = realPostion%count + count-1;
        }
        return realPostion;
    }


    public static View getNetWorkInfoView(final Context context, View parentView, int flag) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_net_info_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_network_info);
        WarningInfoTextView textView = (WarningInfoTextView) view.findViewById(R.id.tv_warninginfo);
        switch (flag) {
            case NOMAL_ERR_VIEW:
                imageView.setBackgroundResource(R.drawable.err_info);
                textView.setText(context.getString(R.string.error_info));
                break;
            case LOADING_DATA_INFO_VIEW:
                imageView.setBackgroundResource(R.drawable.load_data);
                textView.startProcess(context.getResources().getString(R.string.loading_data));
                break;

            case NET_SLOWLY_VIEW:
                imageView.setBackgroundResource(R.drawable.net_slowly);
                textView.setText(context.getString(R.string.network_slow));
                Button refleshBtn = (Button) view.findViewById(R.id.btn_network_setting);
                refleshBtn.setVisibility(View.VISIBLE);
                refleshBtn.setText(context.getString(R.string.refresh));
                view.setTag(refleshBtn);
                break;
            case NET_BREAK_VIEW:
                imageView.setBackgroundResource(R.drawable.net_break);
                textView.setText(context.getString(R.string.network_break));
                Button btn = (Button) view.findViewById(R.id.btn_network_setting);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            AndroidUtil.startActivity(context, intent);
                        } catch (Exception e) {
                            Toast.makeText(context, context.getString(R.string.open_network_failed), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
        }

        if (parentView != null) {
            if (parentView instanceof RelativeLayout) {
                RelativeLayout parent = (RelativeLayout) parentView;
                parent.addView(view);
                RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(view.getLayoutParams());
                l.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                view.setLayoutParams(l);
            } else if (parentView instanceof LinearLayout) {
                LinearLayout parent = (LinearLayout) parentView;
                parent.addView(view);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(view.getLayoutParams());
                l.topMargin = AndroidUtil.dip2px(context, 50);
                view.setLayoutParams(l);
            }

        }

        return view;
    }

    /**
     * 获取NavDrawer主题
     * @param context
     * @return
     */
    public static int getNavDrawerWidth(Context context) {
        int width = AndroidUtil.getScreenWH(context)[0];
        return width - AndroidUtil.dip2px(context, 56);
    }

}
