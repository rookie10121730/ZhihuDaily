package com.seven.hzxubowen.zhihudaily.bean;

/**
 * Created by hzxubowen on 2016/6/14.
 */

/**
 * 存储首页新闻列表中的关键信息
 */

public class Story {
    //private String date;
    private String dailyTitle;
    private String imageUrl;
    private String storyId;

    public Story(String dailyTitle, String imageUrl, String newsId){
    //    this.date = date;
        this.dailyTitle = dailyTitle;
        this.imageUrl = imageUrl;
        this.storyId = newsId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String newsId) {
        this.storyId = newsId;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
