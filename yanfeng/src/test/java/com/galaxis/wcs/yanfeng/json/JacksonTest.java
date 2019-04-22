package com.galaxis.wcs.yanfeng.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxis.wcs.yanfeng.util.template.WcsResult;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JacksonTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testR() throws IOException {
        String str = "{\"data\":{\"birth_day\":7,\"birth_month\":6},\"errcode\":0,\"msg\":\"ok\",\"ret\":0}";

        JsonNode node = mapper.readTree(str);
        String data = node.get("data").get("birth_month").asText();
        System.out.println("data = " + data);
    }

    @Test
    public void testW() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);

        Map<String, Object> mp = new HashMap<>();
        mp.put("data", map);
        mp.put("aa", "bb");

        String value = mapper.writeValueAsString(mp);
        System.out.println("value = " + value);
    }

    @Test
    public void test1() {
        WcsResult wcsResult = new WcsResult();
        wcsResult.setId(123);
        wcsResult.setMessageName("msgname");
        wcsResult.setResult(0);
        String s = JSON.toJSONString(wcsResult);
        System.out.println(s);
    }

    @Test
    public void testr() {
        String s = "{\"id\":123,\"messageName\":\"msgname\",\"result\":0";
        WcsResult wcsResult = null;
        try {
            wcsResult = JSON.parseObject(s, WcsResult.class);
        } catch (JSONException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        System.out.println("wcsResult = " + wcsResult);
    }

    @Test
    public void testInbound() {
        // {
        //     "id": 643,
        //     "messageName": " appointBoxAnnounce",
        //     "boxId": "214"
        // }
        JSONObject json = new JSONObject();
        json.put("id", 120);
        json.put("messageName", "appointBoxAnnounce");
        json.put("requestLocation", "1");
        json.put("boxId", "carton_no_3");

        String s = json.toJSONString();
        System.out.println("s = " + s);
    }

    @Test
    public void testInboundStart() {
        //     {
        //         "id": 784,
        //         "messageName": " updateMovement",
        //         "boxId": "1111111",
        //         "wmsId": "13523",
        //         "location": {
        //             "s_level": 101,
        //             "s_location": 101,
        //             "e_level": 3,
        //             "e_location": 110111,
        //             "r_level": 0,
        //             "r_location": 0
        //         },
        //         "state": 0
        //     }
        JSONObject json = new JSONObject();
        json.put("id", 121);
        json.put("messageName", "updateMovement");
        json.put("boxId", "carton_no_3");
        json.put("wmsId", "100000005");
        json.put("state", 0);
        JSONObject location = new JSONObject();
        location.put("s_level", 101);
        location.put("s_location", 101);
        location.put("e_level", 3);
        location.put("e_location", 210111);
        location.put("r_level", 0);
        location.put("r_location", 0);
        json.put("location", location);

        String s = json.toJSONString();
        System.out.println("s = " + s);
    }
}
