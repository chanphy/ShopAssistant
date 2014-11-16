package com.phy0312.shopassistant.model;

import java.util.Date;

/**
 * 活动
 * Created by dingdj on 2014/11/16.
 */
public class HuoDong {
    private long id;
    private String title;
    private String iconUrl;
    private Date startDate;
    private Date endDate;

    public HuoDong(long id, String title, String iconUrl, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.iconUrl = iconUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
