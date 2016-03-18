package liu.com.mydocuments.myapplication;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liuhui on 16/3/15.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
//        client.setConnectTimeout(100000, TimeUnit.MILLISECONDS);
        OkHttpUtils.getInstance().debug("OkHttpUtils").setConnectTimeout(100000, TimeUnit.MILLISECONDS);
    }
}
