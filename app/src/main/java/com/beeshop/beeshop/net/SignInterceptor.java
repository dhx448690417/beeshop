package com.beeshop.beeshop.net;

import android.text.TextUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by sunpengfei on 2017/7/5.
 */

public class SignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        addHeader(builder);
        Map<String, String> params = getBasicParams();
        String sign = getSign(original, params);
        HttpUrl httpUrl = getHttpUrl(original, sign, params);
        builder.url(httpUrl);

        Response response = chain.proceed(builder.build());

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                return response;
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                LoggerUtil.d(" request.url() ===== "+ response.request().url());
                LoggerUtil.d(JsonUtil.formatJson(result));
//                Constant.LogE(TAG, " response.url():"+ response.request().url());
//                Constant.LogE(TAG, " response.body():" + result);
                //得到所需的string，开始判断是否异常
                //***********************do something*****************************

            }

        }
        return response;
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    protected void addHeader(Request.Builder builder) {
    }

    protected Map<String, String> getBasicParams() {
        return null;
    }

    private HttpUrl getHttpUrl(Request original, String sign, Map<String, String> params) {
        HttpUrl.Builder builder = original.url().newBuilder();
        if (!TextUtils.isEmpty(sign)) {
            builder.addQueryParameter("sign", sign);
        }
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addQueryParameter(key, params.get(key));
            }
        }
        return builder.build();
    }

    private String getSign(Request original, Map<String, String> params) {
        HttpUrl url = original.url();

        Map<String, String> paramsGet = new HashMap<>();
        for (int i = 0; i < url.querySize(); i++) {
            paramsGet.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        if (params != null && !params.isEmpty()) {
            paramsGet.putAll(params);
        }

        Map<String, String> postParams = new HashMap<>();
        if (original.body() instanceof FormBody) {
            FormBody formBody = (FormBody) original.body();
            for (int i = 0; i < formBody.size(); i++) {
                postParams.put(formBody.name(i), formBody.value(i));
            }
        }

        return SignHelper.getTokens(paramsGet, postParams);
    }
}
