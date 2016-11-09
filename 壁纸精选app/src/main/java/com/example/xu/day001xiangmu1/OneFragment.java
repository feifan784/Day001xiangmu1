package com.example.xu.day001xiangmu1;



import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xu.day001xiangmu1.Fragment.FragmentYi;
import com.example.xu.day001xiangmu1.Utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/10/24.
 */
public class OneFragment extends Fragment {


    private TabLayout layout;
    private ViewPager pager;

    private List<Fragment>fragmentList = new ArrayList<>();
    private MyAdapter adapter;
    private FragmentManager manager;
    private String[] titles ={"最新","热门","随机"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_one,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = getFragmentManager();

        initView(view);

        initData();

        initAdapter();


        layout.setSelectedTabIndicatorColor(Color.GREEN);
        layout.setSelectedTabIndicatorHeight(8);

        layout.setTabTextColors(Color.BLACK,Color.GREEN);
        layout.setTabMode(TabLayout.MODE_FIXED);
        layout.setTabGravity(TabLayout.GRAVITY_FILL);

        layout.setupWithViewPager(pager);


//        layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    private void initAdapter() {

        adapter = new MyAdapter(manager);
        pager.setAdapter(adapter);

    }

    private void initData() {

        for (int i = 0; i <3 ; i++) {
            fragmentList.add(FragmentYi.newInstance(UrlUtils.GENXINURL[i]));
        }

//        fragmentList.add(new FragmentEr());
//        fragmentList.add(new FragmentSan());
    }

    private void initView(View view) {

        layout = (TabLayout) view.findViewById(R.id.tablayout22);
        pager = (ViewPager) view.findViewById(R.id.viewpager22);


    }
    class MyAdapter extends FragmentStatePagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //?????????????????????????????????????????????????????????
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }

}
}