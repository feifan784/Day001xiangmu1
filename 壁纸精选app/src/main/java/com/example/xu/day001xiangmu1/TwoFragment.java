package com.example.xu.day001xiangmu1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Bean.FenleiBean;
import ImgCache.MyCustomRequest;
import ImgCache.MyImageCache;

/**
 * Created by xu on 2016/10/24.
 */
public class TwoFragment extends Fragment{
    private ListView lv;
    private List<FenleiBean.DataBean>list = new ArrayList<>();
    private MyAdapter adapter;
    private RequestQueue queue;
    private Bitmap mbitmap;
    private Context context;
    private com.nostra13.universalimageloader.core.ImageLoader load;
    private static String Zhuliebiao = "http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=category";

    public interface TextCallBack{
        void getText(String mess);
    }
    private TextCallBack callBack;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        callBack = (TextCallBack) context;
//    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        callBack = (TextCallBack) activity;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        //初始化callback
                //传值方法一
       // callBack = (TextCallBack) context;

        load = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        ImageLoaderConfiguration conf= new ImageLoaderConfiguration.Builder(getActivity())
                .diskCache(new LimitedAgeDiskCache(new File("/mnt/sdcard/xff1608"),24*3600))
                .memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()/8)))
                .build();

        load.init(conf);
        queue = Volley.newRequestQueue(getActivity());

        initView(view);

        initData();

        initAdapter();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context,NewTwoActivity.class);

                intent.putExtra("textName",list.get(position).getPicCategoryName());
                intent.putExtra("position",position);
                context.startActivity(intent);
    //                callBack.getText(list.get(position).getPicCategoryName());



            }
        });

    }

    private void initAdapter() {
        adapter = new MyAdapter();
        lv.setAdapter(adapter);



    }

    private void initData() {
        MyCustomRequest<FenleiBean>request = new MyCustomRequest<FenleiBean>(Zhuliebiao,
                FenleiBean.class,
                new Response.Listener<FenleiBean>() {
                    @Override
                    public void onResponse(FenleiBean response) {

                        list.addAll(response.getData());
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("xff", "onErrorResponse: "+error);
                    }
                }

        );
        queue.add(request);

    }

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv);
    }
    class MyAdapter extends BaseAdapter{
        private ImageLoader loader;
        public MyAdapter(){
            loader = new ImageLoader(queue, MyImageCache.getInstance());
        }

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_two,parent,false);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv_fr_two);
                holder.tv_one = (TextView) convertView.findViewById(R.id.textView);
                holder.tv_two = (TextView) convertView.findViewById(R.id.textView2);
                convertView.setTag(holder);


            }else {
                holder = (ViewHolder) convertView.getTag();

            }
            holder.tv_one.setText(list.get(position).getDescWords());
            holder.tv_two.setText(list.get(position).getPicCategoryName());
            holder.iv.setTag(list.get(position).getCategoryPic());


//            holder.iv.setDefaultImageResId(R.drawable.bottom_new_selected);
//            holder.iv.setErrorImageResId(R.drawable.bottom_new);


//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//
//                    .displayer(new RoundedBitmapDisplayer(20))
//                    .build();


//            load.displayImage(list.get(position).getCategoryPic(),holder.iv,options);



            //乱闪???
            DisplayImageOptions opts = new DisplayImageOptions.Builder()
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .build();
            holder.iv.setImageResource(R.mipmap.ic_launcher);
            load.loadImage(list.get(position).getCategoryPic(),opts,
                    new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                            //????????????????????????????????


                            if(holder.iv.getTag().toString().equals(imageUri)){

                                Bitmap bit = Bitmap.createBitmap(loadedImage.getWidth(),loadedImage.getHeight(), Bitmap.Config.ARGB_8888);

                                Canvas canvas  =  new Canvas(bit);

                                RectF rectf = new RectF(0,0,loadedImage.getWidth(),loadedImage.getHeight());

                                Paint paint = new Paint();
                                paint.setAntiAlias(true);
                                paint.setColor(Color.RED);

                                canvas.drawRoundRect(rectf, 50,50,paint);
                                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


                                canvas.drawBitmap(loadedImage,0,0,paint);


                                holder.iv.setImageBitmap(bit);

                            }

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
            TextView tv_one;
            TextView tv_two;
        }
    }
}
