package com.example.xu.day001xiangmu1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xu.day001xiangmu1.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import Bean.SearchHotBean;

/**
 * Created by xu on 2016/10/26.
 */
public class MyHotSearchAdapter extends BaseAdapter {
    private List<SearchHotBean.DataBean>list;
    private LayoutInflater inflater;

    private ImageLoader loader;
    private DisplayImageOptions options;

    public MyHotSearchAdapter(List<SearchHotBean.DataBean> searchList, Context context, ImageLoader loader) {
        this.list = searchList;
        this.loader = loader;

        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.bottom_new)
                .build();




    }

    @Override
    public int getCount() {

      return list!=null?list.size():0;
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
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.lisview_three_item,parent,false);
            holder =new ViewHolder();
            holder.miv1 = (ImageView) convertView.findViewById(R.id.iv_list_1);
            holder.miv2 = (ImageView) convertView.findViewById(R.id.iv_list_2);
            holder.miv3 = (ImageView) convertView.findViewById(R.id.iv_list_3);
            holder.mtv = (TextView) convertView.findViewById(R.id.tv_listitem);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mtv.setText(list.get(position).getKeyword());
        loader.displayImage(list.get(position).getImgs().get(0),holder.miv1,options);
        loader.displayImage(list.get(position).getImgs().get(1),holder.miv2,options);
        loader.displayImage(list.get(position).getImgs().get(2),holder.miv3,options);
        return convertView;
    }
    class ViewHolder{
        ImageView miv1;
        ImageView miv2;
        ImageView miv3;
        TextView mtv;
    }
}
