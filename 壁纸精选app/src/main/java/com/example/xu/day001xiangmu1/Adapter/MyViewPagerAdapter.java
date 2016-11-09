package com.example.xu.day001xiangmu1.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/10/26.
 */
public class MyViewPagerAdapter extends PagerAdapter{
    private List<View>imgList ;

    public MyViewPagerAdapter(List<View> imgList) {
        this.imgList = imgList;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imgList.get(position%imgList.size()));
        return imgList.get(position%imgList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgList.get(position%imgList.size()));
    }
}
