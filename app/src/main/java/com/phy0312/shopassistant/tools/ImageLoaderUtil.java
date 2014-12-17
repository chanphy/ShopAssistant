package com.phy0312.shopassistant.tools;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.phy0312.shopassistant.R;

/**
 * description: <br/>
 * author: dingdj<br/>
 * date: 2014/11/26<br/>
 */
public class ImageLoaderUtil {

    public static DisplayImageOptions displayImageOptions;

    public static DisplayImageOptions newDisplayImageOptionsInstance(){
        if(displayImageOptions == null) {
            displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_stub)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                            //.displayer(new RoundedBitmapDisplayer(20))
                    .build();
        }
        return displayImageOptions;
    }

}
