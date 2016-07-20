package com.seven.hzxubowen.zhihudaily.bean;

/**
 * Created by hzxubowen on 2016/6/24.
 */
public class NewsContent {
    //日报详细内容
    private String newsTitle;
    private String Image_url;
    private String Image_src;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getImage_src() {
        return Image_src;
    }

    public void setImage_src(String image_src) {
        Image_src = image_src;
    }

    public NewsContent(String newsTitle, String image_url, String image_src) {

        this.newsTitle = newsTitle;
        Image_url = image_url;
        Image_src = image_src;
    }
}
