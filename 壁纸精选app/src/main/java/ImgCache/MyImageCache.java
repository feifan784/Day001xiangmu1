package ImgCache;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by xu on 2016/10/25.
 */
public class MyImageCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap>lru;
    private static MyImageCache mic;
    private MyImageCache(){
        lru = new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };

    }

    public static MyImageCache getInstance(){
        if (mic == null){
            synchronized (MyImageCache.class){
                if(mic == null){
                    mic = new MyImageCache();
                }

            }

        }
        return mic;
    }


    @Override
    public Bitmap getBitmap(String url) {



        return lru.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bit);
        RectF rectF = new RectF(0,0,bitmap.getWidth(),bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF,10,10,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,0,0,paint);//??????????????????
        lru.put(url,bit);

    }
}
