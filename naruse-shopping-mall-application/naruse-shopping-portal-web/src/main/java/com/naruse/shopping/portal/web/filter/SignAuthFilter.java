package com.naruse.shopping.portal.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.naruse.shopping.common.base.wrapper.BodyReaderHttpServletRequestWrapper;
import com.naruse.shopping.portal.web.util.CheckUtil;
import com.naruse.shopping.portal.web.util.HttpParamUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;

@Component
public class SignAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //获取参数
        SortedMap<String, Object> allParams = HttpParamUtil.getAllParams(httpServletRequest);

        if (CheckUtil.checkSignIsSucceed(allParams)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();

            JSONObject param = new JSONObject();
            param.put("code",-1);
            param.put("msg", "check sign is failed");

            writer.append(param.toJSONString());
        }
    }

    @Override
    public void destroy() {

    }
}
