package com.example.jsontest;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class JsonTest {

    private static final String TAG = "JsonTest";

    /*
        将JSON格式的字符串转换为Java对象，使用原生API
     */
    @Test
    public void jsonStringToObject1() {
        //  JSON字符串
        String jsonString = "{\"id\":2,\"name\":\"大虾\",\"price\":12.3,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}";
        ShopInfo shopInfo = null;
        try {
            //  创建JSON对象
            JSONObject jsonObject = new JSONObject(jsonString);
            //  根据key，取出对应的value
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            double price = jsonObject.getDouble("price");
            String imagePath = jsonObject.getString("imagePath");
            //  根据value，创建ShopInfo对象
            shopInfo = new ShopInfo(id, name, price, imagePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertNotNull(shopInfo);
        assertEquals(2, shopInfo.getId());
        assertEquals("大虾", shopInfo.getName());
        assertEquals(12.3, shopInfo.getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", shopInfo.getImagePath());
    }

    /*
        将JSON格式的字符串转换为Java对象，使用Gson API
     */
    @Test
    public void jsonStringToObject2() {
        //  JSON字符串
        String jsonString = "{\"id\":2,\"name\":\"大虾\",\"price\":12.3,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}";
        //  创建Gson对象
        Gson gson = new Gson();
        //  调用fromJson，将JSON字符串转换为ShopInfo对象
        ShopInfo shopInfo = gson.fromJson(jsonString, ShopInfo.class);

        assertEquals(2, shopInfo.getId());
        assertEquals("大虾", shopInfo.getName());
        assertEquals(12.3, shopInfo.getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", shopInfo.getImagePath());
    }

    /*
        将JSON格式的字符串转换为Java对象List，使用原生API
     */
    @Test
    public void jsonStringToObjectList1() {
        //  JSON字符串
        String jsonString = "[{\"id\":2,\"name\":\"大虾1\",\"price\":12.3,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}, {\"id\":3,\"name\":\"大虾2\",\"price\":12.4,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}]";
        int length = -1;
        List<ShopInfo> list = new ArrayList<>();

        try {
            //  创建JSONArray
            JSONArray jsonArray = new JSONArray(jsonString);
            //  获取JSONArray中元素的个数
            length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                //  得到JSONObject
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //  根据key，取出对应的value
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                double price = jsonObject.getDouble("price");
                String imagePath = jsonObject.getString("imagePath");
                //  创建ShopInfo对象
                ShopInfo shopInfo = new ShopInfo(id, name, price, imagePath);
                //  加入数组
                list.add(shopInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertEquals(2, length);
        assertEquals(2, list.size());

        assertEquals(2, list.get(0).getId());
        assertEquals("大虾1", list.get(0).getName());
        assertEquals(12.3, list.get(0).getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", list.get(0).getImagePath());

        assertEquals(3, list.get(1).getId());
        assertEquals("大虾2", list.get(1).getName());
        assertEquals(12.4, list.get(1).getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", list.get(1).getImagePath());
    }

    /*
        将JSON格式的字符串转换为Java对象List，使用Gson API
     */
    @Test
    public void jsonStringToObjectList2() {
        String jsonString = "[{\"id\":2,\"name\":\"大虾1\",\"price\":12.3,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}, {\"id\":3,\"name\":\"大虾2\",\"price\":12.4,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}]";
        Gson gson = new Gson();
        //  new TypeToken<List<ShopInfo>>() {}.getType()：固定格式
        List<ShopInfo> list = gson.fromJson(jsonString, new TypeToken<List<ShopInfo>>() {}.getType());

        assertEquals(2, list.size());

        assertEquals(2, list.get(0).getId());
        assertEquals("大虾1", list.get(0).getName());
        assertEquals(12.3, list.get(0).getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", list.get(0).getImagePath());

        assertEquals(3, list.get(1).getId());
        assertEquals("大虾2", list.get(1).getName());
        assertEquals(12.4, list.get(1).getPrice(), 0.0);
        assertEquals("https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0", list.get(1).getImagePath());
    }

    /*
        将Java对象转换为JSON字符串，使用Gson API
     */
    @Test
    public void jsonObject2JsonString() {
        ShopInfo shopInfo = new ShopInfo(2, "大虾", 12.3, "https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0");

        Gson gson = new Gson();
        String jsonString = new Gson().toJson(shopInfo);
        Log.d(TAG, "jsonObject2JsonString: " + jsonString);
    }

    /*
        将Java对象List转换为JSON字符串，使用Gson API
     */
    @Test
    public void jsonObjectList2JsonString() {
        List<ShopInfo> list = new ArrayList<>();
        list.add(new ShopInfo(2, "大虾1", 12.3, "https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0"));
        list.add(new ShopInfo(3, "大虾2", 12.4, "https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        Log.d(TAG, "jsonObjectList2JsonString: " + jsonString);
    }

    /*
        将JSON对象转换为Map，使用Gson API
     */
    @Test
    public void jsonObject2Map() {
        String jsonString = "{\"id\":2,\"name\":\"大虾\",\"price\":12.3,\"imagePath\":\"https://go.smzdm.com/0faeb5c57c10523c/ca_aa_fx_95_22768859_10759_182940_111_0\"}";
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
        Log.d(TAG, "jsonObject2Map: " + map);
    }
}
