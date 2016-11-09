package com.example.xu.day001xiangmu1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xu.day001xiangmu1.SQLite.MyHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import Bean.Bzjx;

public class NewOnActivity extends AppCompatActivity {

    private ImageView iv;
    private MyHelper helper;
    private ImageLoader loader;
    private File file=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_on);

        helper = new MyHelper(NewOnActivity.this);
        initView();
        loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(this));

        Intent intent = getIntent();
        final Bitmap bitmap = intent.getParcelableExtra("pic");
        iv.setImageBitmap(bitmap);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView2 = LayoutInflater.from(NewOnActivity.this).inflate(R.layout.pop_shang_item,null);
                final PopupWindow pw2 = new PopupWindow(contentView2, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                pw2.setFocusable(true);
                pw2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw2.setOutsideTouchable(true);


                pw2.setAnimationStyle(R.style.ffAnim);
                pw2.showAtLocation(iv, Gravity.TOP,0,0);

                View contentView = LayoutInflater.from(NewOnActivity.this).inflate(R.layout.pop_item,null);
                final PopupWindow pw = new PopupWindow(contentView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


                pw.setFocusable(true);
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.setOutsideTouchable(true);


                pw.setAnimationStyle(R.style.xffAnim);
                pw.showAtLocation(iv, Gravity.BOTTOM,0,0);

//                pw.showAsDropDown();//选一个

//
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        pw2.dismiss();
                    }
                });

                Button but_shoucang = (Button) contentView.findViewById(R.id.button2);
                Button but_xiazai = (Button) contentView.findViewById(R.id.button4);
                but_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);

                        helper.add(new Bzjx(baos.toByteArray()));
                        Toast.makeText(NewOnActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    }
                });
                but_xiazai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        file = new File("/mnt/sdcard/xffpic");
                        if(!file.exists()){
                            file.mkdirs();
                        }
                        try {

                            FileOutputStream fos = new FileOutputStream("/mnt/sdcard/xffpic/"+System.currentTimeMillis()+".png");
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                            fos.flush();
                            Toast.makeText(NewOnActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                            fos.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });


            }
        });


    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.iv_one_tupian);
    }
}
