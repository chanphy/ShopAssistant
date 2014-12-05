package com.phy0312.shopassistant.ui.base;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.phy0312.shopassistant.view.PullToRefreshLayout;

/**
 * description: 支持下拉刷新 点击的Fragment<br/>
 * author: dingdj<br/>
 * date: 2014/12/5<br/>
 */
public abstract class BaseFragment extends Fragment implements PullToRefreshLayout.PullRefreshListener,
        AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener{

    abstract protected void startLoad(final boolean isUp, final boolean isBottom, final boolean append);

    @Override
    public void onRefreshingUp() {
        startLoad(true, false, true);
    }

    @Override
    public void onRefreshingBottom() {
        startLoad(false, true, true);
    }

}
