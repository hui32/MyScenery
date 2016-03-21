package liu.com.mydocuments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import liu.com.mydocuments.constants.Constant;
import liu.com.mydocuments.jsonBean.SceneryDetail;
import liu.com.mydocuments.util.DataUtil;
import okhttp3.Call;

public class SceneryWvDetailActivity extends BaseActivity {

    @Bind(R.id.wv_detail)
    WebView wvDetail;

    private String detailUrl;
    private String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_wv_detail);
        ButterKnife.bind(this);
        detailUrl = getIntent().getExtras().getString(Constant.IntentParam.DETAIL_URL);
        productId = getIntent().getExtras().getString(Constant.IntentParam.PRODUCT_ID);
        if (!TextUtils.isEmpty(productId)){
            getDetaitlUtl();
        }else if (!TextUtils.isEmpty(detailUrl)){
            wvDetail.loadUrl(detailUrl);
        }
        initWebView();
    }

    private void getDetaitlUtl(){
        OkHttpUtils
                .get()
                .url(Constant.QunarUrl.detailUrl)
                .addHeader(Constant.API_KEY, Constant.BKEY_VALUE)
                .addParams(Constant.RequestParam.SCENERY_ID,productId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "requestUrl = " + Constant.QunarUrl.detailUrl + "\nreqponse-->:" + DataUtil.Unicode2GBK(response));
                        String responseStr = DataUtil.Unicode2GBK(response);
                        SceneryDetail detail = new Gson().fromJson(responseStr, SceneryDetail.class);
                        detailUrl = detail.retData.ticketDetail.loc;
                        wvDetail.loadUrl(detailUrl);
                    }
                });
    }
    private void initWebView() {
        //支持javascript
        wvDetail.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        wvDetail.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        //wvDetail.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wvDetail.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wvDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvDetail.getSettings().setLoadWithOverviewMode(true);
        wvDetail.setWebViewClient(new NaviWebViewClient());
        wvDetail.setWebChromeClient(new WebChromeClient());
        wvDetail.goBack();
    }

    private class NaviWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(detailUrl);
            Log.e(TAG,"detailUrl = "+detailUrl);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
