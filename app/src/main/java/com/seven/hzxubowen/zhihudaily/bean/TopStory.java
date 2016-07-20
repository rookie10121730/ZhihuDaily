package com.seven.hzxubowen.zhihudaily.bean;

/**
 * Created by hzxubowen on 2016/6/14.
 */

/**
 * 存储首页滚动新闻列表中的关键信息
 */

public class TopStory {
    private String newsTitle;
    private String imageUrl;
    private String newsId;

    public TopStory(String newsTitle, String imageUrl, String newsId){
        this.newsTitle = newsTitle;
        this.imageUrl = imageUrl;
        this.newsId = newsId;
    }



    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
