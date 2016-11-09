package com.example.xu.day001xiangmu1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private DisplayMetrics dm;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        dm = getResources().getDisplayMetrics();
        iv = (ImageView) findViewById(R.id.iv);
        int screenW=dm.widthPixels;
        int screenH = dm.heightPixels;
        iv.setMaxWidth(screenW);
        iv.setMaxHeight(screenH);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3*1000);
                    startActivity(new Intent(MainActivity.this,OneActivity.class));
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
