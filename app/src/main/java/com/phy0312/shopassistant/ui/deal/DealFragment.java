package com.phy0312.shopassistant.ui.deal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.net.URLManager;
import com.phy0312.shopassistant.tools.AndroidUtil;
import com.phy0312.shopassistant.ui.base.BaseFragment;

public class DealFragment extends BaseFragment {

    private static final String TAG = "DealFragment";

    private WebView webView;

    public DealFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deal, container, false);
        webView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        final ProgressDialog progressBar = ProgressDialog.show(getActivity(), getActivity().getString(R.string.app_name),
                getActivity().getString(R.string.loading));
        progressBar.show();
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.show();
            }
        });
        if (AndroidUtil.isNetworkAvailable(this.getActivity())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.loadUrl(URLManager.DEAL_URL);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            webView.loadUrl(URLManager.DEAL_URL);
        }
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }
}
