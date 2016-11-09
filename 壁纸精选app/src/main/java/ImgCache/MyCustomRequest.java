package ImgCache;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by xu on 2016/10/25.
 */
public class MyCustomRequest<T> extends Request<T>{

    private Response.Listener<T> listener;
    private Class<T>clazz;

    public MyCustomRequest(String url,Class<T>clazz, Response.Listener<T> listener,Response.ErrorListener errorListener) {
        this(Method.GET,url,clazz, listener,errorListener);
    }

    public MyCustomRequest(int method, String url,Class<T>clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url,errorListener);
        this.listener = listener;
        this.clazz=clazz;
    }

    //类似于doInBackGround方法
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //1得到json字符串
        String json;
        try {
            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json = new String(response.data);
        }
        //2解析Gson
       T bean= new Gson().fromJson(json,clazz);


        //3将解析的结果返回
        return Response.success(bean, HttpHeaderParser.parseCacheHeaders(response));
    }

    //类似于onPostExecute方法
    @Override
    protected void deliverResponse(T response) {


        if (listener!= null){
            listener.onResponse(response);
        }

    }
}
