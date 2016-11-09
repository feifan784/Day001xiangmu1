package com.example.xu.day001xiangmu1;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OneActivity extends AppCompatActivity {

    private FrameLayout fl;
    private List<Fragment>frags = new ArrayList<>();
    private FragmentManager manager;
    private Fragment lastFragment;
    private RadioGroup group;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_one);
        initView();
        initFragments();
//        bar = getSupportActionBar();
//        bar.setTitle("");
//        bar.setCustomView(R.layout.custom_title_one);
//
//        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bottom_bg));


        manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.fl,frags.get(0)).commit();
        group.check(R.id.rb_one);//默认被选中的单选项

        lastFragment = frags.get(0);


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                int tag = Integer.parseInt(rb.getTag().toString());

                if(!frags.get(tag).isAdded()){
                    manager.beginTransaction().add(R.id.fl,frags.get(tag)).commit();
                }else {
                    manager.beginTransaction().show(frags.get(tag)).commit();
                }

                manager.beginTransaction().hide(lastFragment).commit();

                lastFragment = frags.get(tag);

            }
        });



    }

    private void initFragments() {
        frags.add(new OneFragment());
        frags.add(new TwoFragment());
        frags.add(new ThreeFragment());
        frags.add(new FourFragment());


    }

    private void initView() {
        fl = (FrameLayout) findViewById(R.id.fl);
        group = (RadioGroup) findViewById(R.id.group);



    }

    @Override
    public void onBackPressed() {
        long current = System.currentTimeMillis();
        long duration = current-lastTime;

        if (duration<1000){
            finish();
        }else {
            Toast.makeText(OneActivity.this, "双击退出！", Toast.LENGTH_SHORT).show();
        }
        lastTime = current;


    }
}
