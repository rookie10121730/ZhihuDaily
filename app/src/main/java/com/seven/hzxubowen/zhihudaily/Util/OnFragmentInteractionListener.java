package com.seven.hzxubowen.zhihudaily.Util;

import java.util.ArrayList;

/**
 * Created by hzxubowen on 2016/7/22.
 */
public interface OnFragmentInteractionListener {
    //用于触发回调，进入到新的Activity
    void onFragmentInteraction(String newsId, ArrayList<String> idList);
}
