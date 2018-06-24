package com.beeshop.beeshop;

import com.alibaba.fastjson.JSONObject;
import com.beeshop.beeshop.utils.GsonUtil;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ResponseEntityOne responseEntityOne = GsonUtil.gsonToBean(responseStr, ResponseEntityOne.class);
        System.out.println("  jsonObject1 === "+responseEntityOne.getData());
    }

    class ResponseEntityOne implements Serializable {
        private int status;
        private String msg;
        private JSONObject data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public JSONObject getData() {
            return data;
        }

        public void setData(JSONObject data) {
            this.data = data;
        }
    }

    String responseStr = "{\n" +
            "  \"status\": 200,\n" +
            "  \"msg\": \"成功\",\n" +
            "  \"data\": {\n" +
            "    \"count\": 15,\n" +
            "    \"list\": [\n" +
            "      {\n" +
            "        \"id\": \"time\",\n" +
            "        \"name\": \"时间进度\",\n" +
            "        \"calculate\": \"63.33%\",\n" +
            "        \"value\": 19,\n" +
            "        \"budget\": 30,\n" +
            "        \"gap\": \"\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"shelve_c2c\",\n" +
            "        \"name\": \"C端上架\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 202011,\n" +
            "        \"gap\": 202011\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"appoint_task_c2c\",\n" +
            "        \"name\": \"C销售总带看工单\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 1420506,\n" +
            "        \"gap\": 1420506\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"appoint_task_success_c_and_consign\",\n" +
            "        \"name\": \"C销售总带看成交\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 51782,\n" +
            "        \"gap\": 51782\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"finance_inlet_c2c_all\",\n" +
            "        \"name\": \"C销售总进件\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 4451,\n" +
            "        \"gap\": 4451\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"appoint_task\",\n" +
            "        \"name\": \"C端带看工单\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 1397680,\n" +
            "        \"gap\": 1397680\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"evaluation_sign_time\",\n" +
            "        \"name\": \"C端带看成交\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 47269,\n" +
            "        \"gap\": 47269\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"net_finance_set_c2c\",\n" +
            "        \"name\": \"C端净已定\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 34817,\n" +
            "        \"gap\": 34817\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"finance_give_without_consign\",\n" +
            "        \"name\": \"C端放款\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 0,\n" +
            "        \"gap\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"appoint_task_c2c_consign\",\n" +
            "        \"name\": \"C售保卖带看工单\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 22826,\n" +
            "        \"gap\": 22826\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"appoint_task_success_c2c_consign\",\n" +
            "        \"name\": \"C售保卖带看成交\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 4513,\n" +
            "        \"gap\": 4513\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"net_finance_set_c2c_consigned\",\n" +
            "        \"name\": \"C售保卖净已定\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 3547,\n" +
            "        \"gap\": 3547\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"finance_give_c2c_consign\",\n" +
            "        \"name\": \"C售保卖放款\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 1163,\n" +
            "        \"gap\": 1163\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"net_finance_set_c2c_c_sale_consigned\",\n" +
            "        \"name\": \"C销售总净已定\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 38364,\n" +
            "        \"gap\": 38364\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": \"finance_give_c_c_sale_consign\",\n" +
            "        \"name\": \"C销售总放款\",\n" +
            "        \"value\": 0,\n" +
            "        \"calculate\": \"0.00%\",\n" +
            "        \"budget\": 1163,\n" +
            "        \"gap\": 1163\n" +
            "      }\n" +
            "    ],\n" +
            "    \"help\": [\n" +
            "      {\n" +
            "        \"key\": \"C端上架\",\n" +
            "        \"value\": \"C端上架 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C销售总带看工单\",\n" +
            "        \"value\": \"C销售总带看工单 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C销售总带看成交\",\n" +
            "        \"value\": \"C销售总带看成交 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C销售总进件\",\n" +
            "        \"value\": \"C销售总进件 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C端带看工单\",\n" +
            "        \"value\": \"C端带看工单 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C端带看成交\",\n" +
            "        \"value\": \"C端带看成交 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C端净已定\",\n" +
            "        \"value\": \"C端净已定 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C端放款\",\n" +
            "        \"value\": \"C端放款 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C售保卖带看工单\",\n" +
            "        \"value\": \"C售保卖带看工单 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C售保卖带看成交\",\n" +
            "        \"value\": \"C售保卖带看成交 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C售保卖净已定\",\n" +
            "        \"value\": \"C售保卖净已定 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C售保卖放款\",\n" +
            "        \"value\": \"C售保卖放款 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C销售总净已定\",\n" +
            "        \"value\": \"C销售总净已定 / 预算\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"key\": \"C销售总放款\",\n" +
            "        \"value\": \"C销售总放款 / 预算\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"flag\": \"1\",\n" +
            "    \"message\": \"\"\n" +
            "  }\n" +
            "}";
}