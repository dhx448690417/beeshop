package com.beeshop.beeshop.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.config.AppConfig;
import com.beeshop.beeshop.net.RSAUtil;
import com.beeshop.beeshop.utils.GsonUtil;
import com.beeshop.beeshop.utils.LogUtil;
import retrofit2.http.Url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Author : cooper
 * Time :  2018/7/9 下午5:44
 * Description :
 */
public class ChatActivity extends BaseActivity {

    public static final String PARAM_CHAT_USER_ID = "param_chat_user_id";
    @BindView(R.id.wv_chat)
    WebView wvChat;

    private String mOtherUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_chat);
        ButterKnife.bind(this);

        mOtherUserId = getIntent().getStringExtra(PARAM_CHAT_USER_ID);

        setTitleAndBackPressListener("聊天页面");
        initView();
        loadUrl();

    }

    private void initView() {
        wvChat.setWebViewClient(new MyWebViewClient());
        wvChat.setWebChromeClient(new MyWebViewChromeClient());
        WebSettings webSettings = wvChat.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    private void loadUrl() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", mToken);
        params.put("user_id", mOtherUserId);
        params.put("chatkey", "");

        String data = RSAUtil.encryptDataByPublicKey(GsonUtil.gsonMapToString(params).getBytes());
        String url = "";
        try {
            url = AppConfig.H5_URL_ONLINE+"?version=1.0.0&rsa=201805&data="+ URLEncoder.encode(data,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogUtil.e("ah   url ==    "+url);
        wvChat.loadUrl(url);
    }

    private class MyWebViewChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgress();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgress();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }
    }
}
