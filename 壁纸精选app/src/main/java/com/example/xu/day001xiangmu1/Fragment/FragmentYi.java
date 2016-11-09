package com.example.xu.day001xiangmu1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.xu.day001xiangmu1.NewOnActivity;
import com.example.xu.day001xiangmu1.R;
import com.example.xu.day001xiangmu1.Utils.UrlUtils;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Bean.TuijianGenxinBean;
import ImgCache.MyCustomRequest;

/**
 * Created by xu on 2016/10/27.
 */
public class FragmentYi extends Fragment{
    private PullToRefreshGridView prg;
    private ImageLoader loader;
    private RequestQueue queue;
    private Context context;
    private MyAdapter adapter;
    private List<TuijianGenxinBean.DataBean.WallpaperListInfoBean>list = new ArrayList<>();
    public static FragmentYi newInstance(String s){

        FragmentYi fargment = new FragmentYi();
        Bundle bundle =  new Bundle();
        bundle.putString("type",s);
        fargment.setArguments(bundle);

        return fargment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yi,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        queue = Volley.newRequestQueue(getActivity());
        context = getActivity();
        loader =ImageLoader.getInstance();
        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new LimitedAgeDiskCache(new File("/mnt/sdcard/xff1608"),24*3600))
                .memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()/8)))
                .build();

        loader.init(conf);

        initView(view);
        prg.getRefreshableView().setNumColumns(3);

        initData();

        initAdapter();




    }

    private void initAdapter() {
        adapter = new MyAdapter();
        prg.setAdapter(adapter);

    }

    private void initData() {

        MyCustomRequest<TuijianGenxinBean> genxinRequest = new MyCustomRequest<TuijianGenxinBean>(getArguments().getString("type"),
                TuijianGenxinBean.class,
                new Response.Listener<TuijianGenxinBean>() {
                    @Override
                    public void onResponse(TuijianGenxinBean response) {

                        list.addAll(response.getData().getWallpaperListInfo());
                        //0000000000000000000000000000000
                        adapter.notifyDataSetChanged();
                    }
                },
                null
        );
        queue.add(genxinRequest);

    }

    private void initView(View view) {

        prg = (PullToRefreshGridView) view.findViewById(R.id.pfg);
    }
    class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           final ViewHolder holder;
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.tuijian_genxin_item,parent,false);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_tuijian_genxin);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .build();



//            loader.displayImage(list.get(position).getWallPaperMiddle(),holder.iv,opts);
           loader.loadImage(list.get(position).getWallPaperMiddle(), opts,
                   new ImageLoadingListener() {
                       @Override
                       public void onLoadingStarted(String imageUri, View view) {

                       }

                       @Override
                       public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                       }

                       @Override
                       public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {

                           holder.iv.setImageBitmap(loadedImage);
                           holder.iv.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Intent intent = new Intent(context, NewOnActivity.class);
                                   intent.putExtra("pic",loadedImage);
                                   context.startActivity(intent);



                               }
                          });
                        }

                       @Override
                       public void onLoadingCancelled(String imageUri, View view) {

                       }
                   }
           );

            return convertView;
        }
        class ViewHolder{
            ImageView iv;
        }
    }
}
