package com.phy0312.shopassistant.ui.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.phy0312.shopassistant.R;
import com.phy0312.shopassistant.config.MainSp;
import com.phy0312.shopassistant.model.ShareContent;
import com.phy0312.shopassistant.sns.ShareCallback;
import com.phy0312.shopassistant.tools.Constants;
import com.phy0312.shopassistant.tools.ImageLoaderUtil;
import com.phy0312.shopassistant.tools.WeChatShareUtil;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/16<br/>
 */
public class ShareActivity extends Activity {

    public static final int SHAERE_COUPON = 1;
    public static final int SHAERE_ACTIVITY = 2;
    public static final int SHAERE_DEAL = 3;
    public static final int SHAERE_PRODUCT = 4;

    private String mContent;
    private String mDescription;
    private String mImageUrl;
    private String mLinkAddress;
    private String mShareId;
    private int mShareType;
    private String mTitle;
    private final int WXCHAT = 1;
    private final int WXCHAT_TIMELINE = 2;

    public static Intent buildIntent(Context context, int shareType, String shareId, String shareTitle, String shareDesc, String shareImageUrl) {
        return buildIntent(context, shareType, shareId, shareTitle, shareDesc, shareImageUrl, "");
    }

    public static Intent buildIntent(Context context, int shareType, String shareId, String shareTitle, String shareDesc, String shareImageUrl, String shareWebUrl) {
        Intent localIntent = new Intent(context, ShareActivity.class);
        localIntent.putExtra("share_type_key", shareType);
        localIntent.putExtra("share_id_key", shareId);
        localIntent.putExtra("share_title", shareTitle);
        localIntent.putExtra("share_description_key", shareDesc);
        localIntent.putExtra("share_image_url_key", shareImageUrl);
        localIntent.putExtra("share_web_url_key", shareWebUrl);
        return localIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().gravity = 81;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        contentView.setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        setContentView(contentView);

        Intent localIntent = getIntent();
        this.mShareType = localIntent.getIntExtra("share_type_key", 0);
        this.mShareId = localIntent.getStringExtra("share_id_key");
        this.mImageUrl = localIntent.getStringExtra("share_image_url_key");
        this.mLinkAddress = localIntent.getStringExtra("share_web_url_key");
        this.mTitle = localIntent.getStringExtra("share_title");
        this.mDescription = localIntent.getStringExtra("share_description_key");
        OnShareChannelClickedListener localOnShareChannelClickedListener = new OnShareChannelClickedListener();
        findViewById(R.id.share_wechat).setOnClickListener(localOnShareChannelClickedListener);
        findViewById(R.id.share_wechat_timeline).setOnClickListener(localOnShareChannelClickedListener);
        findViewById(R.id.share_qq).setOnClickListener(localOnShareChannelClickedListener);
        findViewById(R.id.share_weibo).setOnClickListener(localOnShareChannelClickedListener);
        findViewById(R.id.share_cancel).setOnClickListener(localOnShareChannelClickedListener);
    }

    private void share(final int type) {
        ImageLoader.getInstance().loadImage(this.mImageUrl, ImageLoaderUtil.displayImageOptions, new
                ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        ShareActivity.this.share(type, BitmapFactory.decodeResource(ShareActivity.this.getResources(), R.drawable.ic_launcher));
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        if (loadedImage == null) {
                            loadedImage = BitmapFactory.decodeResource(ShareActivity.this.getResources(), R.drawable.ic_launcher);
                        }
                        ShareActivity.this.share(type, loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        ShareActivity.this.share(type, BitmapFactory.decodeResource(ShareActivity.this.getResources(), R.drawable.ic_launcher));
                    }
                });
    }

    private void share(int type, Bitmap bitmap) {
        if (type == WXCHAT) {//微信
            shareWeChat(WeChatShareUtil.getWebPageShareContent(this.mLinkAddress + "-tweixin", this.mTitle, this.mDescription, bitmap), false);
        } else if (type == WXCHAT_TIMELINE) {//微信朋友圈
            shareWeChat(WeChatShareUtil.getWebPageShareContent(this.mLinkAddress + "-tweixin", this.mTitle, this.mDescription, bitmap), true);
        }
    }

    private void shareWeChat(ShareContent shareContent, boolean isWXTimeLine) {
        WeChatShareUtil.share(this, shareContent, getShareCallback(), isWXTimeLine, R.string.wechat_not_install);
    }

    private ShareCallback getShareCallback() {
        return new ShareCallback() {
            public void OnSentComplete(int code, String msg) {
                Toast.makeText(ShareActivity.this.getApplicationContext(), R.string.share_success, Toast.LENGTH_SHORT).show();
                if (!ShareActivity.this.isFinishing()) {
                    ShareActivity.this.setResult(-1);
                    ShareActivity.this.finish();
                }
            }

            public void OnSentFailed(int code, String msg) {
                Toast.makeText(ShareActivity.this.getApplicationContext(), R.string.share_failed, Toast.LENGTH_SHORT).show();
                if (!ShareActivity.this.isFinishing()) {
                    ShareActivity.this.finish();
                }
            }
        };
    }


    class OnShareChannelClickedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.share_wechat:
                    buildShareLinkAddr();
                    share(WXCHAT);
                    ShareActivity.this.finish();
                    break;
                case R.id.share_wechat_timeline:
                    buildShareLinkAddr();
                    share(WXCHAT_TIMELINE);
                    ShareActivity.this.finish();
                    break;
                case R.id.share_qq:
                    buildShareLinkAddr();
                    ShareContent.Builder builder = new ShareContent.Builder();
                    builder.setTitle(ShareActivity.this.mTitle);
                    builder.setWebUrl(ShareActivity.this.mLinkAddress + "-tqq");
                    builder.setComment(ShareActivity.this.mLinkAddress);
                    builder.setDescription(ShareActivity.this.mDescription);
                    builder.setImageUrl(ShareActivity.this.mImageUrl);
                    ShareContent shareContent = builder.build();
                    break;
                case R.id.share_weibo:
                    buildShareLinkAddr();
                    break;
                case R.id.share_cancel:
                    ShareActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }


    private void buildShareLinkAddr() {
        String plazaId = MainSp.getInstance(this).getPlazaId();
        String userName = MainSp.getInstance(this).getUserName();
        String shareLinkServerurl = Constants.SHARE_LINK_SERVERURL;
        switch (this.mShareType) {
            case SHAERE_COUPON:
                this.mContent = getString(R.string.share_content_coupon_ticket);
                Object[] objects = new Object[4];
                objects[0] = shareLinkServerurl;
                objects[1] = plazaId;
                objects[2] = this.mShareId;
                objects[3] = userName;
                this.mLinkAddress = String.format("%1$s/coupon/detail-w%2$s-c%3$s/?from=share-pandroid-u%4$s", objects);
                return;
            case SHAERE_ACTIVITY:
                this.mContent = getString(R.string.share_content_activity);
                Object[] arrayOfObject4 = new Object[4];
                arrayOfObject4[0] = shareLinkServerurl;
                arrayOfObject4[1] = plazaId;
                arrayOfObject4[2] = this.mShareId;
                arrayOfObject4[3] = userName;
                this.mLinkAddress = String.format("%1$s/event/detail-w%2$s-a%3$s/?from=share-pandroid-u%4$s", arrayOfObject4);
                return;
            case SHAERE_DEAL:
                this.mContent = getString(R.string.share_content_deal_ticket);
                Object[] arrayOfObject3 = new Object[4];
                arrayOfObject3[0] = shareLinkServerurl;
                arrayOfObject3[1] = plazaId;
                arrayOfObject3[2] = this.mShareId;
                arrayOfObject3[3] = userName;
                this.mLinkAddress = String.format("%1$s/tuangou/detail-w%2$s-d%3$s/?from=share-pandroid-u%4$s", arrayOfObject3);
                return;
            case SHAERE_PRODUCT:
                this.mContent = getString(R.string.share_content_product);
                Object[] arrayOfObject2 = new Object[4];
                arrayOfObject2[0] = shareLinkServerurl;
                arrayOfObject2[1] = plazaId;
                arrayOfObject2[2] = this.mShareId;
                arrayOfObject2[3] = userName;
                this.mLinkAddress = String.format("%1$s/product/detail-w%2$s-p%3$s/?from=share-pandroid-u%4$s", arrayOfObject2);
                return;
            default:
                return;
        }
    }
}
