package com.beeshop.beeshop.utils.qiniu;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

/**
 * Author : cooper
 * Time :  2018/6/1 下午3:53
 * Description :
 */
public class PicUploadManager {

    private UploadManager mUploadManager;

    private String token = "TrXg-Xc6au5j84DwolTckRtYzYDyfMmTfrSnhfqo:OCm0GgHgg26Uq8nYxVP9TQuW8MA=:eyJzY29wZSI6ImZkd2wiLCJkZWFkbGluZSI6MTUyNzk0OTY1OX0=";

    private PicUploadManager() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .recorder(null)           // recorder分片上传时，已上传片记录器。默认null
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        mUploadManager = new UploadManager(config);
    }

    private static class ClassHolder{
        public static PicUploadManager picUploadManager = new PicUploadManager();
    }

    public static PicUploadManager getInstance() {
        return ClassHolder.picUploadManager;
    }

    public void uploadPic(String path, String key/*,UpCompletionHandler completionHandler,final UploadOptions options*/) {
//        mUploadManager.put(path, key, token,completionHandler, options);
        mUploadManager.put(path, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }
}
