package com.zhouzhihao.sxh1.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouzhihao on 2016/7/13 0013.
 */
public class FastJsonTools {
    public FastJsonTools() {

    }

    /**
     * 对单个javabean的解析
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T get(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static <T> List<T> getList(String jsonStriung, Class<T> cls) {

        List<T> list = new ArrayList<T>();
        try {
            list = JSONArray.parseArray(jsonStriung, cls);
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("exception:",e.toString());
        }
        return list;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
