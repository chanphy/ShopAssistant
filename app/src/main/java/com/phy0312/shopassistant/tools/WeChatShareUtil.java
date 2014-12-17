package com.phy0312.shopassistant.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.phy0312.shopassistant.model.ShareContent;
import com.phy0312.shopassistant.sns.ShareCallback;

import com.phy0312.shopassistant.ui.share.ShareActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXMusicObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/16<br/>
 */
public class WeChatShareUtil {

    public static ShareContent getWebPageShareContent(String webUrl, String title, String description, Bitmap bitmap)
    {
        ShareContent.Builder localBuilder = new ShareContent.Builder();
        localBuilder.setWebUrl(webUrl);
        localBuilder.setTitle(title);
        localBuilder.setDescription(description);
        localBuilder.setBitmap(bitmap);
        return localBuilder.build();
    }

    public static boolean share(Activity activity, ShareContent shareContent, ShareCallback shareCallback, boolean isTimeLine, int resId)
    {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(activity.getApplicationContext(), Constants.WX_APP_ID, false);

        if (!WXUtil.isSupportWX(activity.getApplicationContext()))
        {
            if (resId != 0) {
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        if ((isTimeLine) && (!WXUtil.isSupportTimeLine(activity.getApplicationContext())))
        {
            if (resId != 0) {
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        wxapi.registerApp(Constants.WX_APP_ID);
        WXWebpageObject webpageObject = new WXWebpageObject();
        if (shareContent.webUrl != null) {
            webpageObject.webpageUrl = shareContent.webUrl;
        }

        WXMediaMessage wxMediaMessage = new WXMediaMessage((WXMediaMessage.IMediaObject)webpageObject);
        if (shareContent.title != null) {
            wxMediaMessage.title = shareContent.title;
        }
        if (shareContent.description != null) {
            wxMediaMessage.description = shareContent.description;
        }
        ByteArrayOutputStream byteArrayOutputStream;
        if (shareContent.thumbImage != null)
        {
            byteArrayOutputStream = new ByteArrayOutputStream();
            shareContent.thumbImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            wxMediaMessage.thumbData = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        //ShareActivity.setShareCallback(shareCallback);
        wxapi.sendReq(req);
        return true;
    }
}
