package com.example.xu.day001xiangmu1;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xu on 2016/10/24.
 */
public class FourFragment extends Fragment{
    private TextView tv_shouchang,tv_xiazai;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        context = getActivity();

        initView(view);

        tv_shouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        context.startActivity(new Intent(context,ShoucangActivity.class));
            }
        });
    }

    private void initView(View view) {
        tv_shouchang = (TextView) view.findViewById(R.id.tv_shoucang);
        tv_xiazai  = (TextView) view.findViewById(R.id.tv_down);
    }
}
