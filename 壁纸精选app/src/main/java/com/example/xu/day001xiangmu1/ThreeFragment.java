package com.example.xu.day001xiangmu1;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.xu.day001xiangmu1.Adapter.MyHotSearchAdapter;
import com.example.xu.day001xiangmu1.Adapter.MyViewPagerAdapter;
import com.example.xu.day001xiangmu1.Utils.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import Bean.SearchHotBean;
import Bean.ViewpagerBean;
import ImgCache.MyCustomRequest;

/**
 * Created by xu on 2016/10/24.
 */
public class ThreeFragment extends Fragment{

    private LinearLayout hotSearchLayout;
    private ViewPager pager;
    private RadioGroup group;
    private ListView lv;
    private List<SearchHotBean.DataBean>searchList= new ArrayList<>();
    private List<SearchHotBean.DataBean>listviewList= new ArrayList<>();
    private List<ViewpagerBean.DataBean.TopicBean>topicList = new ArrayList<>();
    private List<View>imgList = new ArrayList<>();
    private MyHotSearchAdapter adapter;
    private ImageLoader loader;
    private Context context;
    private RequestQueue queue;
    private HorizontalScrollView hsv;
    private MyViewPagerAdapter adapter2;
    private Handler handler = new Handler();
    private Runnable r;
    private boolean flag = false;
    private Button but;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getActivity();

        initView(view);
        queue = Volley.newRequestQueue(context);
        initLoader();


        initHotSearchData();

        initListViewData();

        initViewPager();

        initListView();


        //按钮的点击事件
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),ThreeFrToActivity.class));

            }
        });



        //
        pager.setCurrentItem(2000);
        r = new Runnable() {
            @Override
            public void run() {
                pager.setCurrentItem(pager.getCurrentItem()+1);
                handler.postDelayed(r,2000);
            }
        };
        handler.postDelayed(r,2000);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton rb  = (RadioButton) group.getChildAt(position%imgList.size());
                group.check(rb.getId());

//                int tag = Integer.parseInt(group.getChildAt(position).toString());
//
//                group.check(tag);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                switch(state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (flag){
                            handler.postDelayed(r,2000);
                            flag = false;
                        }

                    break;
                    case ViewPager.SCROLL_STATE_SETTLING:


                    break;
                    case ViewPager.SCROLL_STATE_DRAGGING:

                        handler.removeCallbacks(r);
                        flag = true;

                        break;

                }

            }
        });

    }

    private void initViewPager() {
        initPagerData();




    }

    private void initPagerAdapter() {
        //???????????????
        adapter2  =new MyViewPagerAdapter(imgList);
        pager.setAdapter(adapter2);
    }

    private void initPagerData() {

        MyCustomRequest<ViewpagerBean> imageRequest = new MyCustomRequest<ViewpagerBean>(UrlUtils.IMAGEURL,
                ViewpagerBean.class,
                new Response.Listener<ViewpagerBean>() {
                    @Override
                    public void onResponse(ViewpagerBean response) {
                        topicList.addAll(response.getData().getTopic());

                        initRadioButton();

                        initPagerAdapter();

                        adapter2.notifyDataSetChanged();


                    }
                },
                null
        );
        queue.add(imageRequest);


    }

    private void initRadioButton() {
        int num = 0;
        if (topicList.size()%2==0){
            num = topicList.size()/2;
        }else {
            num = topicList.size()/2+1;
        }
        for (int i = 0; i <num ; i++) {
            RadioButton rb = new RadioButton(context);
            rb.setId(i);
            rb.setClickable(false);
            group.addView(rb);
            group.check(i);

            View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item,null);
            ImageView iv1 = (ImageView) view.findViewById(R.id.iv_pager_1);
            ImageView iv2 = (ImageView) view.findViewById(R.id.iv_pager_2);
            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .displayer(new RoundedBitmapDisplayer(20))
                    .build();
            loader.displayImage(topicList.get(2*i).getCover_path(),iv1,opts);
            try{
                loader.displayImage(topicList.get(2*i+1).getCover_path(),iv2,opts);
            }catch (Exception e){
                iv2.setVisibility(View.GONE);
            }

           //?????????????????????????????
//            view.setTag(i);
            imgList.add(view);

        }
    }

    private void initListView() {
        initLiatViewAdapter();
    }

    private void initListViewData() {
        MyCustomRequest<SearchHotBean>listRequsext = new MyCustomRequest<SearchHotBean>(UrlUtils.LISTVIEWURL,
                SearchHotBean.class,
                new Response.Listener<SearchHotBean>() {
                    @Override
                    public void onResponse(SearchHotBean response) {
                        listviewList.addAll(response.getData());
                        adapter.notifyDataSetChanged();

                    }
                },null
        );
        queue.add(listRequsext);

    }

    private void initLoader() {
        loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(context));
    }

    private void initLiatViewAdapter() {
        adapter = new MyHotSearchAdapter(listviewList,context,loader);
        lv.setAdapter(adapter);


        pager.setFocusable(true);
        lv.setFocusable(false);

    }

    private void initHotSearchData() {
           MyCustomRequest<SearchHotBean> hotRequest = new MyCustomRequest<SearchHotBean>(UrlUtils.HOTSEARCHURL,
                   SearchHotBean.class,
                   new Response.Listener<SearchHotBean>() {
                       @Override
                       public void onResponse(SearchHotBean response) {
                           searchList.addAll(response.getData());
                           initHorizontal();

                       }
                   },null
           );
        queue.add(hotRequest);


    }

    private void initHorizontal() {
        for (int i = 0; i <searchList.size() ; i++) {

            View hitView = LayoutInflater.from(context).inflate(R.layout.hotsearch_item,null);
            ImageView mmiv = (ImageView) hitView.findViewById(R.id.iv_hotitem);
            TextView mmtv = (TextView) hitView.findViewById(R.id.tv_hotitem);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .displayer(new RoundedBitmapDisplayer(20))
                    .build();
            loader.displayImage(searchList.get(i).getImgs().get(0),mmiv,options);
            hotSearchLayout.addView(hitView);

            mmtv.setText(searchList.get(i).getKeyword());

        }


    }

    private void initView(View view) {
        hotSearchLayout = (LinearLayout) view.findViewById(R.id.hostsearch_layout);
        pager = (ViewPager) view.findViewById(R.id.viewpager22);
        group = (RadioGroup) view.findViewById(R.id.group);
        lv= (ListView) view.findViewById(R.id.lv_three);
        hsv = (HorizontalScrollView) view.findViewById(R.id.hsv);
        but = (Button) view.findViewById(R.id.button);

    }


}
