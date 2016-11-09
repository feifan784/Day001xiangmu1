package com.example.xu.day001xiangmu1;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.xu.day001xiangmu1.SQLite.MyHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import Bean.Bzjx;

public class ShoucangActivity extends AppCompatActivity {

    private GridView gv;
    private MyHelper helper;
    private List<Bzjx> list = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoucang);
        helper = new MyHelper(this);
        initView();

        initData();

        initAdapter();


    }

    private void initAdapter() {

        adapter = new MyAdapter();
        gv.setAdapter(adapter);
    }

    private void initData() {
        list = helper.query();


    }

    private void initView() {
        gv = (GridView) findViewById(R.id.gv);

    }



    class MyAdapter extends BaseAdapter {


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
            ViewHolder holder;

            if(convertView == null){
                convertView = LayoutInflater.from(ShoucangActivity.this).inflate(R.layout.item_gv,parent,false);
                holder = new ViewHolder();

                holder.iv = (ImageView) convertView.findViewById(R.id.iv_gv_one);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }


            byte[] buffer = list.get(position).getImg();
            Log.i("xff", "getView: "+list.toString());
            holder.iv.setImageBitmap(BitmapFactory.decodeByteArray(buffer,0,buffer.length));

            return convertView;
        }

        class ViewHolder {
            ImageView iv;
        }
    }
}
