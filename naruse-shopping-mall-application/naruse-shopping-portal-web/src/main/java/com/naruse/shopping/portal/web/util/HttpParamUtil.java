package com.naruse.shopping.portal.web.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class HttpParamUtil {

    /**
     * 获取所有参数，包括url和body的
     * @param request
     * @return
     */
    public static SortedMap<String, Object> getAllParams(HttpServletRequest request) {
        Map<String, Object> urlParam = getUrlParam(request);

        Map<String, Object> bodyParam = getBodyParam(request);

        SortedMap<String, Object> res = new TreeMap<>();
        if (!CollectionUtils.isEmpty(urlParam)) {
            for (Map.Entry<String, Object> entry : urlParam.entrySet()) {
                res.put(entry.getKey(), entry.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(bodyParam)) {
            for (Map.Entry<String, Object> entry : bodyParam.entrySet()) {
                res.put(entry.getKey(), entry.getValue());
            }
        }

        return res;
    }

    /**
     * 获取body里面的参数
     * @param request
     * @return
     */
    private static Map<String, Object> getBodyParam(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String readLine = "";
            while ((readLine = reader.readLine()) != null) {
                sb.append(readLine);
            }

            if (ObjectUtils.isNotEmpty(sb)) {
                res = JSONObject.parseObject(sb.toString(), Map.class);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return res;
    }

    /**
     * 获取url的参数
     * @param request
     * @return
     */
    private static Map<String, Object> getUrlParam(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();

        try {
            String urlParam = "";

            if (StringUtils.isNotBlank(request.getQueryString())) {
                urlParam = request.getQueryString();
            }

            String[] urlParamArr = urlParam.split("&");
            for (String str : urlParamArr) {
                int index = str.indexOf("=");
                res.put(str.substring(0, index), str.substring(index+1));
            }
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        return res;
    }

}
