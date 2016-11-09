package ImgCache;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Bean.FenleiBean;

/**
 * Created by xu on 2016/10/28.
 */
public class ListLoadData extends AsyncTask<String,Void,List<FenleiBean.DataBean>>{

    private FenleiBean.DataBean bean;
    private List<FenleiBean.DataBean>list = new ArrayList<>();

    public interface ListCallBack{
        void callBack(List<FenleiBean.DataBean> result);
    }
    private ListCallBack back;

    public ListLoadData(ListCallBack back) {
        this.back = back;
    }

    @Override
    protected List<FenleiBean.DataBean> doInBackground(String... params) {

        Log.i("xff", "doInBackground: "+121212);
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
            conn.connect();

            if (conn.getResponseCode() == 200){

                InputStream is =conn.getInputStream();
                StringBuffer sb = new StringBuffer();
                byte[]buffer = new byte[1024];
                int len =  0;
                while((len = is.read(buffer))!=-1){
                    sb.append(new String(buffer,0,len));
                }
                String json = sb.toString();
                Log.i("xff", "doInBackground: "+json);
                try {
                    JSONObject object = new JSONObject(json);
                    JSONArray dataArray = object.getJSONArray("data");
                    List<FenleiBean.DataBean>dataList = new ArrayList<>();
                    for (int i = 0; i <dataArray.length() ; i++) {
                        FenleiBean.DataBean datas = new FenleiBean.DataBean();
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String id = dataObject.getString("ID");
                        datas.setID(id);
                        dataList.add(datas);

                    }
                    Log.i("xff", "doInBackground111111: "+dataList.toString());
                    return dataList;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                 bean = new Gson().fromJson(json,FenleiBean.DataBean.class);

//                list.add(bean);


            }

                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<FenleiBean.DataBean> result) {
        super.onPostExecute(result);

        if(result!=null&&back!=null){
            back.callBack(result);
        }

    }
}
