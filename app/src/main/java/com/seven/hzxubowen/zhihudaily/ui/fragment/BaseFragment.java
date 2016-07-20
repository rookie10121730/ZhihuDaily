package com.seven.hzxubowen.zhihudaily.ui.fragment;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
/**
 * Created by hzxubowen on 2016/7/19.
 */
public class BaseFragment extends DialogFragment {

    private LoadFragment loadingFragment;

    public void showLoadingFragment(String text) {
        loadingFragment = LoadFragment.newInstance();
        loadingFragment.show(getActivity().getSupportFragmentManager(), "loading");
    }

    public void dismissLoadingFragment() {
        if (loadingFragment != null) {
            loadingFragment.dismissAllowingStateLoss();
            loadingFragment = null;
        }
    }



}
