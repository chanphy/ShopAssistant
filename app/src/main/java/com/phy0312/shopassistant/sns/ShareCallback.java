package com.phy0312.shopassistant.sns;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/16<br/>
 */
public interface ShareCallback {

    public abstract void OnSentComplete(int code, String msg);

    public abstract void OnSentFailed(int code, String msg);
}
