package com.phy0312.shopassistant.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * description: <br/>
 * author: dingdongjin_91<br/>
 * date: 2014/12/16<br/>
 */
public class ShareContent {
    public String audioUrl;
    public String comment;
    public String description;
    public Bitmap image;
    public String imageUrl;
    public Bitmap thumbImage;
    public String title;
    public String tweet;
    public String videoUrl;
    public String webUrl;

    public ShareContent(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.comment = builder.comment;
        this.tweet = builder.tweet;
        this.image = builder.image;
        this.thumbImage = builder.thumbImage;
        this.imageUrl = builder.imageUrl;
        this.videoUrl = builder.videoUrl;
        this.audioUrl = builder.audioUrl;
        this.webUrl = builder.webUrl;
    }

    public static class Builder {
        public static final int COMMENT_MAX_LEN = 40;
        public static final int DESCRIPTION_MAX_LEN = 80;
        public static final int SNS_SHARE_THUMB_SIZE = 100;
        public static final int TITLE_MAX_LEN = 30;
        public static final int TWEET_MAX_LEN = 140;
        private String audioUrl;
        private String comment;
        private String description;
        private Bitmap image;
        private String imageUrl;
        private Bitmap thumbImage;
        private String title;
        private String tweet;
        private String videoUrl;
        private String webUrl;

        private void initEmptyFiledsWithDefaultValues() {
        }

        public ShareContent build() {
            initEmptyFiledsWithDefaultValues();
            return new ShareContent(this);
        }

        public Builder setAudioUrl(String audioUrl) {
            if ((TextUtils.isEmpty(audioUrl)) || ((!audioUrl.startsWith("http://")) && (!audioUrl.startsWith("https://")))) {
                this.audioUrl = null;
                return this;
            }
            this.audioUrl = audioUrl;
            return this;
        }

        public Builder setBitmap(Bitmap bitmap) {
            if ((bitmap != null) && (!bitmap.isRecycled()) && (bitmap.getWidth() > 0)) {
                this.image = bitmap;
                this.thumbImage = Bitmap.createScaledBitmap(this.image, 100, 100, true);
                return this;
            }
            this.image = null;
            this.thumbImage = null;
            return this;
        }

        public Builder setComment(String comment) {
            if (TextUtils.isEmpty(comment)) {
                this.comment = null;
                return this;
            }
            if (comment.length() > 40) {
                this.comment = comment.substring(0, 40);
                return this;
            }
            this.comment = comment;
            return this;
        }

        public Builder setDescription(String description) {
            if (TextUtils.isEmpty(description)) {
                this.description = null;
                return this;
            }
            if (description.length() > 80) {
                this.description = description.substring(0, 80);
                return this;
            }
            this.description = description;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            if ((TextUtils.isEmpty(imageUrl)) || ((!imageUrl.startsWith("http://")) && (!imageUrl.startsWith("https://")))) {
                this.imageUrl = null;
                return this;
            }
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setTitle(String title) {
            if (TextUtils.isEmpty(title)) {
                this.title = null;
                return this;
            }
            if (title.length() > 30) {
                this.title = title.substring(0, 30);
                return this;
            }
            this.title = title;
            return this;
        }

        public Builder setTweet(String tweet) {
            if (TextUtils.isEmpty(tweet)) {
                this.tweet = null;
                return this;
            }
            this.tweet = tweet;
            return this;
        }

        public Builder setVideoUrl(String videoUrl) {
            if ((TextUtils.isEmpty(videoUrl)) || ((!videoUrl.startsWith("http://")) && (!videoUrl.startsWith("https://")))) {
                this.videoUrl = null;
                return this;
            }
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder setWebUrl(String webUrl) {
            if ((TextUtils.isEmpty(webUrl)) || ((!webUrl.startsWith("http://")) && (!webUrl.startsWith("https://")))) {
                this.webUrl = null;
                return this;
            }
            this.webUrl = webUrl;
            return this;
        }
    }
}
