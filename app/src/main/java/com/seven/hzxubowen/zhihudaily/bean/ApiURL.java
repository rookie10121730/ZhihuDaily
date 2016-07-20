package com.seven.hzxubowen.zhihudaily.bean;

/**
 * Created by hzxubowen on 2016/6/14.
 */
public class ApiURL {
    //主页Url
    public static final String homeUrl = "http://news-at.zhihu.com/api/4/news/latest";

    //获取内容Url，实际使用时在末尾加上news的Id
    public static final String contentUrl = "http://news-at.zhihu.com/api/4/news/";

    public static final String ZHIHU_LATEST_URL = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String ZHIHU_PAST_BASE = "http://news.at.zhihu.com/api/4/news/before/";

    //单项主题日报
    public static final String ZHIHU_TITLE_DAILY = "http://news-at.zhihu.com/api/4/theme/";
    //主题日报列表
    public static final String ZHIHU_TITLE_LIST = "http://news-at.zhihu.com/api/4/themes";


}
