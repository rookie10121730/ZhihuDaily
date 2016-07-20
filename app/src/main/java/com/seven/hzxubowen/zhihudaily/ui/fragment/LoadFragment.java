package com.seven.hzxubowen.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.hzxubowen.zhihudaily.R;

/**
 * Created by hzxubowen on 2016/7/19.
 */
public class LoadFragment extends BaseFragment {

    public LoadFragment(){

    }

    public static LoadFragment newInstance(){
        LoadFragment loadFragment = new LoadFragment();
        return loadFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setStyle(DialogFragment.STYLE_NO_TITLE, );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.fr_load, null);

    }


}
