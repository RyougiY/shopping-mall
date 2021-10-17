package com.naruse.shopping.portal.web.util;

import com.naruse.shopping.common.util.util.EncryptUtil;

import java.util.*;

public class CheckUtil {

    public static final String appSecret = "Naruse";

    public static boolean checkSignIsSucceed(Map<String,Object> map){
        String sign = (String) map.get("sign");
        map.remove("sign");
        // 生成sign
        String s = CheckUtil.generatorSign(map);
        return s.equals(sign);
    }

    public static String generatorSign(Map<String,Object> map){
        map.remove("sign");
        // 排序:
        Map<String, Object> stringObjectMap = sortMapByKey(map);
        // 转格式:   name=张三&age=10,:  name,张三,age,10
        Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,Object> e : entries){
            sb.append(e.getKey()).append(",").append(e.getValue()).append("#");
        }

        // 组装secret  在参数的后面 添加 secret
        sb.append("secret").append(appSecret);
        // 生成签名
        return EncryptUtil.encrypt(sb.toString());
    }

    public static Map<String,Object> sortMapByKey(Map<String,Object> map){

        Map<String,Object> sortMap = new TreeMap<>(new MyMapComparator());

        sortMap.putAll(map);

        return  sortMap;

    }

    private static class MyMapComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("appId",1);
        map.put("name","2");

        Map<String, Object> stringObjectMap = sortMapByKey(map);
        String sign = generatorSign(stringObjectMap);
        System.out.println(sign); //4cee1724f45f54250d7e4cd2d6f1aadb
    }
}
