package com.example.xu.day001xiangmu1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.xu.day001xiangmu1.Fragment.FragmentYi;
import com.example.xu.day001xiangmu1.Utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import Bean.FenleiBean;

import ImgCache.ListLoadData;
import ImgCache.MyCustomRequest;

public class NewTwoActivity extends AppCompatActivity implements TwoFragment.TextCallBack, ListLoadData.ListCallBack {

    private TextView tv_title;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private List<Fragment>list = new ArrayList<>();
    private MyAdapter adapter;
    private String[] titles ={"最新","热门","随机"};
    private List<FenleiBean.DataBean>inList = new ArrayList<>();
    private String name;
    private int position;
    private RequestQueue queue;

    private static String Zhuliebiao = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_one);


        queue= Volley.newRequestQueue(this);

        initView();

        initData();

        initAdapter();

        tabLayout.setTabTextColors(Color.BLACK,Color.GREEN);
        tabLayout.setSelectedTabIndicatorHeight(8);
        tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewpager);

        tv_title = (TextView) findViewById(R.id.tv_ssb);
        Intent intent = getIntent();
        name = intent.getStringExtra("textName");
        position = intent.getIntExtra("position",0);
        tv_title.setText(name);



    }

    private void initAdapter() {
        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);

    }

    private void initData() {
        //得到网络数据
        new ListLoadData(this).execute(Zhuliebiao);

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_ssb);
        tabLayout = (TabLayout) findViewById(R.id.tablayout22);
        viewpager  = (ViewPager) findViewById(R.id.viewpager22);

    }

    @Override
    public void callBack(List<FenleiBean.DataBean> result) {
        inList.addAll(result);
//        Log.i("xff", "callBack: "+inList.toString());
        for (int i =0;i<3;i++){
            //000000000000000000000000000网址

            //必须得到传回来的position值
//            Log.i("xff", "callBack: |||||"+UrlUtils.FENLEIURLS[i]+inList.get(position).getID());

            list.add(FragmentYi.newInstance(UrlUtils.FENLEIURLS[i]+inList.get(position).getID()));
//            list.add(FragmentYi.newInstance(UrlUtils.FENLEIURLS[i]));
        }
        //因为要刷新，必须刷新
        adapter.notifyDataSetChanged();

    }

    class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public void getText(String mess) {


//        tv_title.setText(mess);
    }
}
