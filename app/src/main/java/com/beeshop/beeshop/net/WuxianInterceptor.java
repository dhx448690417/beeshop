package com.beeshop.beeshop.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunpengfei on 2017/7/28.
 */

class WuxianInterceptor extends SignInterceptor {
    @Override
    protected Map<String, String> getBasicParams() {
        Map<String, String> params = new HashMap<>();
        params.put("customerId", PhoneInfoHelper.customerId);
        params.put("deviceId", PhoneInfoHelper.IMEI);
        params.put("dpi", PhoneInfoHelper.density + "");
        params.put("screenWH", PhoneInfoHelper.screenWidth + "X" + PhoneInfoHelper.screenHeight);
        params.put("osv", PhoneInfoHelper.osv + "");
        params.put("model", PhoneInfoHelper.model);
        params.put("platform", PhoneInfoHelper.platform);
        params.put("versionId", PhoneInfoHelper.versionName);
        params.put("net", PhoneInfoHelper.netType);
        return params;
    }
}
